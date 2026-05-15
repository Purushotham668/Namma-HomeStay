package com.nammahomestay.app.ui.traveler.booking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.model.Review
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

data class ReviewUiState(
    val rating: Int = 5,
    val comment: String = "",
    val photos: List<android.net.Uri> = emptyList(),
    val isUploading: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ReviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
    private val bookingRepository: BookingRepository,
    private val homeStayRepository: HomeStayRepository
) : ViewModel() {

    private val bookingId: String = checkNotNull(savedStateHandle["bookingId"])
    private val homestayId: String = checkNotNull(savedStateHandle["homestayId"])

    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState: StateFlow<ReviewUiState> = _uiState.asStateFlow()

    init {
        loadExistingReview()
    }

    private fun loadExistingReview() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // Using bookingId as the review ID for stable mapping
            when (val result = homeStayRepository.getReviewByBookingId(bookingId)) {
                is Resource.Success -> {
                    result.data?.let { review ->
                        _uiState.update { state ->
                            state.copy(
                                rating = review.rating,
                                comment = review.comment,
                                isLoading = false
                            )
                        }
                    } ?: _uiState.update { it.copy(isLoading = false) }
                }
                is Resource.Error -> _uiState.update { it.copy(isLoading = false) }
                is Resource.Loading -> Unit
            }
        }
    }

    fun updateRating(rating: Int) {
        _uiState.value = _uiState.value.copy(rating = rating)
    }

    fun updateComment(comment: String) {
        if (comment.length <= 10000) { // Approx 2000 words limit (5 chars/word)
            _uiState.value = _uiState.value.copy(comment = comment)
        }
    }

    fun addPhotos(uris: List<android.net.Uri>) {
        val current = _uiState.value.photos.toMutableList()
        current.addAll(uris)
        _uiState.value = _uiState.value.copy(photos = current.take(5)) // Limit to 5 photos
    }

    fun removePhoto(uri: android.net.Uri) {
        val current = _uiState.value.photos.toMutableList()
        current.remove(uri)
        _uiState.value = _uiState.value.copy(photos = current)
    }

    fun submitReview() {
        val user = authRepository.currentUser ?: return
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            // 1. Upload Photos if any
            val uploadedUrls = mutableListOf<String>()
            if (_uiState.value.photos.isNotEmpty()) {
                _uiState.value = _uiState.value.copy(isUploading = true)
                _uiState.value.photos.forEach { uri ->
                    when (val uploadResult = homeStayRepository.uploadPhoto(user.uid, uri)) {
                        is Resource.Success -> uploadedUrls.add(uploadResult.data)
                        is Resource.Error -> {
                            _uiState.value = _uiState.value.copy(isLoading = false, isUploading = false, error = "Photo upload failed: ${uploadResult.message}")
                            return@launch
                        }
                        else -> Unit
                    }
                }
            }
            _uiState.value = _uiState.value.copy(isUploading = false)

            val review = Review(
                id = bookingId, // Use bookingId as stable review ID
                homestayId = homestayId,
                travelerId = user.uid,
                travelerName = user.displayName ?: "Traveler",
                rating = _uiState.value.rating,
                comment = _uiState.value.comment.trim(),
                reviewPhotos = uploadedUrls
            )
            
            when (val result = homeStayRepository.submitReview(review)) {
                is Resource.Success -> {
                    // Update booking status to Reviewed
                    bookingRepository.updateBookingStatus(bookingId, "Reviewed")
                    _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }
}
