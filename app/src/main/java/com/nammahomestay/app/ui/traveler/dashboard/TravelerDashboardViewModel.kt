package com.nammahomestay.app.ui.traveler.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.data.model.Booking
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.BookingRepository
import com.nammahomestay.app.data.repository.HomeStayRepository
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val searchQuery: String = "",
    val suggestions: List<String> = emptyList(),
    val featuredStays: List<HomeStay> = emptyList(),
    val selectedCategory: String = "Forest",
    val isLoading: Boolean = false,
    val activeStay: Booking? = null
)

@HiltViewModel
class TravelerDashboardViewModel @Inject constructor(
    private val repository: HomeStayRepository,
    private val bookingRepository: BookingRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private var allHomeStays: List<HomeStay> = emptyList()

    init {
        loadData()
    }

    private fun loadData() {
        val uid = authRepository.currentUser?.uid ?: ""
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // 1. Load Stays
            launch {
                when (val result = repository.getAllHomeStays()) {
                    is Resource.Success -> {
                        allHomeStays = result.data
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            featuredStays = result.data.take(3)
                        )
                    }
                    else -> _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }

            // 2. Observe Active Booking (Checked-In)
            if (uid.isNotEmpty()) {
                launch {
                    bookingRepository.observeTravelerBookings(uid).collect { bookings ->
                        val currentTime = System.currentTimeMillis()
                        val active = bookings.find { 
                            it.status == "CheckedIn" && currentTime < it.checkOutDate 
                        }
                        _uiState.update { it.copy(activeStay = active) }
                    }
                }
            }
        }
    }

    fun onCategorySelected(category: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
        updateFeaturedStays()
    }

    private fun updateFeaturedStays() {
        val selected = _uiState.value.selectedCategory
        val filtered = allHomeStays.filter { it.categories.contains(selected) }
        _uiState.value = _uiState.value.copy(featuredStays = filtered.take(5))
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        updateSuggestions(query)
    }

    private fun updateSuggestions(query: String) {
        if (query.isBlank()) {
            _uiState.value = _uiState.value.copy(suggestions = emptyList())
            return
        }
        
        val nameSuggestions = allHomeStays.filter { it.name.contains(query, ignoreCase = true) }.map { it.name }
        val locationSuggestions = allHomeStays.filter { it.address.contains(query, ignoreCase = true) }.map { it.address }
        
        val combined = (nameSuggestions + locationSuggestions).distinct().take(5)
        _uiState.value = _uiState.value.copy(suggestions = combined)
    }
}
