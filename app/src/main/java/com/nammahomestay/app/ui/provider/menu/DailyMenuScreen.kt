package com.nammahomestay.app.ui.provider.menu

import androidx.compose.animation.animateContentSize
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nammahomestay.app.ui.components.NammaBottomNavBar
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.components.NammaCard
import com.nammahomestay.app.ui.components.NammaTextField
import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.ui.components.providerNavItems
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyMenuScreen(
    navController: NavHostController,
    viewModel: DailyMenuViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.isUpdated) {
        if (uiState.isUpdated) {
            snackbarHostState.showSnackbar("Menu updated successfully")
            viewModel.clearUpdatedState()
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
                currentRoute = Screen.DailyMenu.route,
                onItemClick = { route: String ->
                    if (route != Screen.DailyMenu.route) navController.navigate(route)
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
            MenuImmersiveHeader(navController = navController)

            // Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp)) // Added space to prevent overlap with header
                
                // Property Selector (Tabs)
                PropertySelector(uiState = uiState, onSelect = viewModel::selectProperty)

                // Chef's Specials Section
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Chef's Specials",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MidnightBlue
                        )
                        
                        TextButton(
                            onClick = viewModel::addSpecial,
                            colors = ButtonDefaults.textButtonColors(contentColor = LeafAccent)
                        ) {
                            Icon(Icons.Default.Add, null)
                            Spacer(Modifier.width(4.dp))
                            Text("Add Special", fontWeight = FontWeight.Bold)
                        }
                    }
                    
                    if (uiState.menu.specials.isEmpty()) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            color = SurfaceSoft,
                            border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
                        ) {
                            Text(
                                "No specials added yet.",
                                modifier = Modifier.padding(24.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                color = SlateGray
                            )
                        }
                    } else {
                        uiState.menu.specials.forEach { special ->
                            FoodItemCard(
                                item = special,
                                categoryLabel = "Special Dish",
                                icon = Icons.Default.Star,
                                isExpanded = uiState.expandedItemIds.contains(special.id),
                                onToggleExpand = { viewModel.toggleItemExpansion(special.id) },
                                onUpdate = { name, desc -> viewModel.updateSpecial(special.id, name, desc) },
                                onPriceChange = { viewModel.updateSpecialPrice(special.id, it) },
                                onVegToggle = { viewModel.toggleSpecialVegStatus(special.id) },
                                onSave = { viewModel.saveItemAndMinimize(special.id) },
                                onRemove = { viewModel.removeSpecial(special.id) }
                            )
                        }
                    }
                }

                // Standard Meals Section
                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    Text(
                        text = "Standard Meals",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MidnightBlue
                    )

                    // Breakfast Section
                    MealListSection(
                        title = "Breakfast",
                        icon = Icons.Default.BakeryDining,
                        items = uiState.menu.breakfastItems,
                        expandedItemIds = uiState.expandedItemIds,
                        onToggleExpand = viewModel::toggleItemExpansion,
                        placeholder = "e.g. Masala Dosa",
                        onAdd = viewModel::addBreakfastItem,
                        onUpdate = viewModel::updateBreakfastItem,
                        onPriceChange = viewModel::updateBreakfastItemPrice,
                        onToggleVeg = viewModel::toggleBreakfastItemVegStatus,
                        onSave = { viewModel.saveItemAndMinimize(it) },
                        onRemove = viewModel::removeBreakfastItem
                    )

                    // Lunch Section
                    MealListSection(
                        title = "Lunch",
                        icon = Icons.Default.RamenDining,
                        items = uiState.menu.lunchItems,
                        expandedItemIds = uiState.expandedItemIds,
                        onToggleExpand = viewModel::toggleItemExpansion,
                        placeholder = "e.g. Heritage Thali",
                        onAdd = viewModel::addLunchItem,
                        onUpdate = viewModel::updateLunchItem,
                        onPriceChange = viewModel::updateLunchItemPrice,
                        onToggleVeg = viewModel::toggleLunchItemVegStatus,
                        onSave = { viewModel.saveItemAndMinimize(it) },
                        onRemove = viewModel::removeLunchItem
                    )

                    // Dinner Section
                    MealListSection(
                        title = "Dinner",
                        icon = Icons.Default.DinnerDining,
                        items = uiState.menu.dinnerItems,
                        expandedItemIds = uiState.expandedItemIds,
                        onToggleExpand = viewModel::toggleItemExpansion,
                        placeholder = "e.g. Akki Roti with Chutney",
                        onAdd = viewModel::addDinnerItem,
                        onUpdate = viewModel::updateDinnerItem,
                        onPriceChange = viewModel::updateDinnerItemPrice,
                        onToggleVeg = viewModel::toggleDinnerItemVegStatus,
                        onSave = { viewModel.saveItemAndMinimize(it) },
                        onRemove = viewModel::removeDinnerItem
                    )
                }

                NammaButton(
                    text = "Update ${uiState.selectedHomeStay?.name ?: "Menu"}",
                    onClick = viewModel::saveMenu,
                    isLoading = uiState.isLoading,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp)
                )
            }
        }
        
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(padding))
    }
}

