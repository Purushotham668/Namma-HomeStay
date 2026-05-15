package com.nammahomestay.app.ui.provider.food

import android.content.Context
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.nammahomestay.app.data.model.FoodBooking
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.FoodRepository
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.theme.*
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class FoodAnalyticsUiState(
    val isLoading: Boolean = false,
    val bookings: List<FoodBooking> = emptyList(),
    val filteredBookings: List<FoodBooking> = emptyList(),
    val selectedFilter: String = "Day", // Day, Week, Month
    val totalRevenue: Double = 0.0,
    val breakfastCount: Int = 0,
    val lunchCount: Int = 0,
    val dinnerCount: Int = 0,
    val error: String? = null
)

@HiltViewModel
class FoodAnalyticsViewModel @Inject constructor(
    private val repository: FoodRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FoodAnalyticsUiState())
    val uiState: StateFlow<FoodAnalyticsUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = repository.getProviderFoodBookings(uid)
            if (result is Resource.Success) {
                val bookings = result.data
                _uiState.update { it.copy(bookings = bookings, isLoading = false) }
                applyFilter("Day")
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun applyFilter(filter: String) {
        val now = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        
        val filtered = when (filter) {
            "Day" -> {
                _uiState.value.bookings.filter { isSameDay(it.bookingDate, now) }
            }
            "Week" -> {
                val weekAgo = now - (7 * 24 * 60 * 60 * 1000L)
                _uiState.value.bookings.filter { it.bookingDate >= weekAgo }
            }
            "Month" -> {
                val monthAgo = now - (30 * 24 * 60 * 60 * 1000L)
                _uiState.value.bookings.filter { it.bookingDate >= monthAgo }
            }
            else -> _uiState.value.bookings
        }

        var rev = 0.0
        var b = 0; var l = 0; var d = 0
        filtered.forEach { booking ->
            rev += booking.totalPrice
            booking.items.forEach { item ->
                when (item.category) {
                    "Breakfast" -> b += item.quantity
                    "Lunch" -> l += item.quantity
                    "Dinner" -> d += item.quantity
                }
            }
        }

        _uiState.update { it.copy(
            selectedFilter = filter,
            filteredBookings = filtered,
            totalRevenue = rev,
            breakfastCount = b,
            lunchCount = l,
            dinnerCount = d
        ) }
    }

    private fun isSameDay(d1: Long, d2: Long): Boolean {
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return sdf.format(Date(d1)) == sdf.format(Date(d2))
    }

    fun exportToCsv(context: Context) {
        val state = _uiState.value
        val fileName = "FoodReport_${state.selectedFilter}_${System.currentTimeMillis()}.csv"
        val csvHeader = "Booking ID,Guest,Date,Total,Status,Payment,Items\n"
        
        try {
            val file = File(context.getExternalFilesDir(null), fileName)
            val fos = FileOutputStream(file)
            fos.write(csvHeader.toByteArray())
            
            state.filteredBookings.forEach { b ->
                val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(b.bookingDate))
                val items = b.items.joinToString(";") { "${it.quantity}x ${it.itemName}" }
                val row = "${b.id},${b.travelerName},$date,${b.totalPrice},${b.status},${b.paymentMethod},\"$items\"\n"
                fos.write(row.toByteArray())
            }
            fos.close()
            Toast.makeText(context, "Report saved to ${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Export failed", Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodAnalyticsScreen(
    navController: NavHostController,
    viewModel: FoodAnalyticsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Food Analytics", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.exportToCsv(context) }) {
                        Icon(Icons.Default.Download, null, tint = DeepEmerald)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = IvoryWhite)
            )
        },
        containerColor = IvoryWhite
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())) {
            // Filter Tabs
            Row(modifier = Modifier.fillMaxWidth().padding(24.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Day", "Week", "Month").forEach { filter ->
                    FilterChip(
                        selected = uiState.selectedFilter == filter,
                        onClick = { viewModel.applyFilter(filter) },
                        label = { Text(filter) },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = DeepEmerald, selectedLabelColor = Color.White)
                    )
                }
            }

            // Summary Revenue Card (Glassmorphism inspired)
            Surface(
                modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                color = DeepEmerald,
                shadowElevation = 8.dp
            ) {
                Box(modifier = Modifier.background(Brush.verticalGradient(listOf(DeepEmerald, DeepEmeraldDark))).padding(32.dp)) {
                    Column {
                        Text("Total Earnings", color = IvoryWhite.copy(alpha = 0.7f), style = MaterialTheme.typography.labelLarge)
                        Text("₹${uiState.totalRevenue}", color = Color.White, style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.ExtraBold)
                        Spacer(Modifier.height(8.dp))
                        Text("${uiState.filteredBookings.size} Orders Processed", color = PremiumGold, fontWeight = FontWeight.Bold)
                    }
                    Icon(Icons.Default.TrendingUp, null, modifier = Modifier.align(Alignment.TopEnd).size(48.dp), tint = IvoryWhite.copy(alpha = 0.1f))
                }
            }

            Spacer(Modifier.height(32.dp))

            // Stats Grid
            Row(modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                AnalyticsStatCard("Breakfast", uiState.breakfastCount.toString(), Icons.Default.WbSunny, LeafAccent, Modifier.weight(1f))
                AnalyticsStatCard("Lunch", uiState.lunchCount.toString(), Icons.Default.Restaurant, PremiumGold, Modifier.weight(1f))
                AnalyticsStatCard("Dinner", uiState.dinnerCount.toString(), Icons.Default.NightsStay, MidnightBlue, Modifier.weight(1f))
            }

            Spacer(Modifier.height(32.dp))

            // Recent Orders List
            Text("Detailed Transactions", modifier = Modifier.padding(horizontal = 24.dp), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
            Spacer(Modifier.height(16.dp))
            
            uiState.filteredBookings.forEach { booking ->
                TransactionItem(booking)
            }
            
            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
fun AnalyticsStatCard(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier) {
    Surface(modifier = modifier, shape = RoundedCornerShape(24.dp), color = color.copy(alpha = 0.1f), border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.2f))) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = color, modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
            Text(label, style = MaterialTheme.typography.labelSmall, color = SlateGray)
        }
    }
}

@Composable
fun TransactionItem(booking: FoodBooking) {
    Surface(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp).fillMaxWidth(), shape = RoundedCornerShape(20.dp), color = Color.White, border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(40.dp), shape = CircleShape, color = if (booking.status == "Served") LeafAccent.copy(alpha = 0.1f) else ErrorDark.copy(alpha = 0.1f)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(if (booking.status == "Served") Icons.Default.Check else Icons.Default.Pending, null, tint = if (booking.status == "Served") LeafAccent else ErrorDark, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(booking.travelerName, fontWeight = FontWeight.Bold, color = MidnightBlue)
                Text(SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(booking.bookingDate)), style = MaterialTheme.typography.labelSmall, color = SlateGray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("₹${booking.totalPrice}", fontWeight = FontWeight.ExtraBold, color = DeepEmerald)
                Text(booking.paymentMethod, style = MaterialTheme.typography.labelSmall, color = if (booking.paymentMethod == "Cash") ErrorDark else LeafAccent)
            }
        }
    }
}
