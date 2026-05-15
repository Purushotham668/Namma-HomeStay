package com.nammahomestay.app.ui.traveler.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.components.NammaCard
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.components.NammaBottomNavBar
import com.nammahomestay.app.ui.components.travelerNavItems
import com.nammahomestay.app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun HomeStayDetailScreen(
    navController: NavHostController,
    homestayId: String,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val stay = uiState.homeStays.find { it.providerId == homestayId || it.id == homestayId }
    val context = LocalContext.current

    LaunchedEffect(homestayId) {
        viewModel.loadReviews(homestayId)
    }

    Scaffold(
        containerColor = IvoryWhite,
        bottomBar = {
            Column {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 16.dp,
                    color = SuccessMintLight,
                    tonalElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Secondary Actions Row
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            // Professional Inquire Icon-Button
                            Surface(
                                onClick = { 
                                    stay?.let { 
                                        navController.navigate(Screen.SendInquiry.createRoute(it.id)) 
                                    }
                                },
                                shape = RoundedCornerShape(16.dp),
                                color = SurfaceSoft,
                                modifier = Modifier.size(56.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Default.QuestionAnswer, 
                                        null, 
                                        tint = DeepEmerald, 
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }

                            // Professional Call Icon-Button
                            Surface(
                                onClick = { /* Dialer logic */ },
                                shape = RoundedCornerShape(16.dp),
                                color = SurfaceSoft,
                                modifier = Modifier.size(56.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Default.Call, 
                                        null, 
                                        tint = DeepEmerald, 
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }

                        // Primary Action: Book Now or Order Meals
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            if (uiState.isCheckedInAtCurrent) {
                                // Once checked in, Meal Ordering becomes the primary action
                                NammaButton(
                                    text = "Order Meals",
                                    onClick = { 
                                        stay?.let { 
                                            navController.navigate(Screen.MealBooking.createRoute(it.id)) 
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            } else {
                                // Default primary action for non-checked-in users
                                NammaButton(
                                    text = "Book Your Stay",
                                    onClick = { 
                                        stay?.let { 
                                            navController.navigate(Screen.Booking.createRoute(it.id)) 
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
                NammaBottomNavBar(
                    items = travelerNavItems,
                    currentRoute = Screen.Explore.route,
                    onItemClick = { route: String ->
                        if (route != Screen.Explore.route) navController.navigate(route)
                    }
                )
            }
        }
    ) { padding ->
        if (stay == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DeepEmerald)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header Photo Gallery
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                ) {
                    val listState = androidx.compose.foundation.lazy.rememberLazyListState()
                    val currentIndex by remember {
                        derivedStateOf { listState.firstVisibleItemIndex + 1 }
                    }

                    if (stay.photoUrls.isNotEmpty()) {
                        androidx.compose.foundation.lazy.LazyRow(
                            modifier = Modifier.fillMaxSize(),
                            state = listState,
                            flingBehavior = androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior(listState)
                        ) {
                            items(stay.photoUrls.size) { index ->
                                com.nammahomestay.app.ui.components.NammaImage(
                                    url = stay.photoUrls[index],
                                    modifier = Modifier
                                        .fillParentMaxWidth()
                                        .fillMaxHeight()
                                )
                            }
                        }
                    } else {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = SurfaceSoft
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Landscape, null, modifier = Modifier.size(120.dp), tint = DividerThin)
                            }
                        }
                    }
                    
                    // Gradient Overlay
                    Box(modifier = Modifier.fillMaxSize().background(
                        Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f)))
                    ))
                    
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(16.dp).background(Color.Black.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                    }
                    
                    if (stay.photoUrls.size > 1) {
                        Surface(
                            color = Color.Black.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd)
                        ) {
                            Text(
                                text = "$currentIndex/${stay.photoUrls.size} Photos",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White
                            )
                        }
                    }
                }

                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = stay.name, 
                        style = MaterialTheme.typography.displaySmall, 
                        fontWeight = FontWeight.ExtraBold, 
                        color = DeepEmerald
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, null, tint = LeafAccent, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = stay.address, 
                                style = MaterialTheme.typography.bodyMedium, 
                                color = SlateGray,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        
                        TextButton(
                            onClick = {
                                val gmmIntentUri = Uri.parse("geo:0,0?q=${stay.latitude},${stay.longitude}(${Uri.encode(stay.name)})")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.setPackage("com.google.android.apps.maps")
                                if (mapIntent.resolveActivity(context.packageManager) != null) {
                                    context.startActivity(mapIntent)
                                } else {
                                    // Fallback to browser
                                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=${stay.latitude},${stay.longitude}")))
                                }
                            }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Map, null, tint = LeafAccent, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text("View on Maps", color = LeafAccent, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Rooms Section
                    if (stay.rooms.isNotEmpty()) {
                        Text("Available Rooms", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
                        Spacer(modifier = Modifier.height(16.dp))
                        stay.rooms.forEach { room ->
                            NammaCard(modifier = Modifier.padding(bottom = 16.dp)) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        modifier = Modifier.size(100.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        color = SurfaceSoft
                                    ) {
                                        if (room.photoUrl != null) {
                                            com.nammahomestay.app.ui.components.NammaImage(
                                                url = room.photoUrl,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        } else {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(Icons.Default.Bed, null, tint = DividerThin)
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(room.name, fontWeight = FontWeight.ExtraBold, color = MidnightBlue, style = MaterialTheme.typography.titleMedium)
                                        Text(room.capacity, color = SlateGray, style = MaterialTheme.typography.bodySmall)
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text("₹${room.price}", fontWeight = FontWeight.ExtraBold, color = DeepEmerald, style = MaterialTheme.typography.titleLarge)
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                    
                    // Description
                    Text("The Experience", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stay.description.ifEmpty { "A peaceful getaway nestled in the heart of rural Karnataka." }, 
                        style = MaterialTheme.typography.bodyLarge, 
                        color = MidnightBlue,
                        lineHeight = 26.sp
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Facilities
                    Text("Amenities", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
                    Spacer(modifier = Modifier.height(16.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        mainAxisSpacing = 10.dp,
                        crossAxisSpacing = 10.dp
                    ) {
                        stay.checklist.filter { it.value }.forEach { (item, _) ->
                            Surface(
                                color = SurfaceSoft,
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
                            ) {
                                Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.CheckCircle, null, tint = LeafAccent, modifier = Modifier.size(18.dp))
                                    Spacer(Modifier.width(8.dp))
                                    Text(item, style = MaterialTheme.typography.labelLarge, color = DeepEmerald, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Daily Menu
                    Text("Daily Authentic Menu", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
                    Spacer(modifier = Modifier.height(16.dp))
                    NammaCard(containerColor = SuccessMintLight) {
                        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            MealCategory("🍳 Breakfast", stay.menu.breakfastItems)
                            MealCategory("🍲 Lunch", stay.menu.lunchItems)
                            MealCategory("🍛 Dinner", stay.menu.dinnerItems)

                            if (stay.menu.specials.isNotEmpty()) {
                                Divider(modifier = Modifier.padding(vertical = 4.dp), color = DividerThin)
                                Text("🌟 Chef's Specials", fontWeight = FontWeight.ExtraBold, color = PremiumGold)
                                stay.menu.specials.forEach { special ->
                                    FoodItemRow(special)
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Local Spots
                    if (stay.localSpots.isNotEmpty()) {
                        Text("Local Attractions", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
                        Spacer(modifier = Modifier.height(16.dp))
                        stay.localSpots.forEach { spot ->
                            NammaCard(modifier = Modifier.padding(bottom = 12.dp)) {
                                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        modifier = Modifier.size(52.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        color = PremiumGold.copy(alpha = 0.15f)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Default.Terrain, null, tint = PremiumGold)
                                        }
                                    }
                                    Spacer(Modifier.width(16.dp))
                                    Column {
                                        Text(spot.name, fontWeight = FontWeight.ExtraBold, color = MidnightBlue, style = MaterialTheme.typography.titleMedium)
                                        Text("${spot.distance} away", color = LeafAccent, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                                    }
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(40.dp))
                    
                    // Reviews Section
                    Text("Guest Reviews", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    if (uiState.selectedStayReviews.isEmpty()) {
                        NammaCard(modifier = Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.padding(24.dp), contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.RateReview, null, tint = DividerThin, modifier = Modifier.size(48.dp))
                                    Spacer(Modifier.height(12.dp))
                                    Text("No reviews yet. Be the first to review!", color = SlateGray, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    } else {
                        uiState.selectedStayReviews.forEach { review ->
                            NammaCard(modifier = Modifier.padding(bottom = 16.dp)) {
                                Column(modifier = Modifier.padding(20.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Surface(
                                                modifier = Modifier.size(40.dp),
                                                shape = CircleShape,
                                                color = LeafAccent.copy(alpha = 0.1f)
                                            ) {
                                                Box(contentAlignment = Alignment.Center) {
                                                    Text(
                                                        review.travelerName.take(1).uppercase(),
                                                        fontWeight = FontWeight.Bold,
                                                        color = LeafAccent
                                                    )
                                                }
                                            }
                                            Spacer(Modifier.width(12.dp))
                                            Column {
                                                Text(review.travelerName, fontWeight = FontWeight.ExtraBold, color = MidnightBlue, style = MaterialTheme.typography.titleMedium)
                                                Text(SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(review.timestamp)), style = MaterialTheme.typography.labelSmall, color = SlateGray)
                                            }
                                        }
                                        
                                        Surface(
                                            color = PremiumGold.copy(alpha = 0.1f),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(Icons.Default.Star, null, tint = PremiumGold, modifier = Modifier.size(14.dp))
                                                Text(" ${review.rating}", fontWeight = FontWeight.ExtraBold, color = MidnightBlue, style = MaterialTheme.typography.labelSmall)
                                            }
                                        }
                                    }
                                    
                                    if (review.comment.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            text = review.comment, 
                                            color = MidnightBlue, 
                                            style = MaterialTheme.typography.bodyMedium,
                                            lineHeight = 22.sp
                                        )
                                    }
                                    
                                    if (review.reviewPhotos.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        androidx.compose.foundation.lazy.LazyRow(
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            items(review.reviewPhotos.size) { index ->
                                                Surface(
                                                    modifier = Modifier
                                                        .size(100.dp, 80.dp),
                                                    shape = RoundedCornerShape(12.dp),
                                                    border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
                                                ) {
                                                    com.nammahomestay.app.ui.components.NammaImage(
                                                        url = review.reviewPhotos[index],
                                                        modifier = Modifier.fillMaxSize()
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

@Composable
fun MealCategory(title: String, items: List<com.nammahomestay.app.data.model.FoodItem>) {
    if (items.isNotEmpty()) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, fontWeight = FontWeight.Bold, color = DeepEmerald)
            items.forEach { item ->
                FoodItemRow(item)
            }
        }
    }
}

@Composable
fun FoodItemRow(item: com.nammahomestay.app.data.model.FoodItem) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Professional Indian Dietary Icon
        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .size(14.dp)
                .background(Color.White, shape = RoundedCornerShape(2.dp))
                .border(
                    1.dp,
                    if (item.isVeg) LeafAccent else ErrorDark,
                    RoundedCornerShape(2.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .background(if (item.isVeg) LeafAccent else ErrorDark, shape = CircleShape)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MidnightBlue
            )
            if (item.description.isNotEmpty()) {
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = SlateGray,
                    lineHeight = 16.sp
                )
            }
        }
        
        if (item.price.isNotEmpty()) {
            Text(
                text = "₹${item.price}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = DeepEmerald
            )
        }
    }
}

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    mainAxisSpacing: androidx.compose.ui.unit.Dp = 0.dp,
    crossAxisSpacing: androidx.compose.ui.unit.Dp = 0.dp,
    content: @Composable () -> Unit
) {
    androidx.compose.ui.layout.Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeholders = measurables.map { it.measure(constraints.copy(minWidth = 0, minHeight = 0)) }
        val rows = mutableListOf<List<androidx.compose.ui.layout.Placeable>>()
        var currentRow = mutableListOf<androidx.compose.ui.layout.Placeable>()
        var currentRowWidth = 0

        placeholders.forEach { placeable ->
            if (currentRowWidth + placeable.width + mainAxisSpacing.roundToPx() > constraints.maxWidth && currentRow.isNotEmpty()) {
                rows.add(currentRow)
                currentRow = mutableListOf()
                currentRowWidth = 0
            }
            currentRow.add(placeable)
            currentRowWidth += placeable.width + mainAxisSpacing.roundToPx()
        }
        rows.add(currentRow)

        val height = rows.sumOf { it.maxOfOrNull { it.height } ?: 0 } + (rows.size - 1) * crossAxisSpacing.roundToPx()
        layout(constraints.maxWidth, height) {
            var y = 0
            rows.forEach { row ->
                var x = 0
                val rowHeight = row.maxOfOrNull { it.height } ?: 0
                row.forEach { placeable ->
                    placeable.placeRelative(x, y)
                    x += placeable.width + mainAxisSpacing.roundToPx()
                }
                y += rowHeight + crossAxisSpacing.roundToPx()
            }
        }
    }
}
