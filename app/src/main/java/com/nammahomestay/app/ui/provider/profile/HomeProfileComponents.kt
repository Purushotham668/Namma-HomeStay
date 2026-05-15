package com.nammahomestay.app.ui.provider.profile

import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.ui.components.*
import com.nammahomestay.app.ui.theme.*

@Composable
fun SectionTitle(title: String, subtitle: String? = null) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = MidnightBlue
        )
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = SlateGray
            )
        }
    }
}

@Composable
fun ProfileImmersiveHeader(
    onBack: () -> Unit,
    completedFields: Int,
    totalFields: Int,
    hostName: String
) {
    val progress by animateFloatAsState(
        targetValue = completedFields.toFloat() / totalFields.toFloat(),
        label = "progress"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(DeepEmerald, DeepEmeraldDark)
                )
            )
    ) {
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
                .padding(top = 48.dp, start = 24.dp, end = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.background(IvoryWhite.copy(alpha = 0.1f), CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = IvoryWhite)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = hostName.ifEmpty { "Your Profile" },
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = IvoryWhite
            )
            Text(
                text = "Complete your listing to attract more travelers",
                style = MaterialTheme.typography.bodyLarge,
                color = IvoryWhite.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(modifier = Modifier.padding(bottom = 80.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Profile Completion",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = IvoryWhite
                    )
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = PremiumGold
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .clip(CircleShape),
                    color = PremiumGold,
                    trackColor = IvoryWhite.copy(alpha = 0.2f)
                )
            }
        }
    }
}

@Composable
fun HomeStaySummaryCard(
    homeStay: HomeStay,
    inquiryCount: Int,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onToggleAvailability: (Boolean) -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(DeepEmerald.copy(alpha = 0.1f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.MapsHomeWork, null, tint = DeepEmerald)
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = homeStay.name.ifEmpty { "Unnamed Property" },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MidnightBlue,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Rating Badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .background(PremiumGold.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.Star, null, tint = PremiumGold, modifier = Modifier.size(14.dp))
                        Text(
                            text = if (homeStay.reviewCount > 0) "${homeStay.rating}" else "New",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = DeepEmeraldDark
                        )
                    }
                }

                Text(
                    text = homeStay.address.ifEmpty { "Address not set" },
                    style = MaterialTheme.typography.bodySmall,
                    color = SlateGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Price Badge
                    Surface(
                        color = DeepEmerald.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "₹${homeStay.pricing.pricePerDay}/night",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = DeepEmerald
                        )
                    }
                    
                    // Inquiries Badge
                    if (inquiryCount > 0) {
                        Surface(
                            color = DeepEmerald.copy(alpha = 0.85f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.Mail, null, tint = Color.White, modifier = Modifier.size(12.dp))
                                Text(
                                    text = "$inquiryCount Inquiries",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Availability Toggle
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Switch(
                            checked = homeStay.pricing.isAvailable,
                            onCheckedChange = onToggleAvailability,
                            modifier = Modifier.scale(0.7f),
                            colors = SwitchDefaults.colors(checkedTrackColor = LeafAccent)
                        )
                        Text(
                            text = if (homeStay.pricing.isAvailable) "Live" else "Hidden",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (homeStay.pricing.isAvailable) DeepEmerald else SlateGray
                        )
                    }
                    
                    // Status Badge (Occupied)
                    Surface(
                        color = if (homeStay.isOccupied) PremiumGold.copy(alpha = 0.15f) else LeafAccent.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(if (homeStay.isOccupied) PremiumGold else LeafAccent, CircleShape)
                            )
                            Text(
                                text = if (homeStay.isOccupied) "Occupied" else "Vacant",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (homeStay.isOccupied) DeepEmeraldDark else DeepEmerald
                            )
                        }
                    }
                }
            }
            
            IconButton(
                onClick = onDelete,
                modifier = Modifier.background(SurfaceSoft, CircleShape).size(36.dp)
            ) {
                Icon(Icons.Default.Delete, "Delete", tint = Color.Red.copy(alpha = 0.6f), modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun FloatingIdentityCard(uiState: HomeProfileUiState, viewModel: HomeProfileViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(24.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        color = IvoryWhite
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Identity", "The core details travelers will see.")
            
            NammaTextField(
                value = uiState.homeStay.name,
                onValueChange = viewModel::updateName,
                label = "Property Name *",
                leadingIcon = { Icon(Icons.Default.MapsHomeWork, null, tint = DeepEmerald) }
            )
            NammaTextField(
                value = uiState.homeStay.ownerName,
                onValueChange = viewModel::updateOwnerName,
                label = "Host Name *",
                leadingIcon = { Icon(Icons.Default.Person, null, tint = DeepEmerald) }
            )
            
            var showCodePicker by remember { mutableStateOf(false) }
            val countryCodes = listOf(
                "🇮🇳 +91" to "+91",
                "🇺🇸 +1" to "+1",
                "🇬🇧 +44" to "+44",
                "🇦🇪 +971" to "+971",
                "🇸🇬 +65" to "+65"
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    onClick = { showCodePicker = true },
                    modifier = Modifier.height(56.dp).width(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = SurfaceSoft,
                    border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        val currentPrefix = uiState.homeStay.contact.split(" ").firstOrNull() ?: "+91"
                        val displayCode = countryCodes.find { it.second == currentPrefix }?.first ?: "🇮🇳 +91"
                        Text(displayCode, fontWeight = FontWeight.Bold, color = MidnightBlue)
                        
                        DropdownMenu(
                            expanded = showCodePicker,
                            onDismissRequest = { showCodePicker = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            countryCodes.forEach { (display, code) ->
                                DropdownMenuItem(
                                    text = { Text(display) },
                                    onClick = {
                                        val numberOnly = uiState.homeStay.contact.substringAfter(" ").ifEmpty { "" }
                                        viewModel.updateContact("$code $numberOnly")
                                        showCodePicker = false
                                    }
                                )
                            }
                        }
                    }
                }

                NammaTextField(
                    value = uiState.homeStay.contact.substringAfter(" "),
                    onValueChange = { 
                        val prefix = uiState.homeStay.contact.split(" ").firstOrNull() ?: "+91"
                        viewModel.updateContact("$prefix $it") 
                    },
                    label = "Contact Number *",
                    modifier = Modifier.weight(1f),
                    leadingIcon = { Icon(Icons.Default.Phone, null, tint = DeepEmerald) }
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Text("Property Theme *", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = SlateGray)
            val categories = listOf("Forest", "Mountain", "Coffee", "Heritage", "Estate", "River", "Valley")
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = uiState.homeStay.categories.contains(cat)
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.updateCategory(cat) },
                        label = { Text(cat, fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal) }
                    )
                }
            }

            NammaTextField(
                value = uiState.homeStay.description,
                onValueChange = viewModel::updateDescription,
                label = "Property Description *",
                maxLines = 5,
                singleLine = false,
                leadingIcon = { Icon(Icons.Default.Description, null, tint = DeepEmerald) }
            )
        }
    }
}

