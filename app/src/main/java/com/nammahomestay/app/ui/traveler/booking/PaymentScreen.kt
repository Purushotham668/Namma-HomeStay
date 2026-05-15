package com.nammahomestay.app.ui.traveler.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Security
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
import androidx.compose.foundation.BorderStroke
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.components.NammaTextField
import com.nammahomestay.app.ui.components.NammaCard
import com.nammahomestay.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    bookingId: String,
    onBack: () -> Unit,
    onPaymentSuccess: (String) -> Unit,
    viewModel: BookingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    var cardNumber by remember { mutableStateOf("") }
    var expiry by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var nameOnCard by remember { mutableStateOf("") }

    var upiId by remember { mutableStateOf("") }

    LaunchedEffect(uiState.paymentSuccess) {
        if (uiState.paymentSuccess) {
            onPaymentSuccess(bookingId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Secure Checkout", fontWeight = FontWeight.ExtraBold, color = MidnightBlue) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Price Summary Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfaceSoft,
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Total Amount to Pay", color = SlateGray, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "₹${uiState.totalPrice}", 
                        color = DeepEmerald, 
                        style = MaterialTheme.typography.displayMedium, 
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Security, null, tint = LeafAccent, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("100% Safe & Secure Payments", style = MaterialTheme.typography.labelSmall, color = SlateGray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Select Payment Method",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MidnightBlue,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MethodCard(
                    title = "Card",
                    icon = Icons.Default.CreditCard,
                    isSelected = uiState.paymentMethod == "Credit Card",
                    onClick = { viewModel.updatePaymentMethod("Credit Card") },
                    modifier = Modifier.weight(1f)
                )
                MethodCard(
                    title = "UPI",
                    icon = Icons.Default.QrCode,
                    isSelected = uiState.paymentMethod == "UPI",
                    onClick = { viewModel.updatePaymentMethod("UPI") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Payment Form
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                if (uiState.paymentMethod == "Credit Card") {
                    NammaTextField(
                        value = cardNumber,
                        onValueChange = { cardNumber = it },
                        label = "Card Number",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        NammaTextField(
                            value = expiry,
                            onValueChange = { expiry = it },
                            label = "MM/YY",
                            modifier = Modifier.weight(1f)
                        )
                        NammaTextField(
                            value = cvv,
                            onValueChange = { cvv = it },
                            label = "CVV",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    NammaTextField(
                        value = nameOnCard,
                        onValueChange = { nameOnCard = it },
                        label = "Name on Card",
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    NammaTextField(
                        value = upiId,
                        onValueChange = { upiId = it },
                        label = "UPI ID (e.g., name@okicici)",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                if (uiState.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = DeepEmerald)
                    }
                } else {
                    NammaButton(
                        text = "Pay Now",
                        onClick = { viewModel.processPayment(bookingId) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )
                }
                
                uiState.error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun MethodCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(90.dp),
        color = if (isSelected) DeepEmerald else Color.White,
        shape = RoundedCornerShape(20.dp),
        border = if (isSelected) null else BorderStroke(1.5.dp, DividerThin),
        shadowElevation = if (isSelected) 8.dp else 0.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = if (isSelected) IvoryWhite else SlateGray,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontWeight = FontWeight.ExtraBold,
                color = if (isSelected) IvoryWhite else MidnightBlue,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
