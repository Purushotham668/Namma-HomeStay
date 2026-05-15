package com.nammahomestay.app.ui.traveler.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.model.User
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.UserRepository
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileSettingsUiState(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val dob: String = "",
    val language: String = "English",
    val notificationsEnabled: Boolean = true,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    // Tracks which fields have been locally modified but not yet saved
    val isDirty: Boolean = false
)

@HiltViewModel
class ProfileSettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileSettingsUiState())
    val uiState: StateFlow<ProfileSettingsUiState> = _uiState.asStateFlow()

    // Debounce job — cancels and re-schedules on every keystroke
    private var autoSaveJob: Job? = null
    private var currentUid: String = ""

    init {
        listenToProfile()
    }

    /**
     * Opens a real-time Firestore snapshot listener.
     * UI will reflect changes from ANY device/source immediately.
     */
    private fun listenToProfile() {
        val uid = authRepository.currentUser?.uid ?: return
        currentUid = uid

        viewModelScope.launch {
            userRepository.getUserProfileStream(uid).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val user = result.data
                        // Only update local state if the user isn't actively editing
                        // (avoids overwriting in-progress keystrokes from remote updates)
                        if (!_uiState.value.isDirty) {
                            _uiState.value = _uiState.value.copy(
                                uid = user.uid,
                                fullName = user.name,
                                email = user.email,
                                phoneNumber = user.phone,
                                dob = user.dob,
                                language = user.language,
                                notificationsEnabled = user.notificationsEnabled,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    // ─── Field Updaters ──────────────────────────────────────────────────────

    fun updateFullName(name: String) {
        _uiState.value = _uiState.value.copy(fullName = name, isDirty = true)
        scheduleAutoSave()
    }

    fun updatePhoneNumber(phone: String) {
        _uiState.value = _uiState.value.copy(phoneNumber = phone, isDirty = true)
        scheduleAutoSave()
    }

    fun updateDob(dob: String) {
        _uiState.value = _uiState.value.copy(dob = dob, isDirty = true)
        scheduleAutoSave()
    }

    fun updateLanguage(lang: String) {
        _uiState.value = _uiState.value.copy(language = lang, isDirty = true)
        scheduleAutoSave()
    }

    fun toggleNotifications(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(notificationsEnabled = enabled, isDirty = true)
        // Toggle changes save immediately — no need to debounce
        saveProfile()
    }

    // ─── Auto-Save with 1.5s Debounce ────────────────────────────────────────

    /**
     * Schedules a Firebase save 1.5 seconds after the last keystroke.
     * Any subsequent change cancels the previous save and reschedules.
     */
    private fun scheduleAutoSave() {
        autoSaveJob?.cancel()
        autoSaveJob = viewModelScope.launch {
            delay(1500L)
            saveProfile()
        }
    }

    // ─── Manual Save (also called by debounce) ───────────────────────────────

    fun saveProfile() {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true)

            val currentRole = when (val roleResult = authRepository.getUserRole(uid)) {
                is Resource.Success -> roleResult.data
                else -> "traveler"
            }

            val updatedUser = User(
                uid = uid,
                name = _uiState.value.fullName,
                email = _uiState.value.email,
                phone = _uiState.value.phoneNumber,
                dob = _uiState.value.dob,
                language = _uiState.value.language,
                notificationsEnabled = _uiState.value.notificationsEnabled,
                role = currentRole
            )

            when (val result = userRepository.saveUserProfile(updatedUser)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        isSuccess = true,
                        isDirty = false
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        error = result.message
                    )
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun clearStatus() {
        _uiState.value = _uiState.value.copy(isSuccess = false, error = null)
    }
}
