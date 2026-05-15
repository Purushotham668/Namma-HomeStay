package com.nammahomestay.app.ui.traveler.inquiry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.components.NammaCard
import com.nammahomestay.app.ui.components.NammaTextField
import com.nammahomestay.app.ui.components.NammaBottomNavBar
import com.nammahomestay.app.ui.components.travelerNavItems
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendInquiryScreen(
    navController: NavHostController,
    homestayId: String,
    viewModel: SendInquiryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(homestayId) {
        viewModel.init(homestayId)
    }

    LaunchedEffect(uiState.isSent) {
        if (uiState.isSent) {
            val travelerId = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid ?: ""
            navController.navigate(Screen.Chat.createRoute(travelerId, homestayId)) {
                popUpTo(Screen.HomeStayDetail.route) { inclusive = false }
            }
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Direct Inquiry", color = IvoryWhite, fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = IvoryWhite)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = DeepEmerald)
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
            // Premium Immersive Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(DeepEmerald, EmeraldMedium, IvoryWhite)
                        )
                    )
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Surface(
                        modifier = Modifier.size(72.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = IvoryWhite.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, IvoryWhite.copy(alpha = 0.3f))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Mail, null, modifier = Modifier.size(36.dp), tint = IvoryWhite)
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Contacting ${uiState.homeStay?.name ?: "Host"}", 
                        style = MaterialTheme.typography.headlineSmall, 
                        fontWeight = FontWeight.ExtraBold, 
                        color = IvoryWhite,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Share your specific requirements or questions directly with the provider.", 
                        style = MaterialTheme.typography.bodyMedium, 
                        color = IvoryWhite.copy(alpha = 0.8f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Personal Details Section
                Text(
                    "Your Information", 
                    style = MaterialTheme.typography.titleMedium, 
                    fontWeight = FontWeight.Bold, 
                    color = MidnightBlue,
                    modifier = Modifier.padding(top = 8.dp)
                )
                
                NammaCard {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        NammaTextField(
                            value = uiState.name,
                            onValueChange = viewModel::updateName,
                            label = "Full Name",
                            leadingIcon = { Icon(Icons.Default.Person, null, tint = DeepEmerald) }
                        )
                        NammaTextField(
                            value = uiState.phone,
                            onValueChange = viewModel::updatePhone,
                            label = "Mobile Number",
                            leadingIcon = { Icon(Icons.Default.Call, null, tint = DeepEmerald) }
                        )
                    }
                }

                // Message Section
                Text(
                    "Message to Host", 
                    style = MaterialTheme.typography.titleMedium, 
                    fontWeight = FontWeight.Bold, 
                    color = MidnightBlue
                )

                NammaCard {
                    Column(modifier = Modifier.padding(20.dp)) {
                        NammaTextField(
                            value = uiState.message,
                            onValueChange = viewModel::updateMessage,
                            label = "How can we help you?",
                            minLines = 5,
                            maxLines = 15,
                            singleLine = false,
                            placeholder = "Mention dates, special dietary needs, or travel arrangements..."
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                NammaButton(
                    text = "Send Inquiry Now",
                    isLoading = uiState.isLoading,
                    onClick = { viewModel.sendInquiry(homestayId) },
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
        }
        SnackbarHost(snackbarHostState)
    }
}
