package com.nammahomestay.app.ui.traveler.booking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.model.Booking
import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.BookingRepository
import com.nammahomestay.app.data.repository.HomeStayRepository
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.concurrent.TimeUnit
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.nammahomestay.app.MainActivity
import com.nammahomestay.app.R

data class BookingUiState(
    val homeStay: HomeStay? = null,
    val checkInDate: Long? = null,
    val checkOutDate: Long? = null,
    val guests: Int = 1,
    val totalPrice: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val bookingId: String? = null,
    val paymentMethod: String = "Credit Card", // or "UPI"
    val paymentSuccess: Boolean = false
)

@HiltViewModel
class BookingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authRepository: AuthRepository,
    private val homeStayRepository: HomeStayRepository,
    private val bookingRepository: BookingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val homestayId: String? = savedStateHandle.get<String>("homestayId")
    private val bookingIdFromNav: String? = savedStateHandle.get<String>("bookingId")

    private val _uiState = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState.asStateFlow()

    init {
        homestayId?.let { loadHomeStay(it) }
        bookingIdFromNav?.let { loadBooking(it) }
    }

    private fun loadHomeStay(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = homeStayRepository.getHomeProfile(id)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        homeStay = result.data
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun updateDates(checkIn: Long?, checkOut: Long?) {
        _uiState.value = _uiState.value.copy(checkInDate = checkIn, checkOutDate = checkOut)
        calculateTotal()
    }

    fun updateGuests(count: Int) {
        _uiState.value = _uiState.value.copy(guests = count)
        calculateTotal()
    }

    private fun calculateTotal() {
        val stay = _uiState.value.homeStay ?: return
        val checkIn = _uiState.value.checkInDate
        val checkOut = _uiState.value.checkOutDate
        
        if (checkIn != null && checkOut != null && checkOut > checkIn) {
            val diffInMillies = checkOut - checkIn
            val days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
            val basePrice = stay.pricing.pricePerDay.toDoubleOrNull() ?: 0.0
            
            val total = days * basePrice
            _uiState.value = _uiState.value.copy(totalPrice = total)
        } else {
            _uiState.value = _uiState.value.copy(totalPrice = 0.0)
        }
    }

    fun updatePaymentMethod(method: String) {
        _uiState.value = _uiState.value.copy(paymentMethod = method)
    }

    fun loadBooking(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = bookingRepository.getBookingById(id)) {
                is Resource.Success -> {
                    val booking = result.data
                    // Also load homestay to show its details
                    val stayResult = homeStayRepository.getHomeProfile(booking.homestayId)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        bookingId = booking.id,
                        totalPrice = booking.totalPrice,
                        guests = booking.guests,
                        checkInDate = booking.checkInDate,
                        checkOutDate = booking.checkOutDate,
                        homeStay = if (stayResult is Resource.Success) stayResult.data else null
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun createBooking() {
        val travelerId = authRepository.currentUser?.uid ?: return
        val stay = _uiState.value.homeStay ?: return
        val checkIn = _uiState.value.checkInDate ?: return
        val checkOut = _uiState.value.checkOutDate ?: return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val booking = Booking(
                travelerId = travelerId,
                providerId = stay.providerId,
                homestayId = stay.id,
                homestayName = stay.name,
                homestayImageUrl = stay.photoUrls.firstOrNull() ?: "",
                homestayAddress = stay.address,
                checkInDate = checkIn,
                checkOutDate = checkOut,
                guests = _uiState.value.guests,
                totalPrice = _uiState.value.totalPrice,
                status = "Pending", 
                qrCodeData = "" 
            )

            when (val result = bookingRepository.createBooking(booking)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        bookingId = result.data
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun processPayment(bookingId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            kotlinx.coroutines.delay(2000)
            
            when (val result = bookingRepository.updateBookingStatus(bookingId, "Confirmed")) {
                is Resource.Success -> {
                    val qrData = "nammascan:$bookingId"
                    bookingRepository.updateBookingQrCode(bookingId, qrData)
                    
                    // Simulate FCM Push Notification to Provider
                    simulateProviderNotification(
                        title = "New Booking Confirmed!",
                        body = "${_uiState.value.guests} guest(s) booked ${_uiState.value.homeStay?.name}"
                    )
                    
                    _uiState.value = _uiState.value.copy(isLoading = false, paymentSuccess = true)
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }

    private fun simulateProviderNotification(title: String, body: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = "namma_homestay_notifications"
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Booking Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        manager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
