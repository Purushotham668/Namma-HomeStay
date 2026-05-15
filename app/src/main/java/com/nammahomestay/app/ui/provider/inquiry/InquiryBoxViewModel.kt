package com.nammahomestay.app.ui.provider.inquiry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.model.Inquiry
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.InquiryRepository
import com.nammahomestay.app.data.repository.HomeStayRepository
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InquiryBoxUiState(
    val isLoading: Boolean = false,
    val inquiries: List<Inquiry> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class InquiryBoxViewModel @Inject constructor(
    private val repository: InquiryRepository,
    private val homeStayRepository: HomeStayRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InquiryBoxUiState())
    val uiState: StateFlow<InquiryBoxUiState> = _uiState.asStateFlow()

    init {
        cleanupAndLoad()
    }

    private fun cleanupAndLoad() {
        viewModelScope.launch {
            repository.cleanupOldInquiries()
            loadInquiries()
        }
    }

    fun loadInquiries() {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = repository.getConversationsForProvider(uid)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        inquiries = result.data
                    )
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
