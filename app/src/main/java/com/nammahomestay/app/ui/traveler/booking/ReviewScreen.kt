package com.nammahomestay.app.ui.traveler.booking

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nammahomestay.app.ui.theme.*
import com.nammahomestay.app.ui.components.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    bookingId: String,
    homestayId: String,
    onBack: () -> Unit,
    onReviewSubmitted: () -> Unit,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris -> viewModel.addPhotos(uris) }
    )

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onReviewSubmitted()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Experience", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MidnightBlue) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MidnightBlue)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = IvoryWhite)
            )
        },
        containerColor = IvoryWhite
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Dynamic Emoji / Feedback Indicator
            AnimatedContent(
                targetState = uiState.rating,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                },
                label = "FeedbackEmoji"
            ) { rating ->
                val emoji = when(rating) {
                    1 -> "😞"
                    2 -> "😐"
                    3 -> "😊"
                    4 -> "🤩"
                    5 -> "😍"
                    else -> "⭐"
                }
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(LeafAccent.copy(alpha = 0.15f), Color.Transparent)
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = emoji, fontSize = 72.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = when(uiState.rating) {
                    1 -> "Oh no! What went wrong?"
                    2 -> "We're sorry to hear that."
                    3 -> "Glad you liked it!"
                    4 -> "Awesome stay!"
                    5 -> "Absolutely Perfect!"
                    else -> "How was your stay?"
                },
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = MidnightBlue,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Every review helps the rural community grow.",
                style = MaterialTheme.typography.bodyMedium,
                color = SlateGray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Premium Interactive Rating Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(GlassWhite, RoundedCornerShape(32.dp))
                    .border(1.dp, DividerThin, RoundedCornerShape(32.dp))
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "TAP TO RATE",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = LeafAccent,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..5) {
                        val isSelected = i <= uiState.rating
                        val starScale by animateFloatAsState(
                            targetValue = if (isSelected) 1.2f else 1f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                            label = "StarScale"
                        )
                        
                        Icon(
                            imageVector = if (isSelected) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = "Star $i",
                            tint = if (isSelected) PremiumGold else DividerThin,
                            modifier = Modifier
                                .size(48.dp)
                                .scale(starScale)
                                .clickable(
                                    interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                                    indication = null
                                ) { viewModel.updateRating(i) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Comment Section with Floating Label Style
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(24.dp))
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "SHARE DETAILS",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = SlateGray,
                        letterSpacing = 1.5.sp
                    )
                    
                    // Character Limit Progress
                    val progress = uiState.comment.length.toFloat() / 10000f
                    Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.fillMaxSize(),
                            color = if (progress > 0.9f) ErrorDark else DeepEmerald,
                            strokeWidth = 2.dp,
                            trackColor = DividerThin,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = uiState.comment,
                    onValueChange = { viewModel.updateComment(it) },
                    placeholder = { Text("What made this homestay special? The food, the hosts, or the view?", color = MutedText, fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DeepEmerald,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = SurfaceSoft,
                        unfocusedContainerColor = SurfaceSoft
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = MidnightBlue)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Photo Grid Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(24.dp))
                    .padding(20.dp)
            ) {
                Text(
                    "ADD PHOTOS",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = SlateGray,
                    letterSpacing = 1.5.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        Surface(
                            modifier = Modifier
                                .size(88.dp)
                                .clickable { photoPickerLauncher.launch("image/*") },
                            color = SuccessMintLight,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.AddPhotoAlternate, null, tint = DeepEmerald, modifier = Modifier.size(28.dp))
                                    Text("Add", style = MaterialTheme.typography.labelSmall, color = DeepEmerald, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                    
                    items(uiState.photos) { uri ->
                        Box(modifier = Modifier.size(88.dp)) {
                            AsyncImage(
                                model = uri,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.Crop
                            )
                            IconButton(
                                onClick = { viewModel.removePhoto(uri) },
                                modifier = Modifier
                                    .size(26.dp)
                                    .align(Alignment.TopEnd)
                                    .padding(4.dp)
                                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                            ) {
                                Icon(Icons.Default.Close, null, tint = Color.White, modifier = Modifier.size(14.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Premium Post Button
            if (uiState.isLoading || uiState.isUploading) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = DeepEmerald, strokeWidth = 3.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        if (uiState.isUploading) "Optimizing photos..." else "Securing your feedback...",
                        style = MaterialTheme.typography.labelSmall,
                        color = SlateGray
                    )
                }
            } else {
                Button(
                    onClick = { viewModel.submitReview() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MidnightBlue,
                        disabledContainerColor = DividerThin
                    ),
                    enabled = uiState.rating > 0
                ) {
                    Text(
                        "POST REVIEW", 
                        fontWeight = FontWeight.ExtraBold, 
                        letterSpacing = 1.5.sp,
                        color = Color.White
                    )
                }
            }

            if (uiState.error != null) {
                Spacer(modifier = Modifier.height(20.dp))
                Surface(
                    color = ErrorRose.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = uiState.error!!,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = ErrorDark,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
