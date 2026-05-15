package com.nammahomestay.app.ui.provider.inquiry
 
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nammahomestay.app.data.model.Inquiry
import com.nammahomestay.app.ui.components.NammaCard
import com.nammahomestay.app.ui.components.NammaBottomNavBar
import com.nammahomestay.app.ui.components.providerNavItems
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*
import com.nammahomestay.app.util.toFormattedDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InquiryBoxScreen(
    navController: NavHostController,
    viewModel: InquiryBoxViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        bottomBar = {
            NammaBottomNavBar(
                items = providerNavItems,
                currentRoute = Screen.InquiryBox.route,
                onItemClick = { route: String ->
                    if (route != Screen.InquiryBox.route) navController.navigate(route)
                }
            )
        },
        containerColor = IvoryWhite
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Immersive Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(DeepEmerald, DeepEmeraldDark)
                        )
                    )
            ) {
                // Decorative Icon
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = IvoryWhite.copy(alpha = 0.05f),
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 30.dp, y = (-20).dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 48.dp, start = 24.dp, end = 24.dp)
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.background(IvoryWhite.copy(alpha = 0.1f), CircleShape)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = IvoryWhite)
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = "Traveler Inbox",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = IvoryWhite
                    )
                    Text(
                        text = "Respond to stay inquiries and bookings.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = IvoryWhite.copy(alpha = 0.7f)
                    )
                }
            }

            // List Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-40).dp)
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(IvoryWhite)
            ) {
                if (uiState.isLoading && uiState.inquiries.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = DeepEmerald)
                    }
                } else if (uiState.inquiries.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Mail, null, modifier = Modifier.size(80.dp), tint = MutedText)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Your inbox is empty", style = MaterialTheme.typography.bodyLarge, color = SlateGray)
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.inquiries) { inquiry ->
                            InquiryItem(
                                inquiry = inquiry,
                                onClick = {
                                    navController.navigate(Screen.Chat.createRoute(inquiry.travelerId, inquiry.homestayId))
                                }
                            )
                        }
                        item { Spacer(modifier = Modifier.height(40.dp)) }
                    }
                }
            }
        }
        
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(padding))
    }
}

@Composable
fun InquiryItem(
    inquiry: Inquiry,
    onClick: () -> Unit
) {
    NammaCard(onClick = onClick) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = inquiry.homestayName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepEmerald
                )
            }
            Text(
                text = inquiry.timestamp.toFormattedDate(),
                style = MaterialTheme.typography.labelSmall,
                color = SlateGray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = inquiry.message,
                style = MaterialTheme.typography.bodyMedium,
                color = MidnightBlue,
                lineHeight = 22.sp,
                maxLines = 2
            )
            
            /* 
            if (inquiry.replyMessage.isNotEmpty()) {
                ...
            }
            */
        }
    }
}
