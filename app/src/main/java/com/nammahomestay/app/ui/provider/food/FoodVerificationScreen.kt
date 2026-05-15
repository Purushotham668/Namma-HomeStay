package com.nammahomestay.app.ui.provider.food

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.nammahomestay.app.data.model.FoodBooking
import com.nammahomestay.app.data.repository.FoodRepository
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.provider.verification.CameraPreview
import com.nammahomestay.app.ui.theme.*
import com.nammahomestay.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FoodVerificationUiState(
    val isScanning: Boolean = true,
    val isLoading: Boolean = false,
    val booking: FoodBooking? = null,
    val error: String? = null,
    val success: Boolean = false
)

@HiltViewModel
class FoodVerificationViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FoodVerificationUiState())
    val uiState: StateFlow<FoodVerificationUiState> = _uiState.asStateFlow()

    fun processQRCode(data: String) {
        if (!data.startsWith("nammafood:")) {
            _uiState.update { it.copy(isScanning = false, error = "Invalid Food Ticket") }
            return
        }
        val bookingId = data.removePrefix("nammafood:")
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isScanning = false) }
            val result = repository.getBookingById(bookingId)
            if (result is Resource.Success && result.data != null) {
                _uiState.update { it.copy(isLoading = false, booking = result.data) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Ticket not found") }
            }
        }
    }

    fun markAsServed() {
        val bookingId = _uiState.value.booking?.id ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = repository.markAsServed(bookingId)
            if (result is Resource.Success) {
                _uiState.update { it.copy(isLoading = false, success = true) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Update failed") }
            }
        }
    }

    fun resumeScanning() {
        _uiState.value = FoodVerificationUiState()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodVerificationScreen(
    navController: NavHostController,
    viewModel: FoodVerificationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verify Meal Ticket", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (uiState.isScanning) {
                CameraPreview(onQrCodeScanned = { viewModel.processQRCode(it) })
                ScannerOverlay()
            } else {
                ResultContent(uiState, viewModel, navController)
            }
        }
    }
}

@Composable
fun ScannerOverlay() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f))) {
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.Transparent)
                    .border(4.dp, LeafAccent, RoundedCornerShape(32.dp))
            )
            Spacer(Modifier.height(32.dp))
            Surface(color = Color.Black.copy(alpha = 0.4f), shape = RoundedCornerShape(20.dp)) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.QrCodeScanner, null, tint = Color.White)
                    Spacer(Modifier.width(12.dp))
                    Text("Scan Guest's Food Ticket", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ResultContent(uiState: FoodVerificationUiState, viewModel: FoodVerificationViewModel, navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(color = DeepEmerald)
        } else if (uiState.success) {
            Icon(Icons.Default.CheckCircle, null, tint = LeafAccent, modifier = Modifier.size(80.dp))
            Spacer(Modifier.height(16.dp))
            Text("Meal Marked as Served!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold, color = DeepEmerald)
            Spacer(Modifier.height(48.dp))
            NammaButton("Scan Another", onClick = { viewModel.resumeScanning() }, modifier = Modifier.fillMaxWidth())
        } else if (uiState.booking != null) {
            val booking = uiState.booking
            Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), color = SurfaceSoft, border = androidx.compose.foundation.BorderStroke(1.dp, DividerThin)) {
                Column(Modifier.padding(24.dp)) {
                    Text("GUEST DETAILS", style = MaterialTheme.typography.labelSmall, color = SlateGray, fontWeight = FontWeight.Bold)
                    Text(booking.travelerName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
                    Spacer(Modifier.height(16.dp))
                    Text("ORDERED DISHES", style = MaterialTheme.typography.labelSmall, color = SlateGray, fontWeight = FontWeight.Bold)
                    booking.items.forEach { item ->
                        Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("${item.quantity}x ${item.itemName}", fontWeight = FontWeight.Medium)
                            Text(if (item.isVeg) "Veg" else "Non-Veg", color = if (item.isVeg) LeafAccent else ErrorDark)
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Payment Method", color = SlateGray)
                        Text(booking.paymentMethod, fontWeight = FontWeight.Bold, color = if (booking.paymentMethod == "Cash") ErrorDark else LeafAccent)
                    }
                }
            }
            Spacer(Modifier.height(48.dp))
            NammaButton("Mark as Served", onClick = { viewModel.markAsServed() }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            TextButton(onClick = { viewModel.resumeScanning() }) { Text("CANCEL", color = SlateGray) }
        } else if (uiState.error != null) {
            Icon(Icons.Default.Error, null, tint = ErrorDark, modifier = Modifier.size(80.dp))
            Text(uiState.error, color = ErrorDark, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(48.dp))
            NammaButton("Try Again", onClick = { viewModel.resumeScanning() }, modifier = Modifier.fillMaxWidth())
        }
    }
}
