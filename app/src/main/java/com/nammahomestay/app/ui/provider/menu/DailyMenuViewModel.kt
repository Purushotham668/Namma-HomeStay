package com.nammahomestay.app.ui.provider.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahomestay.app.data.model.DailyMenu
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.MenuRepository
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.data.repository.HomeStayRepository
import javax.inject.Inject

data class DailyMenuUiState(
    val isLoading: Boolean = false,
    val homeStays: List<HomeStay> = emptyList(),
    val selectedHomeStay: HomeStay? = null,
    val menu: DailyMenu = DailyMenu(),
    val error: String? = null,
    val isUpdated: Boolean = false,
    val expandedItemIds: Set<String> = emptySet()
)

@HiltViewModel
class DailyMenuViewModel @Inject constructor(
    private val repository: MenuRepository,
    private val homeStayRepository: HomeStayRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyMenuUiState())
    val uiState: StateFlow<DailyMenuUiState> = _uiState.asStateFlow()

    init {
        loadProperties()
    }

    private fun loadProperties() {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = homeStayRepository.getProviderHomeStays(uid)) {
                is Resource.Success -> {
                    val stays = result.data
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        homeStays = stays
                    )
                    // Select first property by default only if none is selected
                    if (stays.isNotEmpty() && _uiState.value.selectedHomeStay == null) {
                        selectProperty(stays.first())
                    } else if (_uiState.value.selectedHomeStay != null) {
                        // Update the selected property with new data from the list
                        stays.find { it.id == _uiState.value.selectedHomeStay?.id }?.let {
                            _uiState.value = _uiState.value.copy(selectedHomeStay = it, menu = it.menu)
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

    fun selectProperty(homeStay: HomeStay) {
        _uiState.value = _uiState.value.copy(
            selectedHomeStay = homeStay,
            menu = homeStay.menu,
            expandedItemIds = emptySet() // Minimize all on property switch
        )
    }

    fun toggleItemExpansion(id: String) {
        val current = _uiState.value.expandedItemIds
        _uiState.value = _uiState.value.copy(
            expandedItemIds = if (current.contains(id)) current - id else current + id
        )
    }

    // --- Breakfast Operations ---
    fun addBreakfastItem() {
        val newItem = com.nammahomestay.app.data.model.FoodItem()
        val items = _uiState.value.menu.breakfastItems.toMutableList()
        items.add(newItem)
        _uiState.value = _uiState.value.copy(
            menu = _uiState.value.menu.copy(breakfastItems = items),
            expandedItemIds = _uiState.value.expandedItemIds + newItem.id
        )
    }

    fun updateBreakfastItem(id: String, name: String, description: String) {
        val items = _uiState.value.menu.breakfastItems.toMutableList()
        val index = items.indexOfFirst { it.id == id }
        if (index != -1) {
            items[index] = items[index].copy(name = name, description = description)
            _uiState.value = _uiState.value.copy(menu = _uiState.value.menu.copy(breakfastItems = items))
        }
    }

    fun updateBreakfastItemPrice(id: String, price: String) {
        val items = _uiState.value.menu.breakfastItems.toMutableList()
        val index = items.indexOfFirst { it.id == id }
        if (index != -1) {
            items[index] = items[index].copy(price = price)
            _uiState.value = _uiState.value.copy(menu = _uiState.value.menu.copy(breakfastItems = items))
        }
    }

    fun toggleBreakfastItemVegStatus(id: String) {
        val items = _uiState.value.menu.breakfastItems.toMutableList()
        val index = items.indexOfFirst { it.id == id }
        if (index != -1) {
            items[index] = items[index].copy(isVeg = !items[index].isVeg)
            _uiState.value = _uiState.value.copy(menu = _uiState.value.menu.copy(breakfastItems = items))
        }
    }

    fun removeBreakfastItem(id: String) {
        val items = _uiState.value.menu.breakfastItems.filter { it.id != id }
        _uiState.value = _uiState.value.copy(
            menu = _uiState.value.menu.copy(breakfastItems = items),
            expandedItemIds = _uiState.value.expandedItemIds - id
        )
    }

    // --- Lunch Operations ---
    fun addLunchItem() {
        val newItem = com.nammahomestay.app.data.model.FoodItem()
        val items = _uiState.value.menu.lunchItems.toMutableList()
        items.add(newItem)
        _uiState.value = _uiState.value.copy(
            menu = _uiState.value.menu.copy(lunchItems = items),
            expandedItemIds = _uiState.value.expandedItemIds + newItem.id
        )
    }

    fun updateLunchItem(id: String, name: String, description: String) {
        val items = _uiState.value.menu.lunchItems.toMutableList()
        val index = items.indexOfFirst { it.id == id }
        if (index != -1) {
            items[index] = items[index].copy(name = name, description = description)
            _uiState.value = _uiState.value.copy(menu = _uiState.value.menu.copy(lunchItems = items))
        }
    }

    fun updateLunchItemPrice(id: String, price: String) {
        val items = _uiState.value.menu.lunchItems.toMutableList()
        val index = items.indexOfFirst { it.id == id }
        if (index != -1) {
            items[index] = items[index].copy(price = price)
            _uiState.value = _uiState.value.copy(menu = _uiState.value.menu.copy(lunchItems = items))
        }
    }

    fun toggleLunchItemVegStatus(id: String) {
        val items = _uiState.value.menu.lunchItems.toMutableList()
        val index = items.indexOfFirst { it.id == id }
        if (index != -1) {
            items[index] = items[index].copy(isVeg = !items[index].isVeg)
            _uiState.value = _uiState.value.copy(menu = _uiState.value.menu.copy(lunchItems = items))
        }
    }

    fun removeLunchItem(id: String) {
        val items = _uiState.value.menu.lunchItems.filter { it.id != id }
        _uiState.value = _uiState.value.copy(
            menu = _uiState.value.menu.copy(lunchItems = items),
            expandedItemIds = _uiState.value.expandedItemIds - id
        )
    }

    // --- Dinner Operations ---
    fun addDinnerItem() {
        val newItem = com.nammahomestay.app.data.model.FoodItem()
        val items = _uiState.value.menu.dinnerItems.toMutableList()
        items.add(newItem)
        _uiState.value = _uiState.value.copy(
            menu = _uiState.value.menu.copy(dinnerItems = items),
            expandedItemIds = _uiState.value.expandedItemIds + newItem.id
        )
    }

    fun updateDinnerItem(id: String, name: String, description: String) {
        val items = _uiState.value.menu.dinnerItems.toMutableList()
        val index = items.indexOfFirst { it.id == id }
        if (index != -1) {
            items[index] = items[index].copy(name = name, description = description)
            _uiState.value = _uiState.value.copy(menu = _uiState.value.menu.copy(dinnerItems = items))
        }
    }

    fun updateDinnerItemPrice(id: String, price: String) {
        val items = _uiState.value.menu.dinnerItems.toMutableList()
        val index = items.indexOfFirst { it.id == id }
        if (index != -1) {
            items[index] = items[index].copy(price = price)
            _uiState.value = _uiState.value.copy(menu = _uiState.value.menu.copy(dinnerItems = items))
        }
    }

    fun toggleDinnerItemVegStatus(id: String) {
        val items = _uiState.value.menu.dinnerItems.toMutableList()
        val index = items.indexOfFirst { it.id == id }
        if (index != -1) {
            items[index] = items[index].copy(isVeg = !items[index].isVeg)
            _uiState.value = _uiState.value.copy(menu = _uiState.value.menu.copy(dinnerItems = items))
        }
    }

    fun removeDinnerItem(id: String) {
        val items = _uiState.value.menu.dinnerItems.filter { it.id != id }
        _uiState.value = _uiState.value.copy(
            menu = _uiState.value.menu.copy(dinnerItems = items),
            expandedItemIds = _uiState.value.expandedItemIds - id
        )
    }

    // --- Special Operations ---
    fun addSpecial() {
        val newItem = com.nammahomestay.app.data.model.FoodItem()
        val currentSpecials = _uiState.value.menu.specials.toMutableList()
        currentSpecials.add(newItem)
        _uiState.value = _uiState.value.copy(
            menu = _uiState.value.menu.copy(specials = currentSpecials),
            expandedItemIds = _uiState.value.expandedItemIds + newItem.id
        )
    }

    fun updateSpecial(id: String, name: String, description: String) {
        val currentSpecials = _uiState.value.menu.specials.toMutableList()
        val index = currentSpecials.indexOfFirst { it.id == id }
        if (index != -1) {
            currentSpecials[index] = currentSpecials[index].copy(name = name, description = description)
            _uiState.value = _uiState.value.copy(
                menu = _uiState.value.menu.copy(specials = currentSpecials)
            )
        }
    }

    fun updateSpecialPrice(id: String, price: String) {
        val currentSpecials = _uiState.value.menu.specials.toMutableList()
        val index = currentSpecials.indexOfFirst { it.id == id }
        if (index != -1) {
            currentSpecials[index] = currentSpecials[index].copy(price = price)
            _uiState.value = _uiState.value.copy(
                menu = _uiState.value.menu.copy(specials = currentSpecials)
            )
        }
    }

    fun toggleSpecialVegStatus(id: String) {
        val currentSpecials = _uiState.value.menu.specials.toMutableList()
        val index = currentSpecials.indexOfFirst { it.id == id }
        if (index != -1) {
            currentSpecials[index] = currentSpecials[index].copy(isVeg = !currentSpecials[index].isVeg)
            _uiState.value = _uiState.value.copy(
                menu = _uiState.value.menu.copy(specials = currentSpecials)
            )
        }
    }

    fun removeSpecial(id: String) {
        val currentSpecials = _uiState.value.menu.specials.filter { it.id != id }
        _uiState.value = _uiState.value.copy(
            menu = _uiState.value.menu.copy(specials = currentSpecials),
            expandedItemIds = _uiState.value.expandedItemIds - id
        )
    }

    fun saveMenu() {
        val homeStayId = _uiState.value.selectedHomeStay?.id ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val updatedMenu = _uiState.value.menu.copy(updatedAt = System.currentTimeMillis())
            when (val result = repository.updateMenu(homeStayId, updatedMenu)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, isUpdated = true)
                    // No need to call loadProperties() here as it triggers a full reset
                    // The local state is already updated.
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun saveItemAndMinimize(id: String) {
        saveMenu()
        toggleItemExpansion(id)
    }

    fun clearUpdatedState() {
        _uiState.value = _uiState.value.copy(isUpdated = false)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
