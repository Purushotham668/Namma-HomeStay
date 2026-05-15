package com.nammahomestay.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false,
    val userRole: String = "",
    val needsRoleSelection: Boolean = false,
    val pendingGoogleUser: com.google.firebase.auth.FirebaseUser? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val currentUser get() = authRepository.currentUser

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when (val result = authRepository.login(email, password)) {
                is Resource.Success -> {
                    fetchUserRole(result.data.uid)
                }
                is Resource.Error -> {
                    val errorMessage = when {
                        result.message?.contains("user-not-found") == true -> "Account not found. Please register first."
                        result.message?.contains("wrong-password") == true -> "Incorrect password. Please try again."
                        else -> result.message ?: "Login failed"
                    }
                    _uiState.value = _uiState.value.copy(isLoading = false, error = errorMessage)
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when (val result = authRepository.signInWithGoogle(idToken)) {
                is Resource.Success -> {
                    val user = result.data
                    val isRegistered = authRepository.isUserRegistered(user.uid)
                    if (isRegistered) {
                        fetchUserRole(user.uid)
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            needsRoleSelection = true,
                            pendingGoogleUser = user
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun completeGoogleRegistration(name: String, phone: String, role: String) {
        val user = _uiState.value.pendingGoogleUser ?: return
        val normalizedRole = role.lowercase()
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when (val result = authRepository.registerGoogleUser(user.uid, name, user.email ?: "", phone, normalizedRole)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        userRole = normalizedRole,
                        needsRoleSelection = false,
                        pendingGoogleUser = null
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun register(name: String, email: String, phone: String, password: String, role: String) {
        val normalizedRole = role.lowercase()
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when (val result = authRepository.register(name, email, phone, password, normalizedRole)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        userRole = normalizedRole
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun fetchUserRole(uid: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = authRepository.getUserRole(uid)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        userRole = result.data
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun logout() {
        authRepository.logout()
        _uiState.value = AuthUiState()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
