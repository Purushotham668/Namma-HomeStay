package com.nammahomestay.app.ui.traveler.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.data.model.Booking
import com.nammahomestay.app.ui.components.*
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelerDashboardScreen(
    navController: NavHostController,
    viewModel: TravelerDashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            NammaBottomNavBar(
                items = travelerNavItems,
                currentRoute = Screen.TravelerDashboard.route,
                onItemClick = { route ->
                    if (route != Screen.TravelerDashboard.route) {
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
            // FIXED Hero Section Spacing
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 48.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(DeepEmerald, DeepEmeraldDark)
                        )
                    )
            ) {
                // Background Pattern
                Icon(
                    imageVector = Icons.Default.Park,
                    contentDescription = null,
                    tint = IvoryWhite.copy(alpha = 0.05f),
                    modifier = Modifier
                        .size(280.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 60.dp, y = (-40).dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Namaste,",
                                style = MaterialTheme.typography.titleLarge,
                                color = IvoryWhite.copy(alpha = 0.7f)
                            )
                            Text(
                                "Traveler",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = IvoryWhite
                            )
                        }
                        Surface(
                            modifier = Modifier.size(48.dp),
                            shape = CircleShape,
                            color = IvoryWhite.copy(alpha = 0.1f),
                            onClick = { navController.navigate(Screen.TravelerSettings.route) }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Person, null, tint = IvoryWhite)
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(28.dp))
                    
                    // Search Bar with Suggestion Dropdown
                    Box(modifier = Modifier.fillMaxWidth().zIndex(10f)) {
                        Column {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .shadow(24.dp, RoundedCornerShape(18.dp)),
                                shape = RoundedCornerShape(18.dp),
                                color = IvoryWhite
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 20.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Search, null, tint = PremiumGold)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    
                                    Box(modifier = Modifier.weight(1f)) {
                                        if (uiState.searchQuery.isEmpty()) {
                                            Text(
                                                "Search for your next adventure...",
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = SlateGray
                                            )
                                        }
                                        BasicTextField(
                                            value = uiState.searchQuery,
                                            onValueChange = viewModel::onSearchQueryChanged,
                                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MidnightBlue),
                                            cursorBrush = SolidColor(DeepEmerald),
                                            modifier = Modifier.fillMaxWidth(),
                                            singleLine = true
                                        )
                                    }
                                }
                            }
                            
                            // Suggestions Dropdown
                            if (uiState.suggestions.isNotEmpty()) {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 4.dp)
                                        .shadow(16.dp, RoundedCornerShape(18.dp)),
                                    color = IvoryWhite,
                                    shape = RoundedCornerShape(18.dp)
                                ) {
                                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                        uiState.suggestions.forEach { suggestion ->
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable { viewModel.onSearchQueryChanged(suggestion) }
                                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(Icons.Default.History, null, tint = LeafAccent, modifier = Modifier.size(18.dp))
                                                Spacer(modifier = Modifier.width(12.dp))
                                                Text(
                                                    text = suggestion,
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    color = MidnightBlue,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ── Active Stay / Meal Service Card ──
            if (uiState.activeStay != null) {
                Spacer(modifier = Modifier.height(24.dp))
                ActiveStayMealCard(
                    booking = uiState.activeStay!!,
                    onOrderMeals = { 
                        navController.navigate(Screen.MealBooking.createRoute(uiState.activeStay!!.homestayId))
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Quick Actions Horizontal
            Text(
                "Quick Actions",
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = DeepEmerald
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ActionCard(
                    title = "Discover",
                    subtitle = "Nearby Stays",
                    icon = Icons.Default.Map,
                    color = LeafAccent,
                    modifier = Modifier.weight(1f)
                ) {
                    navController.navigate(Screen.Explore.route)
                }
                ActionCard(
                    title = "History",
                    subtitle = "Past Bookings",
                    icon = Icons.Default.History,
                    color = PremiumGold,
                    modifier = Modifier.weight(1f)
                ) {
                    navController.navigate(Screen.MyInquiries.route)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Popular Categories Section (Updated with new categories)
            Text(
                "Popular Categories",
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = DeepEmerald
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val categories = listOf(
                    "Forest" to Icons.Default.Forest,
                    "Mountain" to Icons.Default.Terrain,
                    "Coffee" to Icons.Default.Coffee,
                    "Heritage" to Icons.Default.Castle,
                    "Estate" to Icons.Default.Grass,
                    "River" to Icons.Default.Waves,
                    "Valley" to Icons.Default.Landscape
                )
                
                categories.forEach { (name, icon) ->
                    CategoryItem(
                        name = name,
                        icon = icon,
                        selected = uiState.selectedCategory == name,
                        onClick = { viewModel.onCategorySelected(name) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Featured/Handpicked Stays
            if (uiState.featuredStays.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "Handpicked for You",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = DeepEmerald
                        )
                        Text(
                            "Top-rated malnad hospitality",
                            style = MaterialTheme.typography.bodySmall,
                            color = SlateGray
                        )
                    }
                    TextButton(onClick = { navController.navigate(Screen.Explore.route) }) {
                        Text("View All", color = LeafAccent, fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    uiState.featuredStays.forEach { stay ->
                        FeaturedStayCard(
                            name = stay.name,
                            location = stay.address,
                            price = stay.pricing.pricePerDay,
                            rating = String.format("%.1f", stay.rating),
                            imageUrl = stay.photoUrls.firstOrNull() ?: "",
                            onClick = { navController.navigate(Screen.HomeStayDetail.createRoute(stay.id)) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(130.dp),
        shape = RoundedCornerShape(24.dp),
        color = SurfaceSoft,
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                modifier = Modifier.size(44.dp),
                shape = RoundedCornerShape(12.dp),
                color = color.copy(alpha = 0.15f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = color, modifier = Modifier.size(24.dp))
                }
            }
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = DeepEmerald)
                Text(subtitle, style = MaterialTheme.typography.labelSmall, color = SlateGray)
            }
        }
    }
}

@Composable
fun CategoryItem(name: String, icon: ImageVector, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp).clickable { onClick() }
    ) {
        Surface(
            modifier = Modifier.size(64.dp),
            shape = RoundedCornerShape(20.dp),
            color = if (selected) PremiumGold else SurfaceSoft,
            border = if (selected) null else androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = if (selected) DeepEmeraldDark else DeepEmerald, modifier = Modifier.size(28.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(name, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = if (selected) DeepEmerald else SlateGray)
    }
}

@Composable
fun FeaturedStayCard(
    name: String,
    location: String,
    price: String,
    rating: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    NammaCard(
        modifier = Modifier
            .width(280.dp)
            .clickable { onClick() }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                NammaImage(
                    url = imageUrl,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
                
                Surface(
                    modifier = Modifier.padding(12.dp).align(Alignment.TopEnd),
                    color = GlassWhite,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, null, tint = PremiumGold, modifier = Modifier.size(14.dp))
                        Text(" $rating", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MidnightBlue, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(location, style = MaterialTheme.typography.bodySmall, color = SlateGray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("₹$price", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = DeepEmerald)
                    Text("per night", style = MaterialTheme.typography.labelSmall, color = SlateGray)
                }
            }
        }
    }
}

@Composable
fun ActiveStayMealCard(booking: Booking, onOrderMeals: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(28.dp),
        color = IvoryWhite,
        shadowElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, LeafAccent.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = LeafAccent.copy(alpha = 0.1f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Restaurant, null, tint = LeafAccent, modifier = Modifier.size(24.dp))
                    }
                }
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("Enjoy Your Stay!", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
                    Text("At ${booking.homestayName}", style = MaterialTheme.typography.labelSmall, color = SlateGray)
                }
            }
            
            Spacer(Modifier.height(16.dp))
            Divider(color = DividerThin)
            Spacer(Modifier.height(16.dp))
            
            Text(
                "Fresh, home-cooked malnad delicacies are ready for you. Check out today's menu!",
                style = MaterialTheme.typography.bodyMedium,
                color = SlateGray,
                lineHeight = 20.sp
            )
            
            Spacer(Modifier.height(20.dp))
            
            NammaButton(
                text = "Order Meals Now",
                onClick = onOrderMeals,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
