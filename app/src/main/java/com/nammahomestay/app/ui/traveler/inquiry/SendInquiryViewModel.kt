package com.nammahomestay.app.ui.traveler.inquiry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.model.Inquiry
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.InquiryRepository
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.data.repository.HomeStayRepository

data class SendInquiryUiState(
    val isLoading: Boolean = false,
    val homeStay: HomeStay? = null,
    val name: String = "",
    val phone: String = "",
    val message: String = "",
    val error: String? = null,
    val isSent: Boolean = false
)

@HiltViewModel
class SendInquiryViewModel @Inject constructor(
    private val inquiryRepository: InquiryRepository,
    private val authRepository: AuthRepository,
    private val homeStayRepository: HomeStayRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SendInquiryUiState())
    val uiState: StateFlow<SendInquiryUiState> = _uiState.asStateFlow()

    fun init(homestayId: String) {
        prefillUserInfo()
        loadHomeStay(homestayId)
    }

    private fun loadHomeStay(id: String) {
        viewModelScope.launch {
            when (val result = homeStayRepository.getHomeProfile(id)) {
                is Resource.Success<HomeStay> -> {
                    _uiState.value = _uiState.value.copy(homeStay = result.data)
                }
                else -> Unit
            }
        }
    }

    private fun prefillUserInfo() {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            when (val result = authRepository.getUserProfile(uid)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        name = result.data.name,
                        phone = result.data.phone
                    )
                }
                else -> Unit
            }
        }
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updatePhone(phone: String) {
        _uiState.value = _uiState.value.copy(phone = phone)
    }

    fun updateMessage(message: String) {
        _uiState.value = _uiState.value.copy(message = message)
    }

    fun sendInquiry(homestayId: String) {
        val state = _uiState.value
        if (state.homeStay == null || state.homeStay.providerId.isEmpty()) {
            _uiState.value = state.copy(error = "Homestay information is still loading. Please try again in a moment.")
            return
        }
        
        if (state.name.isBlank() || state.phone.isBlank() || state.message.isBlank()) {
            _uiState.value = state.copy(error = "Please fill all fields")
            return
        }
        
        if (state.isLoading) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val inquiry = Inquiry(
                travelerId = authRepository.currentUser?.uid ?: "",
                providerId = state.homeStay?.providerId ?: "",
                homestayId = homestayId,
                homestayName = state.homeStay?.name ?: "HomeStay",
                senderId = authRepository.currentUser?.uid ?: "",
                message = state.message.trim()
            )
            when (val result = inquiryRepository.sendMessage(inquiry)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, isSent = true)
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
