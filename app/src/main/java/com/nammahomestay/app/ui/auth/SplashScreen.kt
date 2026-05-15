package com.nammahomestay.app.ui.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1200),
        label = "splash_alpha"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        
        // Wait for animation to start and a brief moment for auth check
        delay(800)

        val currentUser = viewModel.currentUser
        if (currentUser != null) {
            // Initiate role fetch if not already present
            viewModel.fetchUserRole(currentUser.uid)
            
            // Safety timeout: if role fetch takes too long, navigate to login or handle error
            delay(4000) 
            if (uiState.userRole.isEmpty() && !uiState.isLoading) {
                // If still stuck, force check or redirect
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        } else {
            // Short delay for logo visibility
            delay(500)
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    LaunchedEffect(uiState.isLoggedIn, uiState.userRole) {
        if (uiState.isLoggedIn && uiState.userRole.isNotEmpty()) {
            val dest = if (uiState.userRole.equals("provider", ignoreCase = true))
                Screen.ProviderDashboard.route else Screen.TravelerDashboard.route
            navController.navigate(dest) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DeepEmerald, EmeraldMedium, LeafAccent))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.alpha(alphaAnim)
        ) {
            Surface(
                modifier = Modifier.size(120.dp),
                shape = RoundedCornerShape(36.dp),
                color = IvoryWhite.copy(0.15f),
                tonalElevation = 0.dp
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "App Logo",
                    tint = IvoryWhite,
                    modifier = Modifier.padding(24.dp).size(72.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "ನಮ್ಮ HomeStay",
                style = MaterialTheme.typography.displayMedium,
                color = IvoryWhite,
                letterSpacing = (-1).sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Premium Rural Experiences",
                style = MaterialTheme.typography.bodyLarge,
                color = PremiumGold,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
    }
}
