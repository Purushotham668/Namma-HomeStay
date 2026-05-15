package com.nammahomestay.app.ui.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.nammahomestay.app.ui.components.GoogleSignInButton
import com.nammahomestay.app.ui.components.NammaButton
import com.nammahomestay.app.ui.components.NammaTextField
import com.nammahomestay.app.ui.navigation.Screen
import com.nammahomestay.app.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    // Google Sign-in Configuration
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("821283140355-lrpte2668843pui5g3lcdp025u7795ls.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }
    val context = LocalContext.current
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    val launcher = rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(com.google.android.gms.common.api.ApiException::class.java)
            account.idToken?.let { viewModel.signInWithGoogle(it) }
        } catch (e: Exception) {
            scope.launch { snackbarHostState.showSnackbar("Google Sign-in failed: ${e.localizedMessage}") }
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    LaunchedEffect(uiState.isLoggedIn, uiState.userRole) {
        if (uiState.isLoggedIn && uiState.userRole.isNotEmpty()) {
            val destination = if (uiState.userRole.equals("provider", ignoreCase = true)) 
                Screen.ProviderDashboard.route else Screen.TravelerDashboard.route
            navController.navigate(destination) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    LaunchedEffect(uiState.needsRoleSelection) {
        if (uiState.needsRoleSelection) {
            navController.navigate(Screen.RoleSelection.route)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DeepEmerald, EmeraldMedium, LeafAccent)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(60.dp))
            
            // App Logo / Identity
            Surface(
                modifier = Modifier.size(100.dp),
                shape = RoundedCornerShape(30.dp),
                color = GlassWhite,
                tonalElevation = 8.dp
            ) {
                Icon(
                    Icons.Default.Home, 
                    contentDescription = null, 
                    tint = DeepEmerald, 
                    modifier = Modifier.padding(20.dp).size(60.dp)
                )
            }
            
            Spacer(Modifier.height(16.dp))
            Text(
                "ನಮ್ಮ HomeStay", 
                style = MaterialTheme.typography.displayMedium, 
                color = IvoryWhite
            )
            Text(
                "Premium Rural Experiences", 
                style = MaterialTheme.typography.bodyLarge, 
                color = IvoryWhite.copy(0.7f),
                letterSpacing = 1.sp
            )
            
            Spacer(Modifier.height(48.dp))

            // Glassmorphic Login Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                color = IvoryWhite,
                shadowElevation = 16.dp
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Welcome Back", 
                        style = MaterialTheme.typography.headlineMedium, 
                        color = DeepEmerald,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        "Sign in to explore unique stays", 
                        style = MaterialTheme.typography.bodyMedium, 
                        color = SlateGray,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    NammaTextField(
                        value = email,
                        onValueChange = { email = it; emailError = "" },
                        label = "Email Address",
                        leadingIcon = { Icon(Icons.Default.Email, null, tint = DeepEmerald) },
                        isError = emailError.isNotEmpty(),
                        errorMessage = emailError
                    )

                    Spacer(Modifier.height(16.dp))

                    NammaTextField(
                        value = password,
                        onValueChange = { password = it; passwordError = "" },
                        label = "Password",
                        leadingIcon = { Icon(Icons.Default.Lock, null, tint = DeepEmerald) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, null)
                            }
                        },
                        visualTransformation = if (passwordVisible) androidx.compose.ui.text.input.VisualTransformation.None 
                                               else androidx.compose.ui.text.input.PasswordVisualTransformation(),
                        isError = passwordError.isNotEmpty(),
                        errorMessage = passwordError
                    )

                    Spacer(Modifier.height(12.dp))
                    
                    Text(
                        "Forgot Password?",
                        style = MaterialTheme.typography.labelLarge,
                        color = LeafAccent,
                        modifier = Modifier.align(Alignment.End).clickable { /* TODO */ }
                    )

                    Spacer(Modifier.height(32.dp))

                    NammaButton(
                        text = "Sign In",
                        isLoading = uiState.isLoading,
                        onClick = {
                            if (email.isEmpty()) emailError = "Email is required"
                            if (password.isEmpty()) passwordError = "Password is required"
                            if (email.isNotEmpty() && password.isNotEmpty()) {
                                viewModel.login(email, password)
                            }
                        }
                    )

                    Spacer(Modifier.height(24.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        HorizontalDivider(modifier = Modifier.weight(1f), color = DividerThin)
                        Text(
                            "  OR  ", 
                            style = MaterialTheme.typography.labelSmall, 
                            color = MutedText
                        )
                        HorizontalDivider(modifier = Modifier.weight(1f), color = DividerThin)
                    }

                    Spacer(Modifier.height(24.dp))

                    GoogleSignInButton(
                        onClick = { 
                            googleSignInClient.signOut().addOnCompleteListener {
                                launcher.launch(googleSignInClient.signInIntent) 
                            }
                        },
                        isLoading = uiState.isLoading
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.padding(bottom = 40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Don't have an account? ", color = IvoryWhite.copy(0.8f))
                Text(
                    "Sign Up", 
                    color = PremiumGold, 
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.navigate(Screen.Register.route) }
                )
            }
        }

        SnackbarHost(snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}
