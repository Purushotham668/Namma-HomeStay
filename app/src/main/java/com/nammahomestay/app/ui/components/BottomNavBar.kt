package com.nammahomestay.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.CardTravel
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.nammahomestay.app.ui.theme.*

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val providerNavItems = listOf(
    BottomNavItem("Status", Icons.Default.Dashboard, "provider_dashboard"),
    BottomNavItem("Menu", Icons.Default.Restaurant, "daily_menu"),
    BottomNavItem("Inbox", Icons.Default.MailOutline, "inquiry_box"),
    BottomNavItem("Profile", Icons.Default.Person, "provider_settings")
)

val travelerNavItems = listOf(
    BottomNavItem("Home", Icons.Default.Dashboard, "traveler_dashboard"),
    BottomNavItem("Explore", Icons.Default.Explore, "explore"),
    BottomNavItem("Bookings", Icons.Default.CardTravel, "my_bookings"),
    BottomNavItem("Inquiries", Icons.Default.MailOutline, "my_inquiries"),
    BottomNavItem("Profile", Icons.Default.Person, "traveler_settings")
)

@Composable
fun NammaBottomNavBar(
    items: List<BottomNavItem>,
    currentRoute: String,
    onItemClick: (String) -> Unit
) {
    NavigationBar(
        containerColor = DeepEmerald,
        contentColor = IvoryWhite,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item.route) },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.label)
                },
                label = {
                    Text(text = item.label, style = MaterialTheme.typography.labelSmall)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = IvoryWhite,
                    selectedTextColor = IvoryWhite,
                    unselectedIconColor = IvoryWhite.copy(alpha = 0.5f),
                    unselectedTextColor = IvoryWhite.copy(alpha = 0.5f),
                    indicatorColor = EmeraldMedium
                )
            )
        }
    }
}
