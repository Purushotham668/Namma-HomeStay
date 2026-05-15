package com.nammahomestay.app.ui.provider.localguide

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nammahomestay.app.data.model.LocalSpot
import com.nammahomestay.app.ui.components.NammaBottomNavBar
import com.nammahomestay.app.ui.components.NammaCard
import com.nammahomestay.app.ui.components.NammaTextField
import com.nammahomestay.app.ui.components.providerNavItems
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalGuideScreen(
    navController: NavHostController,
    viewModel: LocalGuideViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    var showAddDialog by remember { mutableStateOf(false) }
    var spotName by remember { mutableStateOf("") }
    var spotDistance by remember { mutableStateOf("") }
    var spotDesc by remember { mutableStateOf("") }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            snackbarHostState.showSnackbar("Spots updated")
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
                currentRoute = Screen.LocalGuide.route,
                onItemClick = { route: String ->
                    if (route != Screen.LocalGuide.route) navController.navigate(route)
                }
            )
        },
        containerColor = IvoryWhite,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = LeafAccent,
                contentColor = IvoryWhite,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 16.dp, end = 8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Spot")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Immersive Header
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
                    imageVector = Icons.Default.Map,
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
                        text = "Local Guide",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = IvoryWhite
                    )
                    Text(
                        text = "Showcase nearby gems to your guests.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = IvoryWhite.copy(alpha = 0.7f)
                    )
                }
            }

            // List Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-40).dp)
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(IvoryWhite)
            ) {
                if (uiState.isLoading && uiState.spots.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = DeepEmerald)
                    }
                } else if (uiState.spots.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(80.dp), tint = MutedText)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("No spots added yet", style = MaterialTheme.typography.bodyLarge, color = SlateGray)
                            TextButton(onClick = { showAddDialog = true }) {
                                Text("Add your first spot", color = DeepEmerald, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.spots) { spot ->
                            SpotItem(spot = spot, onDelete = { viewModel.removeSpot(spot.id) })
                        }
                        item { Spacer(modifier = Modifier.height(80.dp)) }
                    }
                }
            }
        }

        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Add New Spot", color = DeepEmerald, fontWeight = FontWeight.ExtraBold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        NammaTextField(value = spotName, onValueChange = { spotName = it }, label = "Place Name")
                        NammaTextField(value = spotDistance, onValueChange = { spotDistance = it }, label = "Distance (e.g. 5 km)")
                        NammaTextField(value = spotDesc, onValueChange = { spotDesc = it }, label = "Description", maxLines = 3, singleLine = false)
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (spotName.isNotBlank()) {
                                viewModel.addSpot(spotName, spotDistance, spotDesc)
                                spotName = ""; spotDistance = ""; spotDesc = ""
                                showAddDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DeepEmerald),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text("Save", fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) {
                        Text("Cancel", color = SlateGray)
                    }
                },
                containerColor = IvoryWhite,
                shape = RoundedCornerShape(28.dp)
            )
        }
        
        SnackbarHost(hostState = snackbarHostState)
    }
}

@Composable
fun SpotItem(spot: LocalSpot, onDelete: () -> Unit) {
    NammaCard {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = spot.name, 
                    style = MaterialTheme.typography.titleLarge, 
                    fontWeight = FontWeight.ExtraBold, 
                    color = DeepEmerald
                )
                Text(
                    text = spot.distance, 
                    style = MaterialTheme.typography.labelLarge, 
                    color = LeafAccent, 
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = spot.description, 
                    style = MaterialTheme.typography.bodyMedium, 
                    color = MidnightBlue
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorDark)
            }
        }
    }
}
