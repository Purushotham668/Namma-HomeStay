package com.nammahomestay.app.ui.traveler.food

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.nammahomestay.app.data.model.FoodBooking
import com.nammahomestay.app.data.repository.FoodRepository
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FoodTicketViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {
    private val _booking = MutableStateFlow<FoodBooking?>(null)
    val booking: StateFlow<FoodBooking?> = _booking.asStateFlow()

    fun loadBooking(bookingId: String) {
        viewModelScope.launch {
            val result = repository.getBookingById(bookingId)
            if (result is Resource.Success) {
                _booking.value = result.data
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodTicketScreen(
    navController: NavHostController,
    bookingId: String,
    viewModel: FoodTicketViewModel = hiltViewModel()
) {
    val booking by viewModel.booking.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(bookingId) {
        viewModel.loadBooking(bookingId)
    }

    val qrBitmap = remember(booking?.qrCodeData) {
        booking?.qrCodeData?.let { generateQrCode("nammafood:$it", 512) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFF1F8E9), IvoryWhite)))
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Food Ticket", fontWeight = FontWeight.ExtraBold) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, null)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
                )
            },
            containerColor = Color.Transparent
        ) { padding ->
            if (booking == null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = DeepEmerald)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 24.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(24.dp))

                    // Status Badge
                    Surface(
                        color = if (booking?.status == "Served") LeafAccent.copy(alpha = 0.1f) else PremiumGold.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = booking?.status?.uppercase() ?: "",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (booking?.status == "Served") LeafAccent else PremiumGold
                        )
                    }

                    Spacer(Modifier.height(32.dp))

                    // Premium Food Ticket Card
                    com.nammahomestay.app.ui.traveler.booking.TicketCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("MEAL PASS", style = MaterialTheme.typography.labelSmall, color = LeafAccent, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                                    Text(booking?.homestayName ?: "HomeStay", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
                                }
                                Icon(Icons.Default.Fastfood, null, tint = PremiumGold, modifier = Modifier.size(32.dp))
                            }

                            Spacer(Modifier.height(24.dp))
                            Divider(color = DividerThin)
                            Spacer(Modifier.height(24.dp))

                            // Order Summary
                            Text("ORDER SUMMARY", style = MaterialTheme.typography.labelSmall, color = SlateGray, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))
                            booking?.items?.forEach { item ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("${item.quantity}x ${item.itemName}", style = MaterialTheme.typography.bodyMedium, color = MidnightBlue, fontWeight = FontWeight.Medium)
                                    Text("₹${item.price * item.quantity}", style = MaterialTheme.typography.bodyMedium, color = DeepEmerald, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(Modifier.height(24.dp))
                            Divider(color = DividerThin)
                            Spacer(Modifier.height(24.dp))

                            // QR Section
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    if (qrBitmap != null) {
                                        Surface(
                                            modifier = Modifier.size(200.dp),
                                            shape = RoundedCornerShape(16.dp),
                                            color = Color.White,
                                            shadowElevation = 4.dp
                                        ) {
                                            Image(
                                                bitmap = qrBitmap.asImageBitmap(),
                                                contentDescription = "Food QR",
                                                modifier = Modifier.padding(12.dp)
                                            )
                                        }
                                    }
                                    Spacer(Modifier.height(16.dp))
                                    Text("SCAN TO VERIFY MEAL", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.ExtraBold, color = MidnightBlue.copy(alpha = 0.4f), letterSpacing = 1.5.sp)
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(40.dp))

                    NammaButton(
                        text = "Back to Explore",
                        onClick = { 
                            navController.navigate(Screen.Explore.route) {
                                popUpTo(Screen.Explore.route) { inclusive = true }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("CLOSE TICKET", color = DeepEmerald, fontWeight = FontWeight.ExtraBold)
                    }
                    Spacer(Modifier.height(40.dp))
                }
            }
        }
    }
}

private fun generateQrCode(text: String, size: Int): Bitmap? {
    return try {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, size, size)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        bitmap
    } catch (e: Exception) {
        null
    }
}
