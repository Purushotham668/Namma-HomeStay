package com.nammahomestay.app.ui.traveler.inquiry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.lifecycle.viewModelScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nammahomestay.app.data.model.Inquiry
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.InquiryRepository
import com.nammahomestay.app.util.Resource
import com.nammahomestay.app.ui.components.NammaBottomNavBar
import com.nammahomestay.app.ui.components.NammaCard
import com.nammahomestay.app.ui.components.travelerNavItems
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*
import com.nammahomestay.app.util.toFormattedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyInquiriesUiState(
    val isLoading: Boolean = false,
    val inquiries: List<Inquiry> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class MyInquiriesViewModel @Inject constructor(
    private val repository: InquiryRepository,
    private val authRepository: AuthRepository
) : androidx.lifecycle.ViewModel() {

    private val _uiState = MutableStateFlow(MyInquiriesUiState())
    val uiState: StateFlow<MyInquiriesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.cleanupOldInquiries()
            loadMyInquiries()
        }
    }

    fun loadMyInquiries() {
        val currentUser = authRepository.currentUser ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = repository.getConversationsForTraveler(currentUser.uid)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, inquiries = result.data)
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> Unit
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyInquiriesScreen(
    navController: NavHostController,
    viewModel: MyInquiriesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Inquiries", color = IvoryWhite, fontWeight = FontWeight.ExtraBold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = DeepEmerald)
            )
        },
        bottomBar = {
            NammaBottomNavBar(
                items = travelerNavItems,
                currentRoute = Screen.MyInquiries.route,
                onItemClick = { route ->
                    if (route != Screen.MyInquiries.route) {
                        navController.navigate(route)
                    }
                }
            )
        },
        containerColor = IvoryWhite,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        if (uiState.isLoading && uiState.inquiries.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DeepEmerald)
            }
        } else if (uiState.inquiries.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Mail, null, modifier = Modifier.size(80.dp), tint = MutedText)
                    Spacer(Modifier.height(16.dp))
                    Text("No inquiries sent yet", style = MaterialTheme.typography.bodyLarge, color = SlateGray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.inquiries, key = { it.id }) { inquiry ->
                    TravelerInquiryItem(inquiry) {
                        navController.navigate(Screen.Chat.createRoute(inquiry.travelerId, inquiry.homestayId))
                    }
                }
            }
        }
    }
}

@Composable
fun TravelerInquiryItem(inquiry: Inquiry, onClick: () -> Unit) {
    NammaCard(onClick = onClick) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(
                        text = inquiry.homestayName, 
                        style = MaterialTheme.typography.titleMedium, 
                        color = DeepEmerald,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Request Sent", 
                        style = MaterialTheme.typography.labelSmall, 
                        color = LeafAccent,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = inquiry.timestamp.toFormattedDate(), 
                    style = MaterialTheme.typography.bodySmall, 
                    color = SlateGray
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = inquiry.message, 
                style = MaterialTheme.typography.bodyMedium,
                color = MidnightBlue,
                fontWeight = FontWeight.Medium
            )
            /* 
            if (inquiry.replyMessage.isNotEmpty()) {
                ...
            }
            */
        }
    }
}
