package com.nammahomestay.app.ui.traveler.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.model.Booking
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyBookingsUiState(
    val bookings: List<Booking> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class MyBookingsViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyBookingsUiState())
    val uiState: StateFlow<MyBookingsUiState> = _uiState.asStateFlow()

    init {
        loadBookings()
    }

    private fun loadBookings() {
        val travelerId = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            bookingRepository.observeTravelerBookings(travelerId).collect { list ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    bookings = list
                )
            }
        }
    }

    fun cancelBooking(bookingId: String) {
        viewModelScope.launch {
            bookingRepository.updateBookingStatus(bookingId, "Cancelled")
        }
    }

    fun markCheckout(bookingId: String) {
        viewModelScope.launch {
            bookingRepository.updateBookingStatus(bookingId, "Completed")
        }
    }
}
