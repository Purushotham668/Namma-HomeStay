package com.nammahomestay.app.ui.provider.dashboard

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nammahomestay.app.ui.components.NammaBottomNavBar
import com.nammahomestay.app.ui.components.providerNavItems
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*

data class DashboardCard(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val description: String
)

private val dashboardCards = listOf(
    DashboardCard("Home Profile", Icons.Default.Home, Screen.HomeProfile.route, "Manage stay details"),
    DashboardCard("Menu Update", Icons.Default.Restaurant, Screen.DailyMenu.route, "Update daily meals"),
    DashboardCard("Pricing", Icons.Default.Payments, Screen.Pricing.route, "Rates & availability"),
    DashboardCard("Inbox", Icons.Default.Message, Screen.InquiryBox.route, "Traveler messages"),
    DashboardCard("Scan QR", Icons.Default.QrCodeScanner, Screen.ProviderQRScanner.route, "Verify bookings"),
    DashboardCard("Food Terminal", Icons.Default.Kitchen, Screen.ProviderFoodTerminal.route, "Cash meal bookings"),
    DashboardCard("Verify Food", Icons.Default.NoFood, Screen.FoodVerification.route, "Verify meal tickets"),
    DashboardCard("Food Analytics", Icons.Default.Assessment, Screen.FoodAnalytics.route, "Revenue & reports"),
    DashboardCard("Local Guide", Icons.Default.Map, Screen.LocalGuide.route, "Nearby attractions"),
    DashboardCard("Settings", Icons.Default.Settings, Screen.ProviderSettings.route, "Account settings")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderDashboardScreen(
    navController: NavHostController,
    viewModel: ProviderDashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            NammaBottomNavBar(
                items = providerNavItems,
                currentRoute = Screen.ProviderDashboard.route,
                onItemClick = { route ->
                    if (route != Screen.ProviderDashboard.route) {
                        navController.navigate(route)
                    }
                }
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
            ProviderHeroSection(navController, uiState.providerName)
            
            // Overlapping content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-40).dp)
                    .padding(horizontal = 24.dp)
                    .zIndex(2f)
            ) {
                QuickStatsRow(
                    rating = uiState.rating,
                    inquiryCount = uiState.inquiryCount,
                    status = uiState.status
                )
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "Management Hub",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepEmerald
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                ProviderDashboardGrid(navController = navController)
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
private fun ProviderHeroSection(navController: NavHostController, providerName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .clip(RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 48.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(DeepEmerald, DeepEmeraldDark)
                )
            )
    ) {
        // Decorative background icon
        Icon(
            imageVector = Icons.Default.MapsHomeWork,
            contentDescription = null,
            tint = IvoryWhite.copy(alpha = 0.05f),
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-20).dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "Welcome back,",
                        style = MaterialTheme.typography.titleMedium,
                        color = IvoryWhite.copy(alpha = 0.7f)
                    )
                    Text(
                        text = providerName,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = IvoryWhite
                    )
                }
                
                // Profile Circle Avatar
                Surface(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { navController.navigate(Screen.ProviderSettings.route) },
                    shape = CircleShape,
                    color = IvoryWhite.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, PremiumGold)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = providerName.take(2).uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = IvoryWhite
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your homestay is currently highly visible.",
                style = MaterialTheme.typography.bodyMedium,
                color = PremiumGold
            )
        }
    }
}

@Composable
private fun QuickStatsRow(
    rating: String,
    inquiryCount: String,
    status: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(24.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        color = IvoryWhite
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItem(value = rating, label = "Rating", icon = Icons.Default.Star, iconTint = PremiumGold)
            Divider(modifier = Modifier.height(40.dp).width(1.dp), color = DividerThin)
            StatItem(value = inquiryCount, label = "Inquiries", icon = Icons.Default.Mail, iconTint = LeafAccent)
            Divider(modifier = Modifier.height(40.dp).width(1.dp), color = DividerThin)
            StatItem(value = status, label = "Status", icon = Icons.Default.Visibility, iconTint = DeepEmerald)
        }
    }
}

@Composable
private fun StatItem(value: String, label: String, icon: ImageVector, iconTint: androidx.compose.ui.graphics.Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MidnightBlue
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = SlateGray
        )
    }
}

@Composable
private fun ProviderDashboardGrid(navController: NavHostController) {
    val rows = (dashboardCards.size + 1) / 2
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        for (row in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val index1 = row * 2
                val index2 = row * 2 + 1
                
                Box(modifier = Modifier.weight(1f)) {
                    if (index1 < dashboardCards.size) {
                        DashboardActionCard(card = dashboardCards[index1]) {
                            navController.navigate(dashboardCards[index1].route)
                        }
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    if (index2 < dashboardCards.size) {
                        DashboardActionCard(card = dashboardCards[index2]) {
                            navController.navigate(dashboardCards[index2].route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardActionCard(card: DashboardCard, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        color = SurfaceSoft,
        border = androidx.compose.foundation.BorderStroke(1.dp, IvoryWhite.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = IvoryWhite,
                shadowElevation = 8.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = card.icon,
                        contentDescription = card.title,
                        tint = DeepEmerald,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = card.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MidnightBlue,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = card.description,
                style = MaterialTheme.typography.labelSmall,
                color = SlateGray,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }
    }
}
