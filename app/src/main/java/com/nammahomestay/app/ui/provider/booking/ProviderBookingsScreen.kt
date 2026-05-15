package com.nammahomestay.app.ui.provider.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nammahomestay.app.data.model.Booking
import com.nammahomestay.app.ui.theme.*
import com.nammahomestay.app.ui.components.NammaCard
import com.nammahomestay.app.ui.components.NammaButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderBookingsScreen(
    onBack: () -> Unit,
    viewModel: ProviderBookingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Guest Bookings", fontWeight = FontWeight.ExtraBold, color = MidnightBlue) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = IvoryWhite
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Stats Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfaceSoft,
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            ) {
                Row(
                    modifier = Modifier.padding(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatItem(
                        label = "Active", 
                        count = uiState.bookings.count { it.status == "CheckedIn" }.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    StatItem(
                        label = "Upcoming", 
                        count = uiState.bookings.count { it.status == "Confirmed" }.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    StatItem(
                        label = "Total", 
                        count = uiState.bookings.size.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = DeepEmerald)
                }
            } else if (uiState.bookings.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.History, null, modifier = Modifier.size(80.dp), tint = MutedText)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("No guest bookings yet", color = SlateGray, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.bookings) { booking ->
                        ProviderBookingCard(
                            booking = booking,
                            onCheckoutClick = { viewModel.markBookingCompleted(it) }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(40.dp)) }
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, count: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = count, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = DeepEmerald)
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = SlateGray)
        }
    }
}

@Composable
fun ProviderBookingCard(booking: Booking, onCheckoutClick: (String) -> Unit) {
    NammaCard {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Booking #${booking.id.takeLast(6).uppercase()}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = SlateGray
                    )
                    Text(
                        text = "Guest ID: ${booking.travelerId.takeLast(6).uppercase()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MidnightBlue
                    )
                }
                
                // Professional Status Badge
                Surface(
                    color = when(booking.status) {
                        "Confirmed" -> LeafAccent.copy(alpha = 0.1f)
                        "CheckedIn" -> DeepEmerald.copy(alpha = 0.1f)
                        "Pending" -> PremiumGold.copy(alpha = 0.1f)
                        "Completed" -> SurfaceSoft
                        else -> ErrorDark.copy(alpha = 0.1f)
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = booking.status.uppercase(),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = when(booking.status) {
                            "Confirmed" -> LeafAccent
                            "CheckedIn" -> DeepEmerald
                            "Pending" -> PremiumGold
                            "Completed" -> MidnightBlue
                            else -> ErrorDark
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = DividerThin)
            Spacer(modifier = Modifier.height(20.dp))
            
            val dateFormat = SimpleDateFormat("EEE, MMM dd", Locale.getDefault())
            val checkInStr = dateFormat.format(Date(booking.checkInDate))
            val checkOutStr = dateFormat.format(Date(booking.checkOutDate))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CalendarMonth, null, tint = DeepEmerald, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Dates", style = MaterialTheme.typography.labelSmall, color = SlateGray)
                    }
                    Text(text = "$checkInStr - $checkOutStr", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MidnightBlue)
                }
                
                Column(modifier = Modifier.weight(0.6f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Group, null, tint = DeepEmerald, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Guests", style = MaterialTheme.typography.labelSmall, color = SlateGray)
                    }
                    Text(text = "${booking.guests} People", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MidnightBlue)
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Total Payout", style = MaterialTheme.typography.labelSmall, color = SlateGray)
                    Text("₹${booking.totalPrice}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = DeepEmerald)
                }

                if (booking.status == "CheckedIn") {
                    NammaButton(
                        text = "Mark Checkout",
                        onClick = { onCheckoutClick(booking.id) },
                        modifier = Modifier.height(48.dp)
                    )
                }
            }
        }
    }
}
