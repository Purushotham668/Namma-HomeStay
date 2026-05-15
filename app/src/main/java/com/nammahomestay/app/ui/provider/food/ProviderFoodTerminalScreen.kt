package com.nammahomestay.app.ui.provider.food

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.nammahomestay.app.data.model.FoodBooking
import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.data.model.OrderItem
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.FoodRepository
import com.nammahomestay.app.data.repository.HomeStayRepository
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProviderFoodTerminalUiState(
    val isLoading: Boolean = false,
    val homeStays: List<HomeStay> = emptyList(),
    val selectedHomeStay: HomeStay? = null,
    val cartItems: Map<String, Int> = emptyMap(),
    val guestName: String = "",
    val error: String? = null,
    val isSuccess: String? = null
)

@HiltViewModel
class ProviderFoodTerminalViewModel @Inject constructor(
    private val homestayRepository: HomeStayRepository,
    private val foodRepository: FoodRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProviderFoodTerminalUiState())
    val uiState: StateFlow<ProviderFoodTerminalUiState> = _uiState.asStateFlow()

    init {
        loadProperties()
    }

    private fun loadProperties() {
        viewModelScope.launch {
            val uid = authRepository.currentUser?.uid ?: return@launch
            val result = homestayRepository.getProviderHomeStays(uid)
            if (result is Resource.Success) {
                _uiState.update { it.copy(homeStays = result.data, selectedHomeStay = result.data.firstOrNull()) }
            }
        }
    }

    fun selectProperty(stay: HomeStay) {
        _uiState.update { it.copy(selectedHomeStay = stay, cartItems = emptyMap()) }
    }

    fun updateGuestName(name: String) {
        _uiState.update { it.copy(guestName = name) }
    }

    fun updateCart(itemId: String, delta: Int) {
        _uiState.update { state ->
            val currentQty = state.cartItems[itemId] ?: 0
            val newQty = (currentQty + delta).coerceAtLeast(0)
            val newCart = state.cartItems.toMutableMap()
            if (newQty == 0) newCart.remove(itemId) else newCart[itemId] = newQty
            state.copy(cartItems = newCart)
        }
    }

    fun createCashBooking() {
        val state = _uiState.value
        val homestay = state.selectedHomeStay ?: return
        
        if (state.guestName.isBlank()) {
            _uiState.update { it.copy(error = "Enter Guest Name") }
            return
        }

        val orderItems = mutableListOf<OrderItem>()
        var total = 0.0

        fun addItems(list: List<com.nammahomestay.app.data.model.FoodItem>, category: String) {
            list.forEach { item ->
                val qty = state.cartItems[item.id] ?: 0
                if (qty > 0) {
                    val p = item.price.toDoubleOrNull() ?: 0.0
                    orderItems.add(OrderItem(item.id, item.name, category, p, qty, item.isVeg))
                    total += (p * qty)
                }
            }
        }

        addItems(homestay.menu.breakfastItems, "Breakfast")
        addItems(homestay.menu.lunchItems, "Lunch")
        addItems(homestay.menu.dinnerItems, "Dinner")
        addItems(homestay.menu.specials, "Special")

        if (orderItems.isEmpty()) {
            _uiState.update { it.copy(error = "Select at least one dish") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val booking = FoodBooking(
                travelerName = state.guestName,
                providerId = authRepository.currentUser?.uid ?: "",
                homestayId = homestay.id,
                homestayName = homestay.name,
                items = orderItems,
                totalPrice = total,
                status = "Pending", // Cash bookings are pending until served/paid
                paymentMethod = "Cash",
                qrCodeData = java.util.UUID.randomUUID().toString(),
                generatedBy = "Provider"
            )
            val result = foodRepository.createFoodBooking(booking)
            if (result is Resource.Success) {
                _uiState.update { it.copy(isLoading = false, isSuccess = booking.id, cartItems = emptyMap(), guestName = "") }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Failed to create ticket") }
            }
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
    fun clearSuccess() = _uiState.update { it.copy(isSuccess = null) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderFoodTerminalScreen(
    navController: NavHostController,
    viewModel: ProviderFoodTerminalViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(uiState.isSuccess) {
        uiState.isSuccess?.let { bookingId ->
            navController.navigate(Screen.FoodTicket.createRoute(bookingId))
            viewModel.clearSuccess()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Food Terminal", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = IvoryWhite)
            )
        },
        containerColor = IvoryWhite
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(
                modifier = Modifier.weight(1f).verticalScroll(scrollState).padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Property Selector
                Text("Select Homestay", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                androidx.compose.foundation.lazy.LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(uiState.homeStays.size) { i ->
                        val stay = uiState.homeStays[i]
                        FilterChip(
                            selected = uiState.selectedHomeStay?.id == stay.id,
                            onClick = { viewModel.selectProperty(stay) },
                            label = { Text(stay.name) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = DeepEmerald, selectedLabelColor = Color.White)
                        )
                    }
                }

                // Guest Details
                OutlinedTextField(
                    value = uiState.guestName,
                    onValueChange = viewModel::updateGuestName,
                    label = { Text("Guest Name (for Cash Payment)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.Person, null, tint = DeepEmerald) }
                )

                Divider(color = DividerThin)

                // Menu Items
                uiState.selectedHomeStay?.let { stay ->
                    MealTerminalSection("🍳 Breakfast", stay.menu.breakfastItems, uiState.cartItems, viewModel::updateCart)
                    MealTerminalSection("🍲 Lunch", stay.menu.lunchItems, uiState.cartItems, viewModel::updateCart)
                    MealTerminalSection("🍛 Dinner", stay.menu.dinnerItems, uiState.cartItems, viewModel::updateCart)
                }
            }

            // Bottom Action
            if (uiState.cartItems.isNotEmpty()) {
                Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = 16.dp, color = Color.White) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total Billing", color = SlateGray)
                            Text("₹${calculateTotal(uiState)}", fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.titleLarge, color = DeepEmerald)
                        }
                        Spacer(Modifier.height(16.dp))
                        NammaButton(
                            text = "Generate Cash Ticket",
                            onClick = viewModel::createCashBooking,
                            isLoading = uiState.isLoading,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MealTerminalSection(title: String, items: List<com.nammahomestay.app.data.model.FoodItem>, cart: Map<String, Int>, onUpdate: (String, Int) -> Unit) {
    if (items.isNotEmpty()) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(title, fontWeight = FontWeight.Bold, color = DeepEmerald)
            items.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth().background(SurfaceSoft, RoundedCornerShape(16.dp)).padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(item.name, fontWeight = FontWeight.Bold, color = MidnightBlue)
                        Text("₹${item.price}", style = MaterialTheme.typography.labelSmall, color = SlateGray)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { onUpdate(item.id, -1) }, modifier = Modifier.size(32.dp)) {
                            Icon(Icons.Default.Remove, null, tint = DeepEmerald)
                        }
                        Text(text = (cart[item.id] ?: 0).toString(), modifier = Modifier.padding(horizontal = 8.dp), fontWeight = FontWeight.Bold)
                        IconButton(onClick = { onUpdate(item.id, 1) }, modifier = Modifier.size(32.dp)) {
                            Icon(Icons.Default.Add, null, tint = DeepEmerald)
                        }
                    }
                }
            }
        }
    }
}

private fun calculateTotal(state: ProviderFoodTerminalUiState): Double {
    var total = 0.0
    state.selectedHomeStay?.menu?.let { menu ->
        fun sum(list: List<com.nammahomestay.app.data.model.FoodItem>) = list.forEach { 
            total += (it.price.toDoubleOrNull() ?: 0.0) * (state.cartItems[it.id] ?: 0) 
        }
        sum(menu.breakfastItems); sum(menu.lunchItems); sum(menu.dinnerItems); sum(menu.specials)
    }
    return total
}
