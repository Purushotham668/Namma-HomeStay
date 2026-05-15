package com.nammahomestay.app.ui.traveler.booking

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.theme.*

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.PathOperation
import androidx.hilt.navigation.compose.hiltViewModel
import com.nammahomestay.app.util.Resource
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

@Composable
fun BookingConfirmationScreen(
    bookingId: String,
    onBackToHome: () -> Unit,
    onViewBookings: () -> Unit,
    viewModel: BookingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Generate QR Code bitmap
    val qrBitmap = remember(bookingId) {
        generateQrCode("nammascan:$bookingId", 512)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFE8F5E9), IvoryWhite, IvoryWhite)
                )
            )
    ) {
        // Decorative floating circles for a modern feel
        Box(
            modifier = Modifier
                .offset(x = (-50).dp, y = (-20).dp)
                .size(200.dp)
                .background(DeepEmerald.copy(alpha = 0.05f), CircleShape)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 40.dp, y = 40.dp)
                .size(150.dp)
                .background(LeafAccent.copy(alpha = 0.05f), CircleShape)
        )

        Scaffold(
            containerColor = Color.Transparent
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // Success Header
                Surface(
                    modifier = Modifier.size(80.dp),
                    color = LeafAccent,
                    shape = CircleShape,
                    shadowElevation = 8.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "You're All Set!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MidnightBlue
                )
                
                Text(
                    text = "Your booking at ${uiState.homeStay?.name ?: "Namma HomeStay"} is confirmed.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SlateGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp).padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Modern Ticket Card
                TicketCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Ticket Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "CHECK-IN PASS",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = LeafAccent,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 2.sp
                                )
                                Text(
                                    text = uiState.homeStay?.name ?: "Homestay",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MidnightBlue
                                )
                            }
                            Surface(
                                color = SurfaceSoft,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "ID: ${bookingId.takeLast(6).uppercase()}",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepEmerald
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        Divider(color = DividerThin, thickness = 1.dp)
                        Spacer(modifier = Modifier.height(24.dp))

                        // Booking Details Grid
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            DetailItem("Check-in", formatDate(uiState.checkInDate))
                            DetailItem("Guests", "${uiState.guests} People")
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // QR Section with dashed border effect (simplified)
                        if (qrBitmap != null) {
                            Box(
                                modifier = Modifier
                                    .size(180.dp)
                                    .background(Color.White, RoundedCornerShape(16.dp))
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    bitmap = qrBitmap.asImageBitmap(),
                                    contentDescription = "Booking QR Code",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "SCAN AT PROPERTY",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MidnightBlue.copy(alpha = 0.4f),
                            letterSpacing = 1.5.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                NammaButton(
                    text = "View My Bookings",
                    onClick = onViewBookings,
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                TextButton(
                    onClick = onBackToHome,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Home, contentDescription = null, tint = DeepEmerald)
                    Spacer(Modifier.width(8.dp))
                    Text("BACK TO EXPLORE", color = DeepEmerald, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = SlateGray)
        Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
    }
}

@Composable
fun TicketCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        color = Color.White,
        shape = TicketShape(24.dp),
        shadowElevation = 12.dp
    ) {
        content()
    }
}

class TicketShape(private val cornerRadius: androidx.compose.ui.unit.Dp) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val radius = with(density) { cornerRadius.toPx() }
        val cutoutRadius = radius * 0.6f
        
        val rectPath = Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height,
                    cornerRadius = CornerRadius(radius, radius)
                )
            )
        }
        
        val cutoutPath = Path().apply {
            val cutoutY = size.height * 0.35f
            addOval(Rect(center = Offset(0f, cutoutY), radius = cutoutRadius))
            addOval(Rect(center = Offset(size.width, cutoutY), radius = cutoutRadius))
        }
        
        val finalPath = Path.combine(PathOperation.Difference, rectPath, cutoutPath)
        
        return Outline.Generic(finalPath)
    }
}

private fun formatDate(timestamp: Long?): String {
    if (timestamp == null) return "---"
    val sdf = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
    return sdf.format(java.util.Date(timestamp))
}

private fun generateQrCode(text: String, size: Int): Bitmap? {
    return try {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(
            text,
            BarcodeFormat.QR_CODE,
            size,
            size
        )
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
