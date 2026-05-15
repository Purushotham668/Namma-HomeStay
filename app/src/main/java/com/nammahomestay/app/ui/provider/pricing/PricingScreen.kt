package com.nammahomestay.app.ui.provider.pricing

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BedroomChild
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nammahomestay.app.ui.components.NammaBottomNavBar
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.components.NammaCard
import com.nammahomestay.app.ui.components.NammaTextField
import com.nammahomestay.app.ui.components.providerNavItems
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PricingScreen(
    navController: NavHostController,
    viewModel: PricingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            snackbarHostState.showSnackbar("Pricing and Availability updated")
            viewModel.clearSavedState()
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        bottomBar = {
            NammaBottomNavBar(
                items = providerNavItems,
                currentRoute = Screen.Pricing.route,
                onItemClick = { route: String ->
                    if (route != Screen.Pricing.route) navController.navigate(route)
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
            // Immersive Header
            PricingImmersiveHeader(
                navController = navController,
                isAvailable = uiState.pricing.isAvailable,
                onToggle = viewModel::updateAvailability
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-40).dp)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Portfolio Summary
                PortfolioSummaryCard(uiState = uiState)

                // Property Selector
                PropertySelector(uiState = uiState, onSelect = viewModel::selectProperty)

                if (uiState.selectedHomeStay != null) {
                    PricingInputForm(uiState = uiState, viewModel = viewModel)

                    NammaButton(
                        text = "Update ${uiState.selectedHomeStay?.name} Rates",
                        onClick = viewModel::savePricing,
                        isLoading = uiState.isLoading,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp)
                    )
                } else if (!uiState.isLoading && uiState.homeStays.isEmpty()) {
                    Text(
                        "No properties found. Create one in the Property Profile section.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SlateGray,
                        modifier = Modifier.padding(vertical = 40.dp)
                    )
                }
            }
        }
        
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(padding))
    }
}

@Composable
private fun PricingImmersiveHeader(
    navController: NavHostController,
    isAvailable: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(DeepEmerald, DeepEmeraldDark)
                )
            )
    ) {
        // Decorative Icon
        Icon(
            imageVector = Icons.Default.MonetizationOn,
            contentDescription = null,
            tint = IvoryWhite.copy(alpha = 0.05f),
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopEnd)
                .offset(x = 30.dp, y = (-20).dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp, start = 24.dp, end = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.background(IvoryWhite.copy(alpha = 0.1f), CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = IvoryWhite)
                }
                
                // Availability Switch in Header
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = IvoryWhite.copy(alpha = 0.15f),
                    modifier = Modifier.clip(RoundedCornerShape(16.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (isAvailable) "Active" else "Hidden",
                            style = MaterialTheme.typography.labelLarge,
                            color = if (isAvailable) LeafAccent else ErrorRose,
                            fontWeight = FontWeight.Bold
                        )
                        Switch(
                            checked = isAvailable,
                            onCheckedChange = onToggle,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = IvoryWhite,
                                checkedTrackColor = LeafAccent,
                                uncheckedThumbColor = IvoryWhite,
                                uncheckedTrackColor = ErrorRose
                            ),
                            modifier = Modifier.scale(0.8f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Revenue & Rates",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = IvoryWhite
            )
            Text(
                text = "Manage your pricing and guest visibility.",
                style = MaterialTheme.typography.bodyMedium,
                color = IvoryWhite.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun AvailabilityToggleCard(isAvailable: Boolean, onToggle: (Boolean) -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isAvailable) LeafAccent.copy(alpha = 0.15f) else ErrorRose.copy(alpha = 0.1f),
        label = "bgColor"
    )
    val iconColor by animateColorAsState(
        targetValue = if (isAvailable) DeepEmerald else ErrorDark,
        label = "iconColor"
    )
    val scale by animateFloatAsState(
        targetValue = if (isAvailable) 1.05f else 1f,
        label = "scale"
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = backgroundColor,
        border = androidx.compose.foundation.BorderStroke(1.dp, iconColor.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (isAvailable) Icons.Default.CheckCircle else Icons.Default.PowerSettingsNew,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(28.dp).scale(scale)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isAvailable) "Accepting Guests" else "Currently Hidden",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = iconColor
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isAvailable) "Your property is visible to all travelers." else "Your property is hidden from search results.",
                    style = MaterialTheme.typography.bodySmall,
                    color = SlateGray
                )
            }
            Switch(
                checked = isAvailable,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = IvoryWhite,
                    checkedTrackColor = LeafAccent,
                    uncheckedThumbColor = IvoryWhite,
                    uncheckedTrackColor = ErrorRose
                )
            )
        }
    }
}

