package com.nammahomestay.app.ui.traveler.settings

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nammahomestay.app.ui.auth.AuthViewModel
import com.nammahomestay.app.ui.components.*
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TravelerSettingsScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileSettingsViewModel = hiltViewModel()
) {
    val profileState by profileViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(profileState.isSuccess) {
        if (profileState.isSuccess) {
            snackbarHostState.showSnackbar("✓ Profile saved to Firebase")
            profileViewModel.clearStatus()
        }
    }

    LaunchedEffect(profileState.error) {
        profileState.error?.let {
            snackbarHostState.showSnackbar("⚠ $it")
            profileViewModel.clearStatus()
        }
    }

    Scaffold(
        bottomBar = {
            NammaBottomNavBar(
                items = travelerNavItems,
                currentRoute = Screen.TravelerSettings.route,
                onItemClick = { route: String ->
                    if (route != Screen.TravelerSettings.route) {
                        navController.navigate(route)
                    }
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
        ) {
            // Premium Profile Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(DeepEmerald, DeepEmeraldDark)
                        )
                    )
            ) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .offset(x = (-50).dp, y = (-50).dp)
                        .background(PremiumGold.copy(alpha = 0.1f), CircleShape)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(100.dp)
                            .background(PremiumGold.copy(alpha = 0.2f), CircleShape)
                            .padding(4.dp)
                            .background(IvoryWhite, CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(60.dp),
                            tint = DeepEmerald
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = profileState.fullName.ifEmpty { "Namma Traveler" },
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = IvoryWhite
                    )
                    Text(
                        text = profileState.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = IvoryWhite.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Live sync status badge
                    AnimatedContent(
                        targetState = when {
                            profileState.isLoading -> "loading"
                            profileState.isSaving  -> "saving"
                            profileState.isDirty   -> "pending"
                            else                   -> "synced"
                        },
                        transitionSpec = { fadeIn() with fadeOut() }
                    ) { status ->
                        val (icon, label, tint) = when (status) {
                            "saving"  -> Triple(Icons.Default.CloudUpload, "Saving…",     PremiumGold)
                            "pending" -> Triple(Icons.Default.Edit,        "Unsaved changes", IvoryWhite.copy(alpha = 0.6f))
                            "loading" -> Triple(Icons.Default.CloudSync,   "Loading…",    IvoryWhite.copy(alpha = 0.6f))
                            else      -> Triple(Icons.Default.CloudDone,   "Firebase synced", LeafAccent)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(icon, null, tint = tint, modifier = Modifier.size(16.dp))
                            Text(label, style = MaterialTheme.typography.labelMedium, color = tint)
                        }
                    }
                }
            }

            // Main Content — negative offset card-overlap effect
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-30).dp)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(IvoryWhite)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                // Live-update info banner
                Surface(
                    color = LeafAccent.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, LeafAccent.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(Icons.Default.Bolt, null, tint = LeafAccent, modifier = Modifier.size(20.dp))
                        Column {
                            Text(
                                "Real-Time Sync Active",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = LeafAccent
                            )
                            Text(
                                "Every field change is auto-saved to Firebase within 1.5 seconds",
                                style = MaterialTheme.typography.bodySmall,
                                color = SlateGray
                            )
                        }
                    }
                }

                // Section: Personal Information
                ModernSettingsSection(
                    title = "Personal Portfolio",
                    subtitle = "Manage your identity and contact details"
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        ElegantTextField(
                            value = profileState.fullName,
                            onValueChange = profileViewModel::updateFullName,
                            label = "Full Name",
                            icon = Icons.Default.Badge
                        )
                        ElegantTextField(
                            value = profileState.phoneNumber,
                            onValueChange = profileViewModel::updatePhoneNumber,
                            label = "Phone Number",
                            icon = Icons.Default.PhoneIphone
                        )
                        ElegantTextField(
                            value = profileState.dob,
                            onValueChange = profileViewModel::updateDob,
                            label = "Date of Birth",
                            icon = Icons.Default.Event
                        )
                    }
                }

                // Section: Preferences
                ModernSettingsSection(
                    title = "Experience Preferences",
                    subtitle = "Tailor the app to your liking"
                ) {
                    NammaCard {
                        Column(modifier = Modifier.padding(8.dp)) {
                            PreferenceToggle(
                                title = "Push Notifications",
                                subtitle = "Stay updated on your stays",
                                icon = Icons.Default.NotificationsActive,
                                checked = profileState.notificationsEnabled,
                                onCheckedChange = profileViewModel::toggleNotifications
                            )

                            Divider(color = DividerThin, modifier = Modifier.padding(horizontal = 16.dp))

                            PreferenceItem(
                                title = "App Language",
                                value = profileState.language,
                                icon = Icons.Default.Translate,
                                onClick = { /* Show language picker */ }
                            )
                        }
                    }
                }

                // Section: Safety
                ModernSettingsSection(
                    title = "Safety & Security",
                    subtitle = "Protect your account and data"
                ) {
                    NammaCard {
                        Column(modifier = Modifier.padding(8.dp)) {
                            PreferenceItem(
                                title = "Change Password",
                                value = "Last updated 2 months ago",
                                icon = Icons.Default.VpnKey,
                                onClick = { /* Navigate to password change */ }
                            )
                        }
                    }
                }

                // Action Buttons
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Manual save button (still available for explicit saves)
                    NammaButton(
                        text = if (profileState.isSaving) "Saving to Firebase…" else "Save Profile Now",
                        onClick = profileViewModel::saveProfile,
                        isLoading = profileState.isSaving,
                        modifier = Modifier.fillMaxWidth()
                    )

                    TextButton(
                        onClick = {
                            authViewModel.logout()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.textButtonColors(contentColor = ErrorDark)
                    ) {
                        Icon(Icons.Default.Logout, null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Sign Out from Namma HomeStay", fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun ModernSettingsSection(
    title: String,
    subtitle: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column(modifier = Modifier.padding(start = 4.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = DeepEmerald
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = SlateGray
            )
        }
        content()
    }
}

@Composable
fun ElegantTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector
) {
    NammaTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        leadingIcon = { Icon(icon, null, tint = PremiumGold, modifier = Modifier.size(20.dp)) }
    )
}

@Composable
fun PreferenceToggle(
    title: String,
    subtitle: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = DeepEmerald.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = DeepEmerald, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = SlateGray)
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = IvoryWhite,
                checkedTrackColor = LeafAccent,
                uncheckedThumbColor = IvoryWhite,
                uncheckedTrackColor = DividerThin
            )
        )
    }
}

@Composable
fun PreferenceItem(
    title: String,
    value: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = DeepEmerald.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = DeepEmerald, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Text(value, style = MaterialTheme.typography.bodySmall, color = SlateGray)
            }
        }
        Icon(Icons.Default.ChevronRight, null, tint = MutedText)
    }
}
