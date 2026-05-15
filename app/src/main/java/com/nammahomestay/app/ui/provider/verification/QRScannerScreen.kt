package com.nammahomestay.app.ui.provider.verification

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.BorderStroke
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.theme.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlinx.coroutines.guava.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRScannerScreen(
    onBack: () -> Unit,
    viewModel: QRScannerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verify Check-in", color = MidnightBlue, fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (hasCameraPermission) {
                if (uiState.isScanning) {
                    CameraPreview(
                        onQrCodeScanned = { qrData ->
                            viewModel.processQRCode(qrData)
                        }
                    )
                    
                    // Scanning Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.6f))
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(280.dp)
                                    .clip(RoundedCornerShape(32.dp))
                                    .background(Color.Transparent)
                            ) {
                                // Transparent hole for the scanner
                                // Note: Real implementation would use a custom View or canvas drawing
                                // For simplicity here, we just use a border
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(32.dp),
                                    border = BorderStroke(4.dp, LeafAccent)
                                ) {}
                            }
                            
                            Spacer(modifier = Modifier.height(32.dp))
                            
                            Surface(
                                color = Color.Black.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.QrCodeScanner, null, tint = IvoryWhite, modifier = Modifier.size(20.dp))
                                    Spacer(Modifier.width(12.dp))
                                    Text(
                                        text = "Align guest's QR code",
                                        color = IvoryWhite,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                } else {
                    // Result View
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(color = DeepEmerald, strokeWidth = 6.dp, modifier = Modifier.size(64.dp))
                            Spacer(modifier = Modifier.height(24.dp))
                            Text("Verifying Booking Details...", style = MaterialTheme.typography.titleMedium, color = MidnightBlue, fontWeight = FontWeight.Bold)
                        } else if (uiState.successMessage != null) {
                            Surface(
                                modifier = Modifier.size(100.dp),
                                color = LeafAccent.copy(alpha = 0.1f),
                                shape = CircleShape
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.CheckCircle, null, tint = LeafAccent, modifier = Modifier.size(64.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                uiState.successMessage!!,
                                style = MaterialTheme.typography.headlineSmall,
                                color = DeepEmerald,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(48.dp))
                            NammaButton("Scan Another", onClick = { viewModel.resumeScanning() }, modifier = Modifier.fillMaxWidth().height(56.dp))
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedButton(
                                onClick = onBack, 
                                modifier = Modifier.fillMaxWidth().height(56.dp),
                                shape = RoundedCornerShape(28.dp),
                                border = BorderStroke(1.5.dp, DeepEmerald),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = DeepEmerald)
                            ) {
                                Text(
                                    "BACK TO DASHBOARD", 
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.2.sp
                                )
                            }
                        } else if (uiState.foodBooking != null) {
                            val booking = uiState.foodBooking!!
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(24.dp),
                                color = SurfaceSoft,
                                border = BorderStroke(1.dp, DividerThin)
                            ) {
                                Column(Modifier.padding(24.dp)) {
                                    Text("FOOD TICKET DETECTED", style = MaterialTheme.typography.labelSmall, color = LeafAccent, fontWeight = FontWeight.Bold)
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
                                        Text("Payment", color = SlateGray)
                                        Text(booking.paymentMethod, fontWeight = FontWeight.Bold, color = if (booking.paymentMethod == "Cash") ErrorDark else LeafAccent)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                            NammaButton("Mark as Served", onClick = { viewModel.markFoodAsServed() }, modifier = Modifier.fillMaxWidth().height(56.dp))
                            Spacer(modifier = Modifier.height(16.dp))
                            TextButton(onClick = { viewModel.resumeScanning() }) { Text("CANCEL", color = SlateGray) }
                        } else if (uiState.error != null) {
                            Surface(
                                modifier = Modifier.size(100.dp),
                                color = ErrorDark.copy(alpha = 0.1f),
                                shape = CircleShape
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Error, null, tint = ErrorDark, modifier = Modifier.size(64.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                uiState.error!!,
                                style = MaterialTheme.typography.titleLarge,
                                color = ErrorDark,
                                fontWeight = FontWeight.Bold,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(48.dp))
                            NammaButton("Try Again", onClick = { viewModel.resumeScanning() }, modifier = Modifier.fillMaxWidth().height(56.dp))
                        }
                    }
                }
            } else {
                // Permission Denied View
                Column(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.QrCodeScanner, null, modifier = Modifier.size(80.dp), tint = SlateGray)
                    Spacer(Modifier.height(24.dp))
                    Text(
                        "Camera permission is required to scan guest check-in codes.",
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = MidnightBlue
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    NammaButton("Grant Permission", onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) }, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
fun CameraPreview(onQrCodeScanned: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }
    
    val previewView = remember { PreviewView(context) }

    LaunchedEffect(Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        val cameraProvider = cameraProviderFuture.await()

        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, QRAnalyzer(onQrCodeScanned))
            }

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageAnalyzer
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }

    AndroidView(
        factory = { previewView },
        modifier = Modifier.fillMaxSize()
    )
}

class QRAnalyzer(private val onQrCodeScanned: (String) -> Unit) : ImageAnalysis.Analyzer {
    private val scanner = BarcodeScanning.getClient()

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        if (barcode.valueType == Barcode.TYPE_TEXT) {
                            barcode.rawValue?.let {
                                onQrCodeScanned(it)
                            }
                        }
                    }
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}
