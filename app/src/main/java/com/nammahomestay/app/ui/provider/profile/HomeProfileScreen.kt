package com.nammahomestay.app.ui.provider.profile

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.ui.components.*
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*

@Composable
fun HomeProfileScreen(
    navController: NavHostController,
    viewModel: HomeProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            snackbarHostState.showSnackbar("Profile saved successfully")
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
                currentRoute = Screen.ProviderSettings.route,
                onItemClick = { route: String ->
                    if (route != Screen.HomeProfile.route) navController.navigate(route)
                }
            )
        },
        containerColor = IvoryWhite
    ) { padding ->
        if (uiState.isLoading && uiState.homeStays.isEmpty() && !uiState.isEditing) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DeepEmerald)
            }
        } else {
            if (!uiState.isEditing) {
                HomeStayListContent(uiState, viewModel, padding)
            } else {
                HomeStayEditContent(uiState, viewModel, padding)
            }
        }
        
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(padding))
    }
}

@Composable
private fun HomeStayListContent(
    uiState: HomeProfileUiState,
    viewModel: HomeProfileViewModel,
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        val firstName = uiState.accountName.split(" ").firstOrNull() ?: "Your"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("${firstName}'s Properties", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
                Text("Manage your HomeStay listings", style = MaterialTheme.typography.bodyMedium, color = SlateGray)
            }
            IconButton(
                onClick = viewModel::createNewProfile,
                modifier = Modifier.background(DeepEmerald, CircleShape)
            ) {
                Icon(Icons.Default.Add, "Add Property", tint = Color.White)
            }
        }

        if (uiState.homeStays.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                Text("No properties added yet. Tap '+' to create one.", color = SlateGray)
            }
        } else {
            var propertyToDelete by remember { mutableStateOf<HomeStay?>(null) }
            
            uiState.homeStays.forEach { stay ->
                val inquiryCount = uiState.inquiryCounts[stay.id] ?: 0
                HomeStaySummaryCard(
                    homeStay = stay,
                    inquiryCount = inquiryCount,
                    onClick = { viewModel.selectForEdit(stay) },
                    onDelete = { propertyToDelete = stay },
                    onToggleAvailability = { viewModel.toggleAvailability(stay.id, it) }
                )
            }

            if (propertyToDelete != null) {
                AlertDialog(
                    onDismissRequest = { propertyToDelete = null },
                    icon = { Icon(Icons.Default.Delete, null, tint = Color.Red) },
                    title = { Text("Delete Property?") },
                    text = { Text("Are you sure you want to delete '${propertyToDelete?.name}'? This action cannot be undone.") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                propertyToDelete?.let { viewModel.deleteProperty(it.id) }
                                propertyToDelete = null
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                        ) {
                            Text("Delete", fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { propertyToDelete = null }) {
                            Text("Cancel")
                        }
                    },
                    containerColor = Color.White,
                    shape = RoundedCornerShape(28.dp)
                )
            }
        }
    }
}

@Composable
private fun HomeStayEditContent(
    uiState: HomeProfileUiState,
    viewModel: HomeProfileViewModel,
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState())
    ) {
        val completedFields = calculateCompletion(uiState)
        val totalFields = 9

        ProfileImmersiveHeader(
            onBack = viewModel::backToList,
            completedFields = completedFields,
            totalFields = totalFields,
            hostName = uiState.accountName
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-60).dp)
                .padding(horizontal = 24.dp)
                .zIndex(2f),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            FloatingIdentityCard(uiState = uiState, viewModel = viewModel)
            
            Divider(color = DividerThin)
            LocationSection(uiState = uiState, viewModel = viewModel)
            
            Divider(color = DividerThin)
            PricingSection(uiState = uiState, viewModel = viewModel)

            Divider(color = DividerThin)
            ChecklistSection(uiState = uiState, viewModel = viewModel)
            
            Divider(color = DividerThin)
            GallerySection(uiState = uiState, viewModel = viewModel)

            val isAllFieldsFilled = completedFields == totalFields

            NammaButton(
                text = if (isAllFieldsFilled) "Save Property Changes" else "Complete Profile to Save ($completedFields/$totalFields)",
                onClick = {
                    if (isAllFieldsFilled) {
                        viewModel.saveProfile()
                    }
                },
                isLoading = uiState.isLoading,
                enabled = isAllFieldsFilled,
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
