package com.nammahomestay.app.ui.provider.pricing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.model.Pricing
import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.HomeStayRepository
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PricingUiState(
    val isLoading: Boolean = false,
    val homeStays: List<HomeStay> = emptyList(),
    val selectedHomeStay: HomeStay? = null,
    val pricing: Pricing = Pricing(),
    val error: String? = null,
    val isSaved: Boolean = false
)

@HiltViewModel
class PricingViewModel @Inject constructor(
    private val repository: HomeStayRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PricingUiState())
    val uiState: StateFlow<PricingUiState> = _uiState.asStateFlow()

    init {
        loadProperties()
    }

    fun loadProperties() {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = repository.getProviderHomeStays(uid)) {
                is Resource.Success -> {
                    val stays = result.data
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        homeStays = stays,
                        selectedHomeStay = stays.firstOrNull(),
                        pricing = stays.firstOrNull()?.pricing ?: Pricing()
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun selectProperty(homeStay: HomeStay) {
        _uiState.value = _uiState.value.copy(
            selectedHomeStay = homeStay,
            pricing = homeStay.pricing
        )
    }

    fun updatePricePerDay(text: String) {
        _uiState.value = _uiState.value.copy(pricing = _uiState.value.pricing.copy(pricePerDay = text))
    }

    fun updatePricePerPerson(text: String) {
        _uiState.value = _uiState.value.copy(pricing = _uiState.value.pricing.copy(pricePerPerson = text))
    }

    fun updateRoomsAvailable(text: String) {
        _uiState.value = _uiState.value.copy(pricing = _uiState.value.pricing.copy(roomsAvailable = text))
    }

    fun updateAvailability(isAvailable: Boolean) {
        _uiState.value = _uiState.value.copy(pricing = _uiState.value.pricing.copy(isAvailable = isAvailable))
    }

    fun savePricing() {
        val stayId = _uiState.value.selectedHomeStay?.id ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when (val result = repository.updatePricing(stayId, _uiState.value.pricing)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, isSaved = true)
                    loadProperties() // Refresh data to get latest pricing from server
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun clearSavedState() {
        _uiState.value = _uiState.value.copy(isSaved = false)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
