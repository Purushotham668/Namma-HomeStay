package com.nammahomestay.app.ui.traveler.booking

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.navigation.NavHostController
import com.nammahomestay.app.ui.components.NammaBottomNavBar
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.components.travelerNavItems
import com.nammahomestay.app.ui.navigation.Screen
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nammahomestay.app.data.model.Booking
import com.nammahomestay.app.ui.components.NammaImage
import com.nammahomestay.app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBookingsScreen(
    navController: NavHostController,
    onBookingClick: (String) -> Unit,
    onReviewClick: (Booking) -> Unit,
    viewModel: MyBookingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Upcoming", "Past", "Cancelled")
    
    var showCancelDialog by remember { mutableStateOf(false) }
    var showCheckoutDialog by remember { mutableStateOf(false) }
    var bookingToManage by remember { mutableStateOf<Booking?>(null) }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color.White)) {
                TopAppBar(
                    title = { 
                        Text(
                            "My Journeys", 
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold, 
                            color = MidnightBlue 
                        ) 
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
                
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.White,
                    contentColor = DeepEmerald,
                    indicator = { tabPositions ->
                        if (selectedTab < tabPositions.size) {
                            TabRowDefaults.Indicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                                color = LeafAccent
                            )
                        }
                    },
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 14.sp
                                )
                            },
                            selectedContentColor = DeepEmerald,
                            unselectedContentColor = SlateGray
                        )
                    }
                }
            }
        },
        bottomBar = {
            NammaBottomNavBar(
                items = travelerNavItems,
                currentRoute = Screen.MyBookings.route,
                onItemClick = { route ->
                    if (route != Screen.MyBookings.route) {
                        navController.navigate(route)
                    }
                }
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        val currentTime = System.currentTimeMillis()
        val filteredBookings = when (selectedTab) {
            0 -> uiState.bookings.filter { 
                (it.status == "Pending" || it.status == "Confirmed" || it.status == "CheckedIn") && 
                currentTime < it.checkOutDate 
            }
            1 -> uiState.bookings.filter { 
                it.status == "Completed" || it.status == "Reviewed" ||
                ((it.status == "CheckedIn" || it.status == "Confirmed") && currentTime >= it.checkOutDate)
            }
            else -> uiState.bookings.filter { it.status == "Cancelled" }
        }

        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = LeafAccent)
                }
            } else if (filteredBookings.isEmpty()) {
                EmptyBookingsState(tabs[selectedTab])
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(filteredBookings, key = { it.id }) { booking ->
                        ModernBookingCard(
                            booking = booking,
                            currentTime = currentTime,
                            onClick = { onBookingClick(booking.id) },
                            onReviewClick = { onReviewClick(booking) },
                            onCancelClick = { 
                                bookingToManage = booking
                                showCancelDialog = true
                            },
                            onCheckoutClick = {
                                bookingToManage = booking
                                showCheckoutDialog = true
                            }
                        )
                    }
                }
            }
        }

        if (showCancelDialog && bookingToManage != null) {
            CancelConfirmationDialog(
                onConfirm = {
                    viewModel.cancelBooking(bookingToManage!!.id)
                    showCancelDialog = false
                    bookingToManage = null
                },
                onDismiss = {
                    showCancelDialog = false
                    bookingToManage = null
                }
            )
        }

        if (showCheckoutDialog && bookingToManage != null) {
            CheckoutConfirmationDialog(
                onConfirm = {
                    viewModel.markCheckout(bookingToManage!!.id)
                    showCheckoutDialog = false
                    bookingToManage = null
                },
                onDismiss = {
                    showCheckoutDialog = false
                    bookingToManage = null
                }
            )
        }
    }
}