@Composable
private fun PortfolioSummaryCard(uiState: PricingUiState) {
    val totalRevenue = uiState.homeStays.sumOf { 
        (it.pricing.pricePerDay.toDoubleOrNull() ?: 0.0) * (it.pricing.roomsAvailable.toDoubleOrNull() ?: 1.0)
    }.toInt()
    val activeProperties = uiState.homeStays.count { it.pricing.isAvailable }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Total Portfolio Value", style = MaterialTheme.typography.labelMedium, color = SlateGray)
                Text("₹$totalRevenue", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = DeepEmerald)
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text("Active Listings", style = MaterialTheme.typography.labelMedium, color = SlateGray)
                Text("$activeProperties / ${uiState.homeStays.size}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MidnightBlue)
            }
        }
    }
}

@Composable
private fun PropertySelector(uiState: PricingUiState, onSelect: (HomeStay) -> Unit) {
    Column {
        Text(
            "Select Property to Manage",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MidnightBlue,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        androidx.compose.foundation.lazy.LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.homeStays.size) { index ->
                val stay = uiState.homeStays[index]
                val isSelected = uiState.selectedHomeStay?.id == stay.id
                
                Surface(
                    onClick = { onSelect(stay) },
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) DeepEmerald else SurfaceSoft,
                    border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
                ) {
                    Text(
                        text = stay.name.ifEmpty { "Property ${index + 1}" },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        color = if (isSelected) Color.White else MidnightBlue,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun PricingInputForm(uiState: PricingUiState, viewModel: PricingViewModel) {
    NammaCard {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rate Structure",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MidnightBlue
                )
                
                if (uiState.selectedHomeStay?.pricing?.isAvailable == true) {
                    Badge(containerColor = LeafAccent.copy(alpha = 0.2f), contentColor = DeepEmerald) {
                        Text("ACTIVE", modifier = Modifier.padding(4.dp))
                    }
                }
            }

            NammaTextField(
                value = uiState.pricing.pricePerDay,
                onValueChange = viewModel::updatePricePerDay,
                label = "Base Price Per Day (₹)",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Icon(Icons.Default.MonetizationOn, null, tint = PremiumGold) }
            )
            
            NammaTextField(
                value = uiState.pricing.roomsAvailable,
                onValueChange = viewModel::updateRoomsAvailable,
                label = "Total Rooms Available",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Icon(Icons.Default.BedroomChild, null, tint = PremiumGold) }
            )
            
            // Potential Revenue Card
            val dailyPrice = uiState.pricing.pricePerDay.toDoubleOrNull() ?: 0.0
            val rooms = uiState.pricing.roomsAvailable.toDoubleOrNull() ?: 0.0
            val potential = (dailyPrice * rooms).toInt()

            if (potential > 0) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(PremiumGold.copy(alpha = 0.1f))
                        .padding(16.dp)
                ) {
                    Column {
                        Text("Potential Daily Revenue", style = MaterialTheme.typography.labelSmall, color = SlateGray)
                        Text("₹$potential", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = DeepEmerald)
                    }
                }
            }
        }
    }
}