@Composable
private fun MenuImmersiveHeader(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(DeepEmerald, DeepEmeraldDark)
                )
            )
    ) {
        // Decorative Icon
        Icon(
            imageVector = Icons.Default.RestaurantMenu,
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
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.background(IvoryWhite.copy(alpha = 0.1f), CircleShape)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = IvoryWhite)
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Today's Cuisine",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = IvoryWhite
            )
            Text(
                text = "Update your meals to delight your guests.",
                style = MaterialTheme.typography.bodyMedium,
                color = IvoryWhite.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun PropertySelector(uiState: DailyMenuUiState, onSelect: (HomeStay) -> Unit) {
    Column {
        Text(
            "Select Property",
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
private fun MealListSection(
    title: String,
    icon: ImageVector,
    items: List<com.nammahomestay.app.data.model.FoodItem>,
    expandedItemIds: Set<String>,
    onToggleExpand: (String) -> Unit,
    placeholder: String,
    onAdd: () -> Unit,
    onUpdate: (String, String, String) -> Unit,
    onPriceChange: (String, String) -> Unit,
    onToggleVeg: (String) -> Unit,
    onSave: (String) -> Unit,
    onRemove: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SurfaceSoft,
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(icon, null, tint = DeepEmerald, modifier = Modifier.size(24.dp))
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MidnightBlue
                )
            }
            TextButton(
                onClick = onAdd,
                colors = ButtonDefaults.textButtonColors(contentColor = LeafAccent)
            ) {
                Icon(Icons.Default.Add, null)
                Spacer(Modifier.width(4.dp))
                Text("Add Dish", fontWeight = FontWeight.Bold)
            }
        }

        if (items.isEmpty()) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = SurfaceSoft,
                border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
            ) {
                Text(
                    "No items added yet.",
                    modifier = Modifier.padding(16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = SlateGray
                )
            }
        } else {
            items.forEach { item ->
                FoodItemCard(
                    item = item,
                    categoryLabel = "Dish",
                    icon = icon,
                    isExpanded = expandedItemIds.contains(item.id),
                    onToggleExpand = { onToggleExpand(item.id) },
                    onUpdate = { name, desc -> onUpdate(item.id, name, desc) },
                    onPriceChange = { onPriceChange(item.id, it) },
                    onVegToggle = { onToggleVeg(item.id) },
                    onSave = { onSave(item.id) },
                    onRemove = { onRemove(item.id) },
                    placeholder = placeholder
                )
            }
        }
    }
}

@Composable
private fun FoodItemCard(
    item: com.nammahomestay.app.data.model.FoodItem,
    categoryLabel: String,
    icon: ImageVector,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onUpdate: (String, String) -> Unit,
    onPriceChange: (String) -> Unit,
    onVegToggle: () -> Unit,
    onSave: () -> Unit,
    onRemove: () -> Unit,
    placeholder: String = "e.g. Signature Dish"
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clip(RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        color = IvoryWhite,
        shadowElevation = if (isExpanded) 8.dp else 2.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin),
        onClick = if (!isExpanded) onToggleExpand else ({})
    ) {
        if (!isExpanded) {
            // Minimized View (Already refined in previous step)
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Professional Indian Dietary Icon (Square with Dot)
                Box(
                    modifier = Modifier
                        .size(16.dp)
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
                            .size(8.dp)
                            .background(if (item.isVeg) LeafAccent else ErrorDark, shape = CircleShape)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name.ifEmpty { "New Dish" },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MidnightBlue,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    if (item.description.isNotEmpty()) {
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = SlateGray,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                    }
                }
                
                Text(
                    text = if (item.price.isNotEmpty()) "₹${item.price}" else "₹0",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepEmerald
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                IconButton(onClick = onToggleExpand) {
                    Icon(Icons.Default.Edit, "Edit", tint = DeepEmerald, modifier = Modifier.size(20.dp))
                }
            }
        } else {
            // Expanded/Editing View
            Column(modifier = Modifier.padding(24.dp)) { // Increased padding
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top // Align to top for better spacing
                ) {
                    Row(
                        modifier = Modifier.weight(1f).padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(icon, contentDescription = categoryLabel, tint = PremiumGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Editing $categoryLabel",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MidnightBlue
                        )
                    }
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.width(100.dp)) { // Slightly wider for better label fit
                            NammaTextField(
                                value = item.price,
                                onValueChange = onPriceChange,
                                label = "Price",
                                placeholder = "0",
                                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        IconButton(
                            onClick = onRemove,
                            colors = IconButtonDefaults.iconButtonColors(contentColor = ErrorDark)
                        ) {
                            Icon(Icons.Default.Delete, null)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                NammaTextField(
                    value = item.name,
                    onValueChange = { onUpdate(it, item.description) },
                    label = "Dish Name",
                    placeholder = placeholder
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                NammaTextField(
                    value = item.description,
                    onValueChange = { onUpdate(item.name, it) },
                    label = "Dish Description",
                    placeholder = "Describe this delicious dish...",
                    maxLines = 3,
                    singleLine = false
                )

                Spacer(modifier = Modifier.height(20.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text(
                                text = "Dietary Preference",
                                style = MaterialTheme.typography.labelSmall,
                                color = SlateGray
                            )
                            Text(
                                text = if (item.isVeg) "Vegetarian" else "Non-Veg",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (item.isVeg) LeafAccent else ErrorDark
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Switch(
                            checked = item.isVeg,
                            onCheckedChange = { onVegToggle() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = IvoryWhite,
                                checkedTrackColor = LeafAccent,
                                uncheckedThumbColor = IvoryWhite,
                                uncheckedTrackColor = ErrorDark
                            )
                        )
                    }
                    
                    Button(
                        onClick = onSave,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LeafAccent),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Icon(Icons.Default.Check, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Save Dish", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
