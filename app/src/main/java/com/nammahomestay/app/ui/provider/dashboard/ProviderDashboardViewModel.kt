package com.nammahomestay.app.ui.provider.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.HomeStayRepository
import com.nammahomestay.app.data.repository.InquiryRepository
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardStatsUiState(
    val rating: String = "--",
    val inquiryCount: String = "--",
    val status: String = "--",
    val providerName: String = "Host",
    val isLoading: Boolean = true
)

@HiltViewModel
class ProviderDashboardViewModel @Inject constructor(
    private val homeStayRepository: HomeStayRepository,
    private val userRepository: com.nammahomestay.app.data.repository.UserRepository,
    private val inquiryRepository: InquiryRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardStatsUiState())
    val uiState: StateFlow<DashboardStatsUiState> = _uiState.asStateFlow()

    // Track which property IDs are already being observed for inquiries
    private val observedInquiryIds = mutableSetOf<String>()
    // Accumulate counts per property
    private val inquiryCountsMap = mutableMapOf<String, Int>()

    init {
        loadStats()
        observeAccountName()
    }

    private fun observeAccountName() {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            userRepository.getUserProfileStream(uid).collect { result ->
                if (result is Resource.Success) {
                    val firstName = result.data.name.split(" ").firstOrNull() ?: "Host"
                    _uiState.value = _uiState.value.copy(providerName = firstName)
                }
            }
        }
    }

    private fun loadStats() {
        val uid = authRepository.currentUser?.uid ?: return

        viewModelScope.launch {
            homeStayRepository.observeProviderHomeStays(uid).collect { stays ->
                if (stays.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        rating = "New",
                        inquiryCount = "0",
                        status = "Off",
                        isLoading = false
                    )
                    return@collect
                }

                // --- Rating: aggregate average across all properties ---
                val ratedStays = stays.filter { it.reviewCount > 0 }
                val avgRating = if (ratedStays.isNotEmpty()) {
                    val total = ratedStays.sumOf { it.rating.toDouble() * it.reviewCount }
                    val totalReviews = ratedStays.sumOf { it.reviewCount }
                    String.format("%.1f", total / totalReviews)
                } else "New"

                // --- Status: "On" if any property is live and not occupied ---
                val anyLive = stays.any { it.pricing.isAvailable }
                val anyOccupied = stays.any { it.isOccupied }
                val statusText = when {
                    anyOccupied -> "Full"
                    anyLive     -> "On"
                    else        -> "Off"
                }

                // --- Provider name from first stay's owner name ---
                val ownerName = stays.firstOrNull()?.ownerName?.let { name ->
                    name.split(" ").firstOrNull() ?: name
                } ?: "Host"

                _uiState.value = _uiState.value.copy(
                    rating = avgRating,
                    status = statusText,
                    isLoading = false
                )

                // --- Inquiries: start per-property observers (deduped) ---
                stays.forEach { stay ->
                    if (stay.id.isNotEmpty() && stay.id !in observedInquiryIds) {
                        observedInquiryIds.add(stay.id)
                        observeInquiriesForProperty(stay.id)
                    }
                }
            }
        }
    }

    private fun observeInquiriesForProperty(homestayId: String) {
        viewModelScope.launch {
            inquiryRepository.observeInquiryCount(homestayId).collect { count ->
                inquiryCountsMap[homestayId] = count
                val total = inquiryCountsMap.values.sum()
                _uiState.value = _uiState.value.copy(inquiryCount = total.toString())
            }
        }
    }
}