@Composable
fun LocationSection(uiState: HomeProfileUiState, viewModel: HomeProfileViewModel) {
    Column {
        SectionTitle("Location", "Where is your property located?")
        NammaTextField(
            value = uiState.homeStay.address,
            onValueChange = viewModel::updateAddress,
            label = "Full Street Address *",
            maxLines = 3,
            singleLine = false
        )
        
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(20.dp))
                .shadow(8.dp, RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            color = SurfaceSoft,
            border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                NammaMap(
                    latitude = uiState.homeStay.latitude,
                    longitude = uiState.homeStay.longitude,
                    onLocationConfirm = { lat, lng ->
                        viewModel.updateLatitude(lat.toString())
                        viewModel.updateLongitude(lng.toString())
                        viewModel.saveProfile()
                    },
                    showSearch = false // Hide search bar in minimized view
                )

                // Expand Button
                var showFullScreenMap by remember { mutableStateOf(false) }
                IconButton(
                    onClick = { showFullScreenMap = true },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .background(Color.White, CircleShape)
                        .shadow(4.dp, CircleShape)
                ) {
                    Icon(Icons.Default.Fullscreen, "Expand Map", tint = DeepEmerald)
                }

                if (showFullScreenMap) {
                    MapDialog(
                        latitude = uiState.homeStay.latitude,
                        longitude = uiState.homeStay.longitude,
                        onDismiss = { showFullScreenMap = false },
                        onConfirm = { lat, lng ->
                            viewModel.updateLatitude(lat.toString())
                            viewModel.updateLongitude(lng.toString())
                            viewModel.saveProfile()
                            showFullScreenMap = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ChecklistSection(uiState: HomeProfileUiState, viewModel: HomeProfileViewModel) {
    Column {
        SectionTitle("Amenities", "What facilities do you offer?")
        uiState.homeStay.checklist.forEach { (item, checked) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.updateChecklist(item, !checked) }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { viewModel.updateChecklist(item, it) },
                    colors = CheckboxDefaults.colors(checkedColor = DeepEmerald)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(item, style = MaterialTheme.typography.bodyLarge, color = MidnightBlue)
            }
        }
    }
}

@Composable
fun PricingSection(uiState: HomeProfileUiState, viewModel: HomeProfileViewModel) {
    Column {
        SectionTitle("Rates & Revenue", "Set your pricing and track potential earnings.")
        
        NammaTextField(
            value = uiState.homeStay.pricing.pricePerDay,
            onValueChange = viewModel::updatePricePerDay,
            label = "Base Price / Night *",
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Text("₹", fontWeight = FontWeight.Bold, color = DeepEmerald) }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Rooms Available *", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MidnightBlue)
                Text("Total inventory for this property", style = MaterialTheme.typography.bodySmall, color = SlateGray)
            }
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val currentRooms = uiState.homeStay.pricing.roomsAvailable.toIntOrNull() ?: 0
                
                IconButton(
                    onClick = { if (currentRooms > 0) viewModel.updateRoomsAvailable((currentRooms - 1).toString()) },
                    modifier = Modifier.background(SurfaceSoft, CircleShape).size(40.dp)
                ) {
                    Icon(Icons.Default.Remove, null, tint = DeepEmerald)
                }
                
                Text(
                    text = currentRooms.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = MidnightBlue,
                    modifier = Modifier.widthIn(min = 32.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                
                IconButton(
                    onClick = { viewModel.updateRoomsAvailable((currentRooms + 1).toString()) },
                    modifier = Modifier.background(DeepEmerald, CircleShape).size(40.dp)
                ) {
                    Icon(Icons.Default.Add, null, tint = Color.White)
                }
            }
        }

        // Potential Revenue Info Card
        val dailyPrice = uiState.homeStay.pricing.pricePerDay.toDoubleOrNull() ?: 0.0
        val rooms = uiState.homeStay.pricing.roomsAvailable.toDoubleOrNull() ?: 0.0
        val potentialRevenue = dailyPrice * rooms

        if (potentialRevenue > 0) {
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = DeepEmerald.copy(alpha = 0.05f)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.TrendingUp, null, tint = DeepEmerald)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Potential Daily Revenue", style = MaterialTheme.typography.labelMedium, color = SlateGray)
                        Text("₹${potentialRevenue.toInt()}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = DeepEmerald)
                    }
                }
            }
        }
    }
}

