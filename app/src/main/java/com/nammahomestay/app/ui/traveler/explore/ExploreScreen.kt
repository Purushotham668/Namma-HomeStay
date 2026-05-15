package com.nammahomestay.app.ui.traveler.explore

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices
import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.ui.components.NammaBottomNavBar
import com.nammahomestay.app.ui.components.NammaCard
import com.nammahomestay.app.ui.components.NammaImage
import com.nammahomestay.app.ui.components.NammaTextField
import com.nammahomestay.app.ui.components.travelerNavItems
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavHostController,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let { viewModel.updateUserLocation(it.latitude, it.longitude) }
                }
            } catch (e: SecurityException) {}
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let { viewModel.updateUserLocation(it.latitude, it.longitude) }
            }
        } else {
            locationPermissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
    }

    Scaffold(
        bottomBar = {
            NammaBottomNavBar(
                items = travelerNavItems,
                currentRoute = Screen.Explore.route,
                onItemClick = { route ->
                    if (route != Screen.Explore.route) navController.navigate(route)
                }
            )
        },
        containerColor = IvoryWhite
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                ExploreHeader(viewModel = viewModel, uiState = uiState)
            }

            item {
                SectionHeader(
                    title = if (uiState.searchQuery.isEmpty()) "Nearby Homestays" else "Search Results",
                    subtitle = if (uiState.searchQuery.isEmpty()) "Based on your current location" else "Matching your criteria"
                )
            }

            if (uiState.isLoading && uiState.homeStays.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = DeepEmerald)
                    }
                }
            } else if (uiState.homeStays.isEmpty()) {
                item { EmptyState() }
            } else {
                items(uiState.homeStays) { stay ->
                    HomeStayListItem(
                        stay = stay,
                        userLat = uiState.userLat,
                        userLon = uiState.userLon,
                        onClick = { navController.navigate(Screen.HomeStayDetail.createRoute(stay.id)) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreHeader(
    viewModel: ExploreViewModel,
    uiState: ExploreUiState
) {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(DeepEmerald, DeepEmeraldDark)
                ),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            )
            .padding(bottom = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Find Your Perfect\nRural Escape",
            modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = IvoryWhite,
            lineHeight = 36.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        Box(modifier = Modifier.padding(horizontal = 24.dp).zIndex(2f)) {
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
                                    "Where do you want to stay?",
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
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Advanced Fading Sort Row
        FadingHorizontalRow {
            SortBadge("Nearest", Icons.Default.MyLocation, uiState.isNearestSelected) { viewModel.toggleNearest() }
            SortBadge("Top Rated", Icons.Default.AutoAwesome, uiState.isTopRatedSelected) { viewModel.toggleTopRated() }
            SortBadge("Low Price", Icons.Default.TrendingDown, uiState.priceSort == PriceSort.LOW_TO_HIGH) { viewModel.setPriceSort(PriceSort.LOW_TO_HIGH) }
            SortBadge("High Price", Icons.Default.TrendingUp, uiState.priceSort == PriceSort.HIGH_TO_LOW) { viewModel.setPriceSort(PriceSort.HIGH_TO_LOW) }
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        // Advanced Fading Category Row
        FadingHorizontalRow {
            val categories = listOf("Forest", "Mountain", "Coffee", "Heritage", "Estate", "River", "Valley") // Added more to ensure scroll
            categories.forEach { cat ->
                val isSelected = uiState.selectedCategories.contains(cat)
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.toggleCategory(cat) },
                    label = { 
                        Text(
                            text = cat, 
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium
                        ) 
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PremiumGold,
                        selectedLabelColor = DeepEmeraldDark,
                        containerColor = IvoryWhite.copy(alpha = 0.1f),
                        labelColor = IvoryWhite
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = if (isSelected) PremiumGold else IvoryWhite.copy(alpha = 0.3f),
                        enabled = true,
                        selected = isSelected
                    )
                )
            }
        }
    }
}

@Composable
fun FadingHorizontalRow(content: @Composable RowScope.() -> Unit) {
    val scrollState = rememberScrollState()
    
    // We use a Graphics Layer with a Blend Mode to fade the ACTUAL content (the tabs)
    // regardless of the background color.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            .drawWithContent {
                drawContent()
                
                val fadeWidth = 40.dp.toPx()
                val scrollValue = scrollState.value.toFloat()
                val maxScroll = scrollState.maxValue.toFloat()
                
                // Calculate alpha for left fade based on scroll progress
                val leftAlpha = (scrollValue / fadeWidth).coerceIn(0f, 1f)
                // Calculate alpha for right fade based on scroll progress
                val rightAlpha = if (maxScroll > 0) ((maxScroll - scrollValue) / fadeWidth).coerceIn(0f, 1f) else 0f

                if (leftAlpha > 0f) {
                    drawRect(
                        brush = Brush.horizontalGradient(
                            listOf(Color.Transparent, Color.Black),
                            startX = 0f,
                            endX = fadeWidth
                        ),
                        blendMode = BlendMode.DstIn
                    )
                }
                
                if (rightAlpha > 0f) {
                    drawRect(
                        brush = Brush.horizontalGradient(
                            listOf(Color.Black, Color.Transparent),
                            startX = size.width - fadeWidth,
                            endX = size.width
                        ),
                        blendMode = BlendMode.DstIn
                    )
                }
            }
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            content = content
        )
    }
}

