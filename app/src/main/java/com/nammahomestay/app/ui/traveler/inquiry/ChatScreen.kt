package com.nammahomestay.app.ui.traveler.inquiry

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Send
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
import com.nammahomestay.app.data.model.Inquiry
import com.nammahomestay.app.data.repository.AuthRepository
import com.nammahomestay.app.data.repository.InquiryRepository
import com.nammahomestay.app.ui.theme.*
import com.nammahomestay.app.util.Resource
import com.nammahomestay.app.util.toFormattedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val inquiryRepository: InquiryRepository,
    private val authRepository: AuthRepository,
    private val homestayRepository: com.nammahomestay.app.data.repository.HomeStayRepository
) : ViewModel() {
    private val _messages = MutableStateFlow<List<Inquiry>>(emptyList())
    val messages: StateFlow<List<Inquiry>> = _messages.asStateFlow()

    private val _homestay = MutableStateFlow<com.nammahomestay.app.data.model.HomeStay?>(null)
    val homestay: StateFlow<com.nammahomestay.app.data.model.HomeStay?> = _homestay.asStateFlow()

    private val _provider = MutableStateFlow<com.nammahomestay.app.data.model.User?>(null)
    val provider: StateFlow<com.nammahomestay.app.data.model.User?> = _provider.asStateFlow()

    private val _typingId = MutableStateFlow<String?>(null)
    val typingId: StateFlow<String?> = _typingId.asStateFlow()

    val currentUserId = authRepository.currentUser?.uid ?: ""

    private var tId: String = ""
    private var hId: String = ""

    fun init(travelerId: String, homestayId: String) {
        this.tId = travelerId
        this.hId = homestayId

        viewModelScope.launch {
            inquiryRepository.observeChatMessages(travelerId, homestayId).collect {
                _messages.value = it
                if (it.isNotEmpty()) {
                    loadDetails(it.first())
                    // Trigger a seen update in the background whenever new messages arrive
                    viewModelScope.launch {
                        inquiryRepository.markMessagesAsSeen(travelerId, homestayId, currentUserId)
                    }
                }
            }
        }
        
        viewModelScope.launch {
            inquiryRepository.observeTypingStatus(travelerId, homestayId).collect {
                _typingId.value = it
            }
        }

        viewModelScope.launch {
            val result = homestayRepository.getHomeProfile(homestayId)
            if (result is Resource.Success) {
                _homestay.value = result.data
                loadProvider(result.data.providerId)
            }
        }
    }

    private fun loadDetails(message: Inquiry) {
        if (_homestay.value == null) {
            viewModelScope.launch {
                val result = homestayRepository.getHomeProfile(message.homestayId)
                if (result is Resource.Success) {
                    _homestay.value = result.data
                    loadProvider(result.data.providerId)
                }
            }
        }
    }

    private fun loadProvider(providerId: String) {
        if (_provider.value == null && providerId.isNotEmpty()) {
            viewModelScope.launch {
                val result = authRepository.getUserProfile(providerId)
                if (result is Resource.Success) {
                    _provider.value = result.data
                }
            }
        }
    }

    fun setTyping(isTyping: Boolean) {
        viewModelScope.launch {
            inquiryRepository.setTypingStatus(tId, hId, currentUserId, isTyping)
        }
    }

    fun sendMessage(travelerId: String, homestayId: String, messageText: String) {
        if (messageText.isBlank()) return
        
        val hs = _homestay.value
        val providerId = hs?.providerId ?: ""
        val homestayName = hs?.name ?: "HomeStay"

        val message = Inquiry(
            travelerId = travelerId,
            providerId = providerId,
            homestayId = homestayId,
            homestayName = homestayName,
            senderId = currentUserId,
            message = messageText.trim(),
            timestamp = System.currentTimeMillis(),
            seen = false
        )

        viewModelScope.launch {
            setTyping(false)
            inquiryRepository.sendMessage(message)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavHostController,
    travelerId: String,
    homestayId: String,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsState()
    val homestay by viewModel.homestay.collectAsState()
    val provider by viewModel.provider.collectAsState()
    val typingId by viewModel.typingId.collectAsState()
    
    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(travelerId, homestayId) {
        viewModel.init(travelerId, homestayId)
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    // Typing logic
    LaunchedEffect(messageText) {
        if (messageText.isNotEmpty()) {
            viewModel.setTyping(true)
            delay(3000) // Stop typing after 3 seconds of inactivity
            viewModel.setTyping(false)
        } else {
            viewModel.setTyping(false)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = homestay?.name ?: "Loading...", 
                            style = MaterialTheme.typography.titleMedium, 
                            color = IvoryWhite, 
                            fontWeight = FontWeight.Bold
                        )
                        if (typingId != null && typingId != viewModel.currentUserId) {
                            Text(
                                text = "typing...", 
                                style = MaterialTheme.typography.labelSmall, 
                                color = IvoryWhite.copy(alpha = 0.9f),
                                fontWeight = FontWeight.Medium
                            )
                        } else if (provider != null) {
                            Text(
                                text = "${provider?.name} • ${provider?.phone}", 
                                style = MaterialTheme.typography.labelSmall, 
                                color = IvoryWhite.copy(alpha = 0.8f)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = IvoryWhite)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = DeepEmerald)
            )
        },
        containerColor = Color(0xFFE5DDD5) // WhatsApp background color
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { message ->
                    ChatBubble(message = message, isMe = message.senderId == viewModel.currentUserId)
                }
            }

            // Input Bar
            Surface(
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                color = IvoryWhite
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Type a message...") },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFFF0F0F0),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(24.dp),
                        maxLines = 4
                    )
                    Spacer(Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            viewModel.sendMessage(travelerId, homestayId, messageText)
                            messageText = ""
                        },
                        enabled = messageText.isNotBlank(),
                        modifier = Modifier.background(DeepEmerald, RoundedCornerShape(24.dp))
                    ) {
                        Icon(Icons.Default.Send, null, tint = IvoryWhite)
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: Inquiry, isMe: Boolean) {
    val bubbleColor = if (isMe) Color(0xFFDCF8C6) else Color.White
    val alignment = if (isMe) Alignment.End else Alignment.Start
    val shape = if (isMe) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 2.dp)
    } else {
        RoundedCornerShape(topStart = 16.dp, topEnd = 2.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalAlignment = alignment
    ) {
        Surface(
            color = bubbleColor,
            shape = shape,
            shadowElevation = 1.dp,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                Text(
                    text = message.message, 
                    color = MidnightBlue, 
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = message.timestamp.toFormattedDate(), 
                        color = SlateGray.copy(alpha = 0.7f), 
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 10.sp
                    )
                    if (isMe) {
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            imageVector = if (message.seen) Icons.Default.DoneAll else Icons.Default.DoneAll, // Using DoneAll for both but different colors
                            contentDescription = null,
                            tint = if (message.seen) Color(0xFF34B7F1) else Color.Gray,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}
