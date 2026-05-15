package com.nammahomestay.app.ui.provider.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.data.model.Pricing
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.HomeStayRepository
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeProfileUiState(
    val isLoading: Boolean = false,
    val homeStays: List<HomeStay> = emptyList(),
    val homeStay: HomeStay = HomeStay(),
    val error: String? = null,
    val isSaved: Boolean = false,
    val isEditing: Boolean = false,
    val selectedPhotos: List<Uri> = emptyList(),
    val inquiryCounts: Map<String, Int> = emptyMap(),
    val accountName: String = ""
)

@HiltViewModel
class HomeProfileViewModel @Inject constructor(
    private val repository: HomeStayRepository,
    private val userRepository: com.nammahomestay.app.data.repository.UserRepository,
    private val inquiryRepository: com.nammahomestay.app.data.repository.InquiryRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeProfileUiState())
    val uiState: StateFlow<HomeProfileUiState> = _uiState.asStateFlow()

    // Tracks which homestay IDs already have an active inquiry observer
    // so we never launch duplicate coroutines when Firestore emits updates.
    private val observedInquiryIds = mutableSetOf<String>()

    init {
        loadProfiles()
        observeAccountName()
    }

    private fun observeAccountName() {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            userRepository.getUserProfileStream(uid).collect { result ->
                if (result is Resource.Success) {
                    _uiState.value = _uiState.value.copy(accountName = result.data.name)
                }
            }
        }
    }

    fun loadProfiles() {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            repository.observeProviderHomeStays(uid).collect { list ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    homeStays = list
                )
                // Only start a new inquiry observer for properties not yet tracked
                list.forEach { stay ->
                    if (stay.id.isNotEmpty() && stay.id !in observedInquiryIds) {
                        observedInquiryIds.add(stay.id)
                        observeInquiries(stay.id)
                    }
                }
            }
        }
    }

    private fun observeInquiries(homestayId: String) {
        viewModelScope.launch {
            inquiryRepository.observeInquiryCount(homestayId).collect { count ->
                val currentCounts = _uiState.value.inquiryCounts.toMutableMap()
                currentCounts[homestayId] = count
                _uiState.value = _uiState.value.copy(inquiryCounts = currentCounts)
            }
        }
    }

    fun selectForEdit(homeStay: HomeStay) {
        _uiState.value = _uiState.value.copy(
            homeStay = homeStay,
            isEditing = true
        )
    }

    fun createNewProfile() {
        val uid = authRepository.currentUser?.uid ?: return
        _uiState.value = _uiState.value.copy(
            homeStay = HomeStay(providerId = uid),
            isEditing = true
        )
    }

    fun backToList() {
        // Just toggle the editing flag — the real-time flow from loadProfiles()
        // is still active, so no need to restart it.
        _uiState.value = _uiState.value.copy(isEditing = false)
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(homeStay = _uiState.value.homeStay.copy(name = name))
    }

    fun updateOwnerName(owner: String) {
        _uiState.value = _uiState.value.copy(homeStay = _uiState.value.homeStay.copy(ownerName = owner))
    }

    fun updateContact(contact: String) {
        _uiState.value = _uiState.value.copy(homeStay = _uiState.value.homeStay.copy(contact = contact))
    }

    fun updateAddress(address: String) {
        _uiState.value = _uiState.value.copy(homeStay = _uiState.value.homeStay.copy(address = address))
    }

    fun updateDescription(desc: String) {
        _uiState.value = _uiState.value.copy(homeStay = _uiState.value.homeStay.copy(description = desc))
    }

    fun updatePricePerDay(price: String) {
        _uiState.value = _uiState.value.copy(
            homeStay = _uiState.value.homeStay.copy(
                pricing = _uiState.value.homeStay.pricing.copy(pricePerDay = price)
            )
        )
    }

    fun updatePricePerPerson(price: String) {
        _uiState.value = _uiState.value.copy(
            homeStay = _uiState.value.homeStay.copy(
                pricing = _uiState.value.homeStay.pricing.copy(pricePerPerson = price)
            )
        )
    }

    fun updateRoomsAvailable(count: String) {
        _uiState.value = _uiState.value.copy(
            homeStay = _uiState.value.homeStay.copy(
                pricing = _uiState.value.homeStay.pricing.copy(roomsAvailable = count)
            )
        )
    }

    fun updateCategory(category: String) {
        val current = _uiState.value.homeStay.categories.toMutableList()
        if (current.contains(category)) {
            current.remove(category)
        } else {
            current.add(category)
        }
        _uiState.value = _uiState.value.copy(
            homeStay = _uiState.value.homeStay.copy(categories = current)
        )
    }

    fun updateLatitude(lat: String) {
        val latDouble = lat.toDoubleOrNull() ?: 0.0
        _uiState.value = _uiState.value.copy(homeStay = _uiState.value.homeStay.copy(latitude = latDouble))
    }

    fun updateLongitude(lon: String) {
        val lonDouble = lon.toDoubleOrNull() ?: 0.0
        _uiState.value = _uiState.value.copy(homeStay = _uiState.value.homeStay.copy(longitude = lonDouble))
    }

    fun updateChecklist(key: String, value: Boolean) {
        val newChecklist = _uiState.value.homeStay.checklist.toMutableMap()
        newChecklist[key] = value
        _uiState.value = _uiState.value.copy(
            homeStay = _uiState.value.homeStay.copy(checklist = newChecklist)
        )
    }

    fun addPhotos(uris: List<Uri>) {
        _uiState.value = _uiState.value.copy(
            selectedPhotos = _uiState.value.selectedPhotos + uris
        )
    }

    fun toggleOccupancy(homeStayId: String, isOccupied: Boolean) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = repository.updateOccupancy(homeStayId, isOccupied)) {
                is Resource.Success -> _uiState.value = _uiState.value.copy(isLoading = false)
                is Resource.Error -> _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                is Resource.Loading -> Unit
            }
        }
    }

    fun toggleAvailability(homeStayId: String, isAvailable: Boolean) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val currentPricing = _uiState.value.homeStays.find { it.id == homeStayId }?.pricing ?: Pricing()
            val newPricing = currentPricing.copy(isAvailable = isAvailable)
            when (val result = repository.updatePricing(homeStayId, newPricing)) {
                // Real-time listener will automatically update the UI on success
                is Resource.Success -> _uiState.value = _uiState.value.copy(isLoading = false)
                is Resource.Error -> _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                is Resource.Loading -> Unit
            }
        }
    }

    fun removePhoto(uri: Uri) {
        _uiState.value = _uiState.value.copy(
            selectedPhotos = _uiState.value.selectedPhotos - uri
        )
    }

    fun addRoom() {
        val currentRooms = _uiState.value.homeStay.rooms.toMutableList()
        currentRooms.add(com.nammahomestay.app.data.model.Room())
        val updatedHomeStay = _uiState.value.homeStay.copy(rooms = currentRooms)
        _uiState.value = _uiState.value.copy(homeStay = updatedHomeStay)
        
        // Realtime update if existing property
        if (updatedHomeStay.id.isNotEmpty()) {
            silentSaveRooms(updatedHomeStay.id, currentRooms)
        }
    }

    fun updateRoom(updatedRoom: com.nammahomestay.app.data.model.Room) {
        val currentRooms = _uiState.value.homeStay.rooms.toMutableList()
        val index = currentRooms.indexOfFirst { it.id == updatedRoom.id }
        if (index != -1) {
            currentRooms[index] = updatedRoom
            val updatedHomeStay = _uiState.value.homeStay.copy(rooms = currentRooms)
            _uiState.value = _uiState.value.copy(homeStay = updatedHomeStay)
            
            // Realtime update if existing property
            if (updatedHomeStay.id.isNotEmpty()) {
                silentSaveRooms(updatedHomeStay.id, currentRooms)
            }
        }
    }

    fun removeExistingPhoto(url: String) {
        val currentPhotos = _uiState.value.homeStay.photoUrls.filter { it != url }
        val updatedHomeStay = _uiState.value.homeStay.copy(photoUrls = currentPhotos)
        _uiState.value = _uiState.value.copy(homeStay = updatedHomeStay)
        
        // For photos, we'll let the global save handle it for now, 
        // but for consistency we could add updatePhotos in repository.
    }

    fun removeRoom(roomId: String) {
        val currentRooms = _uiState.value.homeStay.rooms.filter { it.id != roomId }
        val updatedHomeStay = _uiState.value.homeStay.copy(rooms = currentRooms)
        _uiState.value = _uiState.value.copy(homeStay = updatedHomeStay)
        
        // Realtime update if existing property
        if (updatedHomeStay.id.isNotEmpty()) {
            silentSaveRooms(updatedHomeStay.id, currentRooms)
        }
    }

    fun uploadRoomPhoto(roomId: String, uri: Uri) {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = repository.uploadPhoto(uid, uri)) {
                is Resource.Success -> {
                    val currentRooms = _uiState.value.homeStay.rooms.toMutableList()
                    val index = currentRooms.indexOfFirst { it.id == roomId }
                    if (index != -1) {
                        currentRooms[index] = currentRooms[index].copy(photoUrl = result.data)
                        val updatedHomeStay = _uiState.value.homeStay.copy(rooms = currentRooms)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            homeStay = updatedHomeStay
                        )
                        
                        // Realtime update after photo upload
                        if (updatedHomeStay.id.isNotEmpty()) {
                            silentSaveRooms(updatedHomeStay.id, currentRooms)
                        }
                    }
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }

    private fun silentSaveRooms(id: String, rooms: List<com.nammahomestay.app.data.model.Room>) {
        viewModelScope.launch {
            repository.updateRooms(id, rooms)
            // No loading state or error handling needed for "silent" updates 
            // unless we want to show a small toast/indicator.
        }
    }

    fun saveProfile() {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                // 1. Upload new photos if any
                val newPhotoUrls = mutableListOf<String>()
                _uiState.value.selectedPhotos.forEach { uri ->
                    when (val uploadResult = repository.uploadPhoto(uid, uri)) {
                        is Resource.Success -> newPhotoUrls.add(uploadResult.data)
                        is Resource.Error -> throw Exception("Photo upload failed: ${uploadResult.message}")
                        else -> Unit
                    }
                }

                // 2. Combine with existing photos and save
                val finalPhotos = _uiState.value.homeStay.photoUrls + newPhotoUrls
                val updatedHomeStay = _uiState.value.homeStay.copy(
                    providerId = uid,
                    photoUrls = finalPhotos
                )
                
                when (val result = repository.saveHomeProfile(updatedHomeStay)) {
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSaved = true,
                            selectedPhotos = emptyList()
                        )
                        // Real-time listener will automatically refresh the property list
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                    }
                    is Resource.Loading -> Unit
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.localizedMessage)
            }
        }
    }

    fun deleteProperty(homeStayId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when (val result = repository.deleteHomeStay(homeStayId)) {
                is Resource.Success -> {
                    // Remove from observed set so if re-added later it gets a fresh listener
                    observedInquiryIds.remove(homeStayId)
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    // Real-time listener will remove the property from the list automatically
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