@Composable
fun SortBadge(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = if (selected) PremiumGold else IvoryWhite.copy(alpha = 0.1f),
        shape = RoundedCornerShape(14.dp),
        border = if (selected) null else androidx.compose.foundation.BorderStroke(1.dp, IvoryWhite.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                icon, null, 
                tint = if (selected) DeepEmeraldDark else IvoryWhite,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = if (selected) DeepEmeraldDark else IvoryWhite
            )
        }
    }
}

@Composable
fun SectionHeader(title: String, subtitle: String) {
    Column(modifier = Modifier.padding(24.dp)) {
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
}

@Composable
fun HomeStayListItem(
    stay: HomeStay,
    userLat: Double?,
    userLon: Double?,
    onClick: () -> Unit
) {
    NammaCard(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(SurfaceSoft)
            ) {
                if (stay.photoUrls.isNotEmpty()) {
                    NammaImage(
                        url = stay.photoUrls.first(),
                        modifier = Modifier.fillMaxSize()
                    )
                }
                
                Box(modifier = Modifier.fillMaxSize().background(
                    brush = Brush.verticalGradient(listOf(Color.Transparent, MidnightBlue.copy(alpha = 0.4f)))
                ))
                
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatusChip(
                        text = if (stay.pricing.isAvailable) "Available" else "Full",
                        color = if (stay.pricing.isAvailable) LeafAccent else ErrorDark
                    )
                    
                    Surface(
                        color = GlassWhite,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Star, null, tint = PremiumGold, modifier = Modifier.size(14.dp))
                            Text(
                                text = " ${stay.rating.takeIf { it > 0 } ?: "4.8"}", 
                                style = MaterialTheme.typography.labelSmall, 
                                fontWeight = FontWeight.ExtraBold,
                                color = MidnightBlue
                            )
                        }
                    }
                }

                if (userLat != null && userLon != null && stay.latitude != 0.0) {
                    val dist = calculateDistance(userLat, userLon, stay.latitude, stay.longitude)
                    Surface(
                        color = DeepEmeraldDark.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(12.dp).align(Alignment.BottomStart)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.NearMe, null, tint = PremiumGold, modifier = Modifier.size(12.dp))
                            Text(
                                text = String.format("%.1f km", dist),
                                color = IvoryWhite,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                
                // OCCUPIED OVERLAY
                if (stay.isOccupied) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Surface(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "OCCUPIED",
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stay.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = DeepEmerald,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, null, tint = LeafAccent, modifier = Modifier.size(14.dp))
                            Text(text = stay.address, style = MaterialTheme.typography.bodySmall, color = SlateGray, maxLines = 1)
                        }
                    }
                    Text(
                        text = "₹${stay.pricing.pricePerDay}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = DeepEmerald
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = DividerThin, thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.RestaurantMenu, null, tint = PremiumGold, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = "Today's Special", style = MaterialTheme.typography.labelSmall, color = SlateGray)
                        Text(
                            text = stay.menu.specials.firstOrNull()?.name?.ifEmpty { "Traditional Malnad Feast" } ?: "Traditional Malnad Feast",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MidnightBlue
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatusChip(text: String, color: Color) {
    Surface(
        color = color,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            color = IvoryWhite,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.SearchOff, null, modifier = Modifier.size(80.dp), tint = MutedText)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "No Homestays Found", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = SlateGray)
        Text(text = "Try adjusting your search or filters", style = MaterialTheme.typography.bodySmall, color = MutedText)
    }
}

private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val r = 6371 
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = kotlin.math.sin(dLat / 2) * kotlin.math.sin(dLat / 2) +
            kotlin.math.cos(Math.toRadians(lat1)) * kotlin.math.cos(Math.toRadians(lat2)) *
            kotlin.math.sin(dLon / 2) * kotlin.math.sin(dLon / 2)
    val c = 2 * kotlin.math.atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))
    return r * c
}
