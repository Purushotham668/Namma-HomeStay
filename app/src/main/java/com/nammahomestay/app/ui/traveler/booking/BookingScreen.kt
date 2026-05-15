package com.nammahomestay.app.ui.traveler.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Add
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
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.components.NammaCard
import com.nammahomestay.app.ui.theme.*
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    onBack: () -> Unit,
    onProceedToPayment: (String) -> Unit,
    viewModel: BookingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val dateRangePickerState = rememberDateRangePickerState()

    LaunchedEffect(dateRangePickerState.selectedStartDateMillis, dateRangePickerState.selectedEndDateMillis) {
        viewModel.updateDates(
            dateRangePickerState.selectedStartDateMillis,
            dateRangePickerState.selectedEndDateMillis
        )
    }

    LaunchedEffect(uiState.bookingId) {
        uiState.bookingId?.let { onProceedToPayment(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Complete Your Booking", fontWeight = FontWeight.ExtraBold, color = MidnightBlue) },
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
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = DeepEmerald)
                }
            } else if (uiState.homeStay != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Property Summary Header
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = SurfaceSoft,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text(
                                text = uiState.homeStay!!.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = DeepEmerald
                            )
                            Text(
                                text = uiState.homeStay!!.address,
                                style = MaterialTheme.typography.bodyMedium,
                                color = SlateGray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Date Selection
                    Text(
                        text = "Pick Your Dates",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 24.dp),
                        color = MidnightBlue
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    MaterialTheme(
                        colorScheme = MaterialTheme.colorScheme.copy(
                            primary = DeepEmerald,
                            onPrimary = Color.White,
                            surface = Color.White,
                            onSurface = MidnightBlue,
                            secondaryContainer = DeepEmerald.copy(alpha = 0.1f),
                            onSecondaryContainer = MidnightBlue
                        )
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
                        ) {
                            DateRangePicker(
                                state = dateRangePickerState,
                                modifier = Modifier.height(450.dp),
                                title = null,
                                headline = null,
                                showModeToggle = false
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Guests Section
                    NammaCard(modifier = Modifier.padding(horizontal = 24.dp)) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Group, null, tint = DeepEmerald, modifier = Modifier.size(20.dp))
                                    Spacer(Modifier.width(8.dp))
                                    Text("Guests", fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
                                }
                                Text("Including children", style = MaterialTheme.typography.labelSmall, color = SlateGray)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { if (uiState.guests > 1) viewModel.updateGuests(uiState.guests - 1) },
                                    modifier = Modifier.background(SurfaceSoft, CircleShape).size(36.dp)
                                ) {
                                    Icon(Icons.Default.Remove, null, modifier = Modifier.size(18.dp))
                                }
                                
                                Text(
                                    text = "${uiState.guests}",
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold
                                )

                                IconButton(
                                    onClick = { viewModel.updateGuests(uiState.guests + 1) },
                                    modifier = Modifier.background(DeepEmerald, CircleShape).size(36.dp)
                                ) {
                                    Icon(Icons.Default.Add, null, tint = Color.White, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Price Breakdown
                    NammaCard(modifier = Modifier.padding(horizontal = 24.dp)) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text("Fare Summary", fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            val pricePerDay = uiState.homeStay!!.pricing.pricePerDay.toDoubleOrNull() ?: 0.0
                            val days = if (uiState.checkInDate != null && uiState.checkOutDate != null) {
                                TimeUnit.DAYS.convert(uiState.checkOutDate!! - uiState.checkInDate!!, TimeUnit.MILLISECONDS)
                            } else 0L

                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Base Price ($days nights)", color = SlateGray)
                                Text("₹${pricePerDay * days}", color = MidnightBlue, fontWeight = FontWeight.Bold)
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(color = DividerThin)
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Total Amount", fontWeight = FontWeight.ExtraBold, color = DeepEmerald, style = MaterialTheme.typography.titleLarge)
                                Text("₹${uiState.totalPrice}", fontWeight = FontWeight.ExtraBold, color = DeepEmerald, style = MaterialTheme.typography.titleLarge)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    NammaButton(
                        text = "Continue to Payment",
                        onClick = { viewModel.createBooking() },
                        enabled = uiState.checkInDate != null && uiState.checkOutDate != null && uiState.totalPrice > 0,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .height(56.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(40.dp))
                }
            } else if (uiState.error != null) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.Info, null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(uiState.error!!, color = MaterialTheme.colorScheme.error, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = onBack) { Text("Go Back") }
                }
            }
        }
    }
}
