package com.nammahomestay.app.ui.traveler.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.model.HomeStay
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
import kotlin.math.*

enum class PriceSort {
    NONE,
    LOW_TO_HIGH,
    HIGH_TO_LOW
}

data class ExploreUiState(
    val isLoading: Boolean = false,
    val homeStays: List<HomeStay> = emptyList(),
    val selectedStayReviews: List<Review> = emptyList(),
    val suggestions: List<String> = emptyList(),
    val selectedCategories: Set<String> = emptySet(),
    val isNearestSelected: Boolean = false,
    val isTopRatedSelected: Boolean = false,
    val priceSort: PriceSort = PriceSort.NONE,
    val error: String? = null,
    val searchQuery: String = "",
    val userLat: Double? = null,
    val userLon: Double? = null,
    val isCheckedInAtCurrent: Boolean = false
)

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: HomeStayRepository,
    private val bookingRepository: BookingRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExploreUiState())
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    private var originalList: List<HomeStay> = emptyList()

    init {
        loadHomeStays()
    }

    fun loadHomeStays() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            fetchAndProcess()
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query, isLoading = true) }
        updateSuggestions(query)
        fetchAndProcess()
    }

    private var bookingJob: kotlinx.coroutines.Job? = null
    private var reviewsJob: kotlinx.coroutines.Job? = null

    fun loadReviews(homestayId: String) {
        // 1. Real-time Observation of Reviews
        reviewsJob?.cancel()
        reviewsJob = viewModelScope.launch {
            repository.observeReviewsForHomestay(homestayId).collect { reviews ->
                _uiState.update { it.copy(selectedStayReviews = reviews) }
            }
        }

        // 2. Real-time Observation of Booking Status
        val uid = authRepository.currentUser?.uid ?: return
        bookingJob?.cancel()
        bookingJob = viewModelScope.launch {
            bookingRepository.observeTravelerBookings(uid).collect { bookings ->
                val isCheckedIn = bookings.any { 
                    it.homestayId == homestayId && it.status == "CheckedIn" 
                }
                _uiState.update { it.copy(isCheckedInAtCurrent = isCheckedIn) }
            }
        }
    }

    private fun updateSuggestions(query: String) {
        if (query.isBlank()) {
            _uiState.update { it.copy(suggestions = emptyList()) }
            return
        }
        
        val allStays = originalList
        val nameSuggestions = allStays.filter { it.name.contains(query, ignoreCase = true) }.map { it.name }
        val locationSuggestions = allStays.filter { it.address.contains(query, ignoreCase = true) }.map { it.address }
        
        val combined = (nameSuggestions + locationSuggestions).distinct().take(5)
        _uiState.update { it.copy(suggestions = combined) }
    }

    fun toggleNearest() {
        _uiState.update { it.copy(isNearestSelected = !it.isNearestSelected) }
        applySortingAndFiltering(originalList)
    }

    fun toggleTopRated() {
        _uiState.update { it.copy(isTopRatedSelected = !it.isTopRatedSelected) }
        applySortingAndFiltering(originalList)
    }

    fun setPriceSort(sort: PriceSort) {
        _uiState.update { state ->
            // If already selected, toggle off. Otherwise, select it.
            val newSort = if (state.priceSort == sort) PriceSort.NONE else sort
            state.copy(priceSort = newSort)
        }
        applySortingAndFiltering(originalList)
    }

    fun updateUserLocation(lat: Double, lon: Double) {
        _uiState.update { it.copy(userLat = lat, userLon = lon) }
        applySortingAndFiltering(originalList)
    }

    private fun fetchAndProcess() {
        viewModelScope.launch {
            val query = _uiState.value.searchQuery
            val result = if (query.isBlank()) {
                repository.getAllHomeStays()
            } else {
                repository.searchHomeStays(query)
            }

            when (result) {
                is Resource.Success -> {
                    originalList = result.data
                    applySortingAndFiltering(result.data)
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun toggleCategory(category: String) {
        _uiState.update { state ->
            val current = state.selectedCategories.toMutableSet()
            if (current.contains(category)) {
                current.remove(category)
            } else {
                current.add(category)
            }
            state.copy(selectedCategories = current)
        }
        applySortingAndFiltering(originalList)
    }

    private fun applySortingAndFiltering(list: List<HomeStay>) {
        val state = _uiState.value
        
        // 0. Filtering by Availability (Provider's On/Off toggle)
        var resultList = list.filter { it.pricing.isAvailable }
        
        // 1. Filtering by Categories
        resultList = if (state.selectedCategories.isEmpty()) {
            resultList
        } else {
            resultList.filter { h -> h.categories.any { state.selectedCategories.contains(it) } }
        }

        // 2. Multi-Criteria Sorting/Filtering
        // We will use a composite comparator based on selected toggles
        val comparator = Comparator<HomeStay> { h1, h2 ->
            var comparison = 0
            
            // Priority 1: Price Sort (Exclusive between High/Low)
            if (state.priceSort != PriceSort.NONE) {
                val p1 = h1.pricing.pricePerDay.toDoubleOrNull() ?: 0.0
                val p2 = h2.pricing.pricePerDay.toDoubleOrNull() ?: 0.0
                comparison = if (state.priceSort == PriceSort.LOW_TO_HIGH) {
                    p1.compareTo(p2)
                } else {
                    p2.compareTo(p1)
                }
            }
            
            // Priority 2: Rating (Top Rated)
            if (comparison == 0 && state.isTopRatedSelected) {
                comparison = h2.rating.compareTo(h1.rating)
            }
            
            // Priority 3: Distance (Nearest)
            if (comparison == 0 && state.isNearestSelected && state.userLat != null && state.userLon != null) {
                val d1 = calculateDistance(state.userLat, state.userLon, h1.latitude, h1.longitude)
                val d2 = calculateDistance(state.userLat, state.userLon, h2.latitude, h2.longitude)
                comparison = d1.compareTo(d2)
            }
            
            comparison
        }

        val finalResult = resultList.sortedWith(comparator)

        _uiState.update { 
            it.copy(
                isLoading = false,
                homeStays = finalResult
            )
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371 // Radius of the earth in km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }
}
