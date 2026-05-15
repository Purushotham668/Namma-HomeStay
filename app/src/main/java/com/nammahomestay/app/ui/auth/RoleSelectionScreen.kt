package com.nammahomestay.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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
import androidx.navigation.NavHostController
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*

@Composable
fun RoleSelectionScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedRole by remember { mutableStateOf("traveler") }

    LaunchedEffect(uiState.isLoggedIn, uiState.userRole) {
        if (uiState.isLoggedIn && uiState.userRole.isNotEmpty()) {
            val destination = if (uiState.userRole.equals("provider", ignoreCase = true))
                Screen.ProviderDashboard.route else Screen.TravelerDashboard.route
            navController.navigate(destination) {
                popUpTo(Screen.RoleSelection.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DeepEmerald, EmeraldMedium)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(90.dp),
                shape = RoundedCornerShape(28.dp),
                color = GlassWhite,
                tonalElevation = 8.dp
            ) {
                Icon(
                    Icons.Default.Home, 
                    contentDescription = null, 
                    tint = DeepEmerald, 
                    modifier = Modifier.padding(18.dp).size(54.dp)
                )
            }
            
            Spacer(Modifier.height(24.dp))
            
            Text(
                "Final Step", 
                style = MaterialTheme.typography.displayMedium, 
                color = IvoryWhite,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Text(
                "How would you like to use the app?", 
                style = MaterialTheme.typography.bodyLarge, 
                color = IvoryWhite.copy(0.7f),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(Modifier.height(48.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                color = IvoryWhite,
                shadowElevation = 16.dp
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        "I want to be a...", 
                        style = MaterialTheme.typography.titleLarge, 
                        color = DeepEmerald,
                        fontWeight = FontWeight.ExtraBold
                    )

                    RoleCard(
                        title = "Traveler",
                        subtitle = "Find and book unique rural homestays",
                        isSelected = selectedRole == "traveler",
                        onClick = { selectedRole = "traveler" }
                    )

                    RoleCard(
                        title = "HomeStay Owner",
                        subtitle = "List your property and host travelers",
                        isSelected = selectedRole == "provider",
                        onClick = { selectedRole = "provider" }
                    )

                    Spacer(Modifier.height(16.dp))

                    NammaButton(
                        text = "Finish Registration",
                        isLoading = uiState.isLoading,
                        onClick = {
                            val name = uiState.pendingGoogleUser?.displayName ?: "User"
                            viewModel.completeGoogleRegistration(name, "", selectedRole)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RoleCard(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) SurfaceSoft else Color.Transparent,
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) DeepEmerald else DividerThin
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = DeepEmerald)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    title, 
                    style = MaterialTheme.typography.titleMedium, 
                    color = DeepEmerald,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    subtitle, 
                    style = MaterialTheme.typography.bodySmall, 
                    color = SlateGray
                )
            }
        }
    }
}
