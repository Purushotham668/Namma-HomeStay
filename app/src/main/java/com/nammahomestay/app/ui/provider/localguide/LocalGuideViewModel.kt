package com.nammahomestay.app.ui.provider.localguide

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.model.LocalSpot
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.LocalSpotRepository
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class LocalGuideUiState(
    val isLoading: Boolean = false,
    val spots: List<LocalSpot> = emptyList(),
    val error: String? = null,
    val isSaved: Boolean = false
)

@HiltViewModel
class LocalGuideViewModel @Inject constructor(
    private val repository: LocalSpotRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocalGuideUiState())
    val uiState: StateFlow<LocalGuideUiState> = _uiState.asStateFlow()

    init {
        loadSpots()
    }

    private fun loadSpots() {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = repository.getSpots(uid)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        spots = result.data
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun addSpot(name: String, distance: String, description: String) {
        val newSpot = LocalSpot(
            id = UUID.randomUUID().toString(),
            name = name,
            distance = distance,
            description = description
        )
        val newList = _uiState.value.spots + newSpot
        saveSpots(newList)
    }

    fun removeSpot(id: String) {
        val newList = _uiState.value.spots.filter { it.id != id }
        saveSpots(newList)
    }

    private fun saveSpots(spots: List<LocalSpot>) {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when (val result = repository.addSpot(uid, spots)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        spots = spots,
                        isSaved = true
                    )
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