@Composable
fun GallerySection(uiState: HomeProfileUiState, viewModel: HomeProfileViewModel) {
    Column {
        SectionTitle("Gallery", "Upload high-quality images.")
        PhotoPickerGrid(
            label = "Property Photos",
            existingUrls = uiState.homeStay.photoUrls,
            selectedPhotos = uiState.selectedPhotos,
            onImagesSelected = viewModel::addPhotos,
            onRemoveSelected = viewModel::removePhoto,
            onRemoveExisting = viewModel::removeExistingPhoto
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        SectionTitle("Individual Rooms", "Showcase specific rooms with photos.")
        uiState.homeStay.rooms.forEachIndexed { index, room ->
            RoomEditCard(
                room = room,
                onUpdate = viewModel::updateRoom,
                onRemove = { viewModel.removeRoom(room.id) },
                onPhotoSelected = { uri -> viewModel.uploadRoomPhoto(room.id, uri) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        NammaButton(
            text = "+ Add Another Room",
            onClick = viewModel::addRoom,
            modifier = Modifier.fillMaxWidth().height(48.dp),
            containerColor = SurfaceSoft,
            contentColor = DeepEmerald
        )
    }
}

@Composable
fun RoomEditCard(
    room: com.nammahomestay.app.data.model.Room,
    onUpdate: (com.nammahomestay.app.data.model.Room) -> Unit,
    onRemove: () -> Unit,
    onPhotoSelected: (Uri) -> Unit
) {
    val launcher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onPhotoSelected(it) }
    }

    NammaCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Room Details", fontWeight = FontWeight.Bold, color = MidnightBlue)
                IconButton(onClick = onRemove, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.RemoveCircleOutline, null, tint = Color.Red.copy(alpha = 0.6f))
                }
            }
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // Room Photo
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceSoft)
                        .clickable { launcher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (!room.photoUrl.isNullOrBlank()) {
                        NammaImage(
                            url = room.photoUrl,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.AddAPhoto, null, tint = SlateGray)
                            Text("Add Photo", style = MaterialTheme.typography.labelSmall, color = SlateGray)
                        }
                    }
                }
                
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    NammaTextField(
                        value = room.name,
                        onValueChange = { onUpdate(room.copy(name = it)) },
                        label = "Room Name (e.g. Deluxe Suite)",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        NammaTextField(
                            value = room.price,
                            onValueChange = { onUpdate(room.copy(price = it)) },
                            label = "Price",
                            modifier = Modifier.weight(1f)
                        )
                        NammaTextField(
                            value = room.capacity,
                            onValueChange = { onUpdate(room.copy(capacity = it)) },
                            label = "Capacity",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MapDialog(
    latitude: Double,
    longitude: Double,
    onDismiss: () -> Unit,
    onConfirm: (Double, Double) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, "Close")
                    }
                    Text("Select Location", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    NammaButton(
                        text = "Confirm",
                        onClick = { /* Confirmation happens inside map usually, or via a button here */ },
                        modifier = Modifier.width(100.dp).height(36.dp)
                    )
                }

                Box(modifier = Modifier.weight(1f)) {
                    NammaMap(
                        latitude = latitude,
                        longitude = longitude,
                        onLocationConfirm = onConfirm,
                        modifier = Modifier.fillMaxSize(),
                        showSearch = true // Enable search in full screen
                    )
                }
            }
        }
    }
}

fun calculateCompletion(uiState: HomeProfileUiState): Int {
    val h = uiState.homeStay
    return listOf(
        h.name.isNotBlank(),
        h.ownerName.isNotBlank(),
        h.contact.length > 5, 
        h.address.isNotBlank(),
        h.description.isNotBlank(),
        h.categories.isNotEmpty(),
        h.latitude != 0.0 || h.longitude != 0.0,
        h.photoUrls.isNotEmpty() || uiState.selectedPhotos.isNotEmpty(),
        // Only Base Price is mandatory now
        h.pricing.pricePerDay.isNotBlank()
    ).count { it }
}
