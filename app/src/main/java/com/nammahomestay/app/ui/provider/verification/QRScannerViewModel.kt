package com.nammahomestay.app.ui.provider.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.repository.BookingRepository
import com.nammahomestay.app.data.repository.FoodRepository
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QRScannerUiState(
    val isScanning: Boolean = true,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val foodBooking: com.nammahomestay.app.data.model.FoodBooking? = null,
    val error: String? = null
)

@HiltViewModel
class QRScannerViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QRScannerUiState())
    val uiState: StateFlow<QRScannerUiState> = _uiState.asStateFlow()

    private var hasProcessed = false

    fun processQRCode(qrData: String) {
        if (hasProcessed) return
        
        hasProcessed = true

        when {
            qrData.startsWith("nammascan:") -> processStayQR(qrData.removePrefix("nammascan:"))
            qrData.startsWith("nammafood:") -> processFoodQR(qrData.removePrefix("nammafood:"))
            else -> {
                _uiState.value = _uiState.value.copy(
                    isScanning = false,
                    error = "Invalid QR Code format. Please scan a valid Namma Homestay QR code."
                )
            }
        }
    }

    private fun processStayQR(bookingId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isScanning = false, isLoading = true, error = null)
            when (val fetchResult = bookingRepository.getBookingById(bookingId)) {
                is Resource.Success -> {
                    val booking = fetchResult.data
                    if (booking.status == "CheckedIn") {
                        _uiState.value = _uiState.value.copy(isLoading = false, error = "Guest is already checked in!")
                        return@launch
                    }
                    when (val updateResult = bookingRepository.updateBookingStatus(bookingId, "CheckedIn")) {
                        is Resource.Success -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                successMessage = "Check-in Successful: ${booking.guests} guest(s) for ${booking.homestayName}"
                            )
                        }
                        is Resource.Error -> _uiState.value = _uiState.value.copy(isLoading = false, error = updateResult.message)
                        else -> Unit
                    }
                }
                is Resource.Error -> _uiState.value = _uiState.value.copy(isLoading = false, error = "Booking not found")
                else -> Unit
            }
        }
    }

    private fun processFoodQR(bookingId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isScanning = false, isLoading = true, error = null)
            val result = foodRepository.getBookingById(bookingId)
            if (result is Resource.Success && result.data != null) {
                _uiState.value = _uiState.value.copy(isLoading = false, foodBooking = result.data)
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Food ticket not found")
            }
        }
    }

    fun markFoodAsServed() {
        val bookingId = _uiState.value.foodBooking?.id ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = foodRepository.markAsServed(bookingId)
            if (result is Resource.Success) {
                _uiState.value = _uiState.value.copy(isLoading = false, successMessage = "Meal marked as served!", foodBooking = null)
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Failed to update food status")
            }
        }
    }

    fun resumeScanning() {
        hasProcessed = false
        _uiState.value = QRScannerUiState()
    }
}