@Composable
fun ModernBookingCard(
    booking: Booking,
    currentTime: Long,
    onClick: () -> Unit,
    onReviewClick: () -> Unit,
    onCancelClick: () -> Unit,
    onCheckoutClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            // Header Image Section
            Box(modifier = Modifier.height(160.dp).fillMaxWidth()) {
                NammaImage(
                    url = booking.homestayImageUrl,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Status Badge Overlay
                StatusBadge(
                    status = booking.status,
                    modifier = Modifier.padding(16.dp).align(Alignment.TopEnd)
                )

                // Date Overlay
                Surface(
                    color = GlassWhite,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(16.dp).align(Alignment.BottomStart)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.CalendarMonth, null, tint = DeepEmerald, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = formatDateRange(booking.checkInDate, booking.checkOutDate),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = DeepEmerald
                        )
                    }
                }
            }

            // Details Section
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = booking.homestayName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MidnightBlue,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.LocationOn, null, tint = SlateGray, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = booking.homestayAddress.ifEmpty { "Rural Karnataka" },
                        style = MaterialTheme.typography.bodySmall,
                        color = SlateGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                Divider(color = DividerThin)
                
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = SurfaceSoft,
                            shape = CircleShape,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Outlined.Group, null, tint = DeepEmerald, modifier = Modifier.size(16.dp))
                            }
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "${booking.guests} Guests",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MidnightBlue
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text("Total Amount", style = MaterialTheme.typography.labelSmall, color = MutedText)
                        Text(
                            text = "₹${booking.totalPrice.toInt()}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = DeepEmerald
                        )
                    }
                }

                if (booking.status == "Completed") {
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onReviewClick,
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LeafAccent,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.Star, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Rate your Experience", fontWeight = FontWeight.Bold)
                    }
                } else if (booking.status == "Confirmed" || booking.status == "Pending") {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onCancelClick,
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(14.dp),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, ErrorDark),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorDark)
                        ) {
                            Text(
                                "Cancel", 
                                color = ErrorDark,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        
                        Button(
                            onClick = onClick,
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeepEmerald,
                                contentColor = Color.White
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    Icons.Default.QrCode, 
                                    null, 
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.White
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "Check-in", 
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                } else if (booking.status != "Reviewed" && (booking.status == "Completed" || currentTime >= booking.checkOutDate)) {
                    // Show Review button only in Past tab for stays not yet reviewed
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onReviewClick,
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LeafAccent,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.RateReview, 
                                null, 
                                modifier = Modifier.size(18.dp),
                                tint = Color.White
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Add Review", 
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                } else if (booking.status == "Reviewed") {
                    // Show Edit Review button for already reviewed stays
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedButton(
                        onClick = onReviewClick,
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = LeafAccent
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, LeafAccent),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.Edit, 
                                null, 
                                modifier = Modifier.size(18.dp),
                                tint = LeafAccent
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Edit Review", 
                                fontWeight = FontWeight.Bold,
                                color = LeafAccent
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CancelConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Surface(
                modifier = Modifier.size(64.dp),
                color = Color(0xFFFFEBEE),
                shape = CircleShape
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFC62828), modifier = Modifier.size(32.dp))
                }
            }
        },
        title = {
            Text(
                "Cancel Booking?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MidnightBlue
            )
        },
        text = {
            Text(
                "Are you sure you want to cancel this booking? A full refund will be initiated to your original payment method.",
                style = MaterialTheme.typography.bodyMedium,
                color = SlateGray,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Yes, Cancel", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Keep Booking", color = SlateGray, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(28.dp)
    )
}

@Composable
fun CheckoutConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Surface(
                modifier = Modifier.size(64.dp),
                color = LeafAccent.copy(alpha = 0.1f),
                shape = CircleShape
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = LeafAccent, modifier = Modifier.size(32.dp))
                }
            }
        },
        title = {
            Text(
                "Confirm Checkout?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MidnightBlue
            )
        },
        text = {
            Text(
                "Are you sure you want to check out now? We hope you had a wonderful stay!",
                style = MaterialTheme.typography.bodyMedium,
                color = SlateGray,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = DeepEmerald),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Yes, Checkout", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = SlateGray, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(28.dp)
    )
}

@Composable
fun StatusBadge(status: String, modifier: Modifier = Modifier) {
    val (bgColor, textColor, icon) = when (status) {
        "Confirmed" -> Triple(Color(0xFFE8F5E9), Color(0xFF2E7D32), Icons.Default.CheckCircle)
        "CheckedIn" -> Triple(Color(0xFFE3F2FD), Color(0xFF1565C0), Icons.Default.Login)
        "Pending" -> Triple(Color(0xFFFFF3E0), Color(0xFFEF6C00), Icons.Default.Schedule)
        "Completed" -> Triple(Color(0xFFF3E5F5), Color(0xFF7B1FA2), Icons.Default.DoneAll)
        else -> Triple(Color(0xFFFFEBEE), Color(0xFFC62828), Icons.Default.Cancel)
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = textColor, modifier = Modifier.size(12.dp))
            Spacer(Modifier.width(4.dp))
            Text(
                text = status,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = textColor
            )
        }
    }
}

@Composable
fun EmptyBookingsState(tab: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(120.dp),
            color = SurfaceSoft,
            shape = CircleShape
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = when(tab) {
                        "Upcoming" -> Icons.Default.EventNote
                        "Past" -> Icons.Default.History
                        else -> Icons.Default.Block
                    },
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MutedText
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "No $tab Bookings",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MidnightBlue
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "When you book a homestay, it will appear here for you to manage.",
            style = MaterialTheme.typography.bodyMedium,
            color = SlateGray,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

private fun formatDateRange(start: Long, end: Long): String {
    val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
    return "${sdf.format(Date(start))} - ${sdf.format(Date(end))}"
}
