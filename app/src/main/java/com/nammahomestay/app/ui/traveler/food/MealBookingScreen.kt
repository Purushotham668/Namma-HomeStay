package com.nammahomestay.app.ui.traveler.food

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.nammahomestay.app.data.model.DailyMenu
import com.nammahomestay.app.data.model.FoodBooking
import com.nammahomestay.app.data.model.FoodItem
import com.nammahomestay.app.data.model.OrderItem
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.BookingRepository
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

data class MealBookingUiState(
    val isLoading: Boolean = false,
    val isVerifyingStay: Boolean = true,
    val hasStayBooking: Boolean = false,
    val menu: DailyMenu = DailyMenu(),
    val homestayName: String = "",
    val cartItems: Map<String, Int> = emptyMap(),
    val isBookingSuccess: String? = null,
    val error: String? = null
)

@HiltViewModel
class MealBookingViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val homestayRepository: HomeStayRepository,
    private val bookingRepository: BookingRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MealBookingUiState())
    val uiState: StateFlow<MealBookingUiState> = _uiState.asStateFlow()

    fun loadMenu(homestayId: String) {
        val travelerId = authRepository.currentUser?.uid ?: ""
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isVerifyingStay = true) }
            
            // 1. Fetch Homestay Profile first to get providerId
            val profileResult = homestayRepository.getHomeProfile(homestayId)
            if (profileResult is Resource.Success) {
                val stay = profileResult.data
                _uiState.update { it.copy(
                    menu = stay.menu,
                    homestayName = stay.name,
                    // Store providerId in a hidden state if needed, but we'll use it in bookMeals
                ) }

                // 2. Verify if user has a stay booking at this property
                val bookingsResult = bookingRepository.getTravelerBookingsOnce(travelerId)
                val hasStay = if (bookingsResult is Resource.Success) {
                    bookingsResult.data.any { 
                        it.homestayId == homestayId && (it.status == "Confirmed" || it.status == "CheckedIn") 
                    }
                } else false

                _uiState.update { it.copy(
                    isLoading = false,
                    isVerifyingStay = false,
                    hasStayBooking = hasStay
                ) }
            } else if (profileResult is Resource.Error) {
                _uiState.update { it.copy(isLoading = false, isVerifyingStay = false, error = profileResult.message) }
            }
        }
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

    fun bookMeals(homestayId: String, paymentMethod: String) {
        val state = _uiState.value
        val traveler = authRepository.currentUser
        
        val orderItems = mutableListOf<OrderItem>()
        var totalPrice = 0.0

        // Helper to add items from a list
        fun addItems(list: List<FoodItem>, category: String) {
            list.forEach { item ->
                val qty = state.cartItems[item.id] ?: 0
                if (qty > 0) {
                    val price = item.price.toDoubleOrNull() ?: 0.0
                    orderItems.add(OrderItem(
                        itemId = item.id,
                        itemName = item.name,
                        category = category,
                        price = price,
                        quantity = qty,
                        isVeg = item.isVeg
                    ))
                    totalPrice += (price * qty)
                }
            }
        }

        addItems(state.menu.breakfastItems, "Breakfast")
        addItems(state.menu.lunchItems, "Lunch")
        addItems(state.menu.dinnerItems, "Dinner")
        addItems(state.menu.specials, "Special")

        if (orderItems.isEmpty()) {
            _uiState.update { it.copy(error = "Please select at least one item") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Fetch homestay profile to get the correct providerId
            val homestayResult = homestayRepository.getHomeProfile(homestayId)
            val providerId = if (homestayResult is Resource.Success) homestayResult.data.providerId else ""

            val booking = FoodBooking(
                travelerId = traveler?.uid ?: "",
                travelerName = traveler?.displayName ?: "Guest",
                providerId = providerId,
                homestayId = homestayId,
                homestayName = state.homestayName,
                items = orderItems,
                totalPrice = totalPrice,
                status = if (paymentMethod == "Online") "Paid" else "Pending",
                paymentMethod = paymentMethod,
                qrCodeData = "nammafood:${java.util.UUID.randomUUID()}",
                generatedBy = "Traveler"
            )

            val result = foodRepository.createFoodBooking(booking)
            if (result is Resource.Success) {
                _uiState.update { it.copy(isLoading = false, isBookingSuccess = booking.id) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Booking failed") }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealBookingScreen(
    navController: NavHostController,
    homestayId: String,
    viewModel: MealBookingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(homestayId) {
        viewModel.loadMenu(homestayId)
    }

    LaunchedEffect(uiState.isBookingSuccess) {
        uiState.isBookingSuccess?.let { bookingId ->
            navController.navigate(Screen.FoodTicket.createRoute(bookingId)) {
                popUpTo(Screen.MealBooking.route) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(uiState.homestayName.ifEmpty { "Meal Service" }, fontWeight = FontWeight.ExtraBold)
                        Text("Authentic Home Cooked Meals", style = MaterialTheme.typography.labelSmall, color = SlateGray)
                    }
                },
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
        when {
            uiState.isLoading || uiState.isVerifyingStay -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = DeepEmerald)
                }
            }
            !uiState.hasStayBooking -> {
                NoStayBookingView(onBookStay = {
                    navController.navigate(Screen.Booking.createRoute(homestayId))
                })
            }
            else -> {
                Column(modifier = Modifier.fillMaxSize().padding(padding)) {
                    Column(
                        modifier = Modifier.weight(1f).verticalScroll(scrollState).padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        MealCategorySection("🍳 Breakfast", uiState.menu.breakfastItems, uiState.cartItems, viewModel::updateCart)
                        MealCategorySection("🍲 Lunch", uiState.menu.lunchItems, uiState.cartItems, viewModel::updateCart)
                        MealCategorySection("🍛 Dinner", uiState.menu.dinnerItems, uiState.cartItems, viewModel::updateCart)
                        if (uiState.menu.specials.isNotEmpty()) {
                            MealCategorySection("🌟 Chef's Specials", uiState.menu.specials, uiState.cartItems, viewModel::updateCart)
                        }
                    }

                    // Bottom Checkout Bar
                    if (uiState.cartItems.isNotEmpty()) {
                        CheckoutBar(uiState, viewModel, homestayId)
                    }
                }
            }
        }
    }
}

@Composable
fun NoStayBookingView(onBookStay: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(100.dp),
            color = PremiumGold.copy(alpha = 0.1f),
            shape = CircleShape
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Hotel, null, tint = PremiumGold, modifier = Modifier.size(48.dp))
            }
        }
        Spacer(Modifier.height(24.dp))
        Text(
            "Stay Booking Required",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = MidnightBlue
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "Meal services are exclusively available for guests with an active stay booking at this property.",
            style = MaterialTheme.typography.bodyMedium,
            color = SlateGray,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(40.dp))
        NammaButton(
            text = "Book Your Stay Now",
            onClick = onBookStay,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MealCategorySection(
    title: String, 
    items: List<FoodItem>, 
    cart: Map<String, Int>,
    onUpdate: (String, Int) -> Unit
) {
    if (items.isNotEmpty()) {
        Column {
            Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
            Spacer(Modifier.height(16.dp))
            items.forEach { item ->
                FoodOrderCard(item, cart[item.id] ?: 0, onUpdate)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun FoodOrderCard(item: FoodItem, quantity: Int, onUpdate: (String, Int) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    DietaryIcon(item.isVeg)
                    Spacer(Modifier.width(8.dp))
                    Text(item.name, fontWeight = FontWeight.Bold, color = MidnightBlue)
                }
                if (item.description.isNotEmpty()) {
                    Text(item.description, style = MaterialTheme.typography.bodySmall, color = SlateGray, maxLines = 2)
                }
                Spacer(Modifier.height(4.dp))
                Text("₹${item.price}", fontWeight = FontWeight.ExtraBold, color = DeepEmerald)
            }

            // Quantity Controls
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(SurfaceSoft, RoundedCornerShape(12.dp)).padding(4.dp)
            ) {
                IconButton(onClick = { onUpdate(item.id, -1) }, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Remove, null, tint = DeepEmerald, modifier = Modifier.size(18.dp))
                }
                Text(
                    text = quantity.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontWeight = FontWeight.Bold,
                    color = MidnightBlue
                )
                IconButton(onClick = { onUpdate(item.id, 1) }, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Add, null, tint = DeepEmerald, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
fun DietaryIcon(isVeg: Boolean) {
    Box(
        modifier = Modifier
            .size(14.dp)
            .background(Color.White, shape = RoundedCornerShape(2.dp))
            .border(1.dp, if (isVeg) LeafAccent else ErrorDark, RoundedCornerShape(2.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(7.dp).background(if (isVeg) LeafAccent else ErrorDark, shape = CircleShape))
    }
}

@Composable
fun CheckoutBar(uiState: MealBookingUiState, viewModel: MealBookingViewModel, homestayId: String) {
    var showPaymentSheet by remember { mutableStateOf(false) }
    
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = IvoryWhite,
        shadowElevation = 16.dp,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                val totalQty = uiState.cartItems.values.sum()
                Text("$totalQty Items Selected", style = MaterialTheme.typography.labelSmall, color = SlateGray)
                Text("Total: ₹${calculateTotal(uiState)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = DeepEmerald)
            }
            
            NammaButton(
                text = "Checkout",
                onClick = { showPaymentSheet = true },
                modifier = Modifier.width(160.dp)
            )
        }
    }

    if (showPaymentSheet) {
        AlertDialog(
            onDismissRequest = { showPaymentSheet = false },
            title = { Text("Choose Payment Method", fontWeight = FontWeight.Bold) },
            text = { Text("How would you like to pay for your delicious home-cooked meal?") },
            confirmButton = {
                Button(
                    onClick = { viewModel.bookMeals(homestayId, "Online") },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepEmerald)
                ) {
                    Text("Pay Online")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.bookMeals(homestayId, "Cash") }) {
                    Text("Pay at Property", color = SlateGray)
                }
            }
        )
    }
}

private fun calculateTotal(state: MealBookingUiState): Double {
    var total = 0.0
    fun sum(list: List<FoodItem>) = list.forEach { 
        total += (it.price.toDoubleOrNull() ?: 0.0) * (state.cartItems[it.id] ?: 0) 
    }
    sum(state.menu.breakfastItems)
    sum(state.menu.lunchItems)
    sum(state.menu.dinnerItems)
    sum(state.menu.specials)
    return total
}
