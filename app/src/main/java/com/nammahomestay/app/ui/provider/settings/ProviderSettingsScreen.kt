package com.nammahomestay.app.ui.provider.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nammahomestay.app.ui.auth.AuthViewModel
import com.nammahomestay.app.ui.components.*
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*
import com.nammahomestay.app.ui.traveler.settings.ProfileSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderSettingsScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileSettingsViewModel = hiltViewModel()
) {
    val profileState by profileViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(profileState.isSuccess) {
        if (profileState.isSuccess) {
            snackbarHostState.showSnackbar("Settings updated successfully")
            profileViewModel.clearStatus()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Account Settings", color = IvoryWhite, fontWeight = FontWeight.ExtraBold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = DeepEmerald)
            )
        },
        bottomBar = {
            NammaBottomNavBar(
                items = providerNavItems,
                currentRoute = Screen.ProviderSettings.route,
                onItemClick = { route ->
                    if (route != Screen.ProviderSettings.route) navController.navigate(route)
                }
            )
        },
        containerColor = IvoryWhite,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Section: Personal
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    "Owner Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepEmerald
                )
                NammaCard {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        NammaTextField(
                            value = profileState.fullName,
                            onValueChange = profileViewModel::updateFullName,
                            label = "Full Name",
                            leadingIcon = { Icon(Icons.Default.Person, null, tint = DeepEmerald) }
                        )
                        NammaTextField(
                            value = profileState.email,
                            onValueChange = { },
                            label = "Email",
                            leadingIcon = { Icon(Icons.Default.Email, null, tint = DeepEmerald) },
                            enabled = false
                        )
                        NammaTextField(
                            value = profileState.phoneNumber,
                            onValueChange = profileViewModel::updatePhoneNumber,
                            label = "Contact Number",
                            leadingIcon = { Icon(Icons.Default.Phone, null, tint = DeepEmerald) }
                        )
                    }
                }
            }

            // Section: Preferences
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    "App Preferences",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepEmerald
                )
                NammaCard {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.NotificationsActive, null, tint = DeepEmerald)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Booking Notifications", style = MaterialTheme.typography.bodyLarge)
                            }
                            Switch(
                                checked = profileState.notificationsEnabled,
                                onCheckedChange = profileViewModel::toggleNotifications,
                                colors = SwitchDefaults.colors(checkedThumbColor = PremiumGold, checkedTrackColor = DeepEmerald)
                            )
                        }
                    }
                }
            }

            // Save Button
            NammaButton(
                text = "Save Changes",
                onClick = profileViewModel::saveProfile,
                isLoading = profileState.isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            // Logout
            NammaButton(
                text = "Logout",
                onClick = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                containerColor = ErrorDark.copy(alpha = 0.1f),
                contentColor = ErrorDark,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    icon: ImageVector,
    iconColor: Color = DeepEmerald,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { 
            Text(
                title, 
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MidnightBlue
            ) 
        },
        leadingContent = { 
            Surface(
                color = SurfaceSoft,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
                }
            }
        },
        trailingContent = {
            Icon(Icons.Default.ChevronRight, null, tint = SlateGray)
        },
        modifier = Modifier.clickable { onClick() },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
    )
}
