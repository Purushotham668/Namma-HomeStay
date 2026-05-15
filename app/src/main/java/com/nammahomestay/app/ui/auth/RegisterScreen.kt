package com.nammahomestay.app.ui.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
fun RegisterScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("traveler") } 
    var passwordVisible by remember { mutableStateOf(false) }
    
    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    // Google Sign-in Configuration
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("821283140355-lrpte2668843pui5g3lcdp025u7795ls.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }
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
                popUpTo(Screen.Register.route) { inclusive = true }
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
            Spacer(Modifier.height(40.dp))
            
            Surface(
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(24.dp),
                color = GlassWhite,
                tonalElevation = 8.dp
            ) {
                Icon(
                    Icons.Default.Home, 
                    contentDescription = null, 
                    tint = DeepEmerald, 
                    modifier = Modifier.padding(16.dp).size(48.dp)
                )
            }
            
            Spacer(Modifier.height(16.dp))
            Text("Create Account", style = MaterialTheme.typography.displayMedium, color = IvoryWhite)
            Text("Join our community of unique stays", style = MaterialTheme.typography.bodyLarge, color = IvoryWhite.copy(0.7f))
            
            Spacer(Modifier.height(32.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                color = IvoryWhite,
                shadowElevation = 16.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NammaTextField(
                        value = name,
                        onValueChange = { name = it; nameError = "" },
                        label = "Full Name",
                        leadingIcon = { Icon(Icons.Default.Person, null, tint = DeepEmerald) },
                        isError = nameError.isNotEmpty(),
                        errorMessage = nameError
                    )

                    Spacer(Modifier.height(12.dp))

                    NammaTextField(
                        value = email,
                        onValueChange = { email = it; emailError = "" },
                        label = "Email Address",
                        leadingIcon = { Icon(Icons.Default.Email, null, tint = DeepEmerald) },
                        isError = emailError.isNotEmpty(),
                        errorMessage = emailError
                    )

                    Spacer(Modifier.height(12.dp))

                    NammaTextField(
                        value = phone,
                        onValueChange = { phone = it; phoneError = "" },
                        label = "Phone Number",
                        leadingIcon = { Icon(Icons.Default.Phone, null, tint = DeepEmerald) },
                        isError = phoneError.isNotEmpty(),
                        errorMessage = phoneError
                    )

                    Spacer(Modifier.height(12.dp))

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

                    // Role Selection Toggle
                    Text(
                        "I am a...", 
                        style = MaterialTheme.typography.labelLarge, 
                        color = DeepEmerald,
                        modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        listOf("traveler" to "Traveler", "provider" to "Owner").forEach { (value, label) ->
                            val isSelected = role == value
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .clickable { role = value },
                                shape = RoundedCornerShape(24.dp),
                                color = if (isSelected) DeepEmerald else SurfaceSoft,
                                border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, DividerThin)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        label, 
                                        color = if (isSelected) IvoryWhite else SlateGray,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(32.dp))

                    NammaButton(
                        text = "Sign Up",
                        isLoading = uiState.isLoading,
                        onClick = {
                            if (name.isEmpty()) nameError = "Name is required"
                            if (email.isEmpty()) emailError = "Email is required"
                            if (phone.isEmpty()) phoneError = "Phone is required"
                            if (password.isEmpty()) passwordError = "Password is required"
                            if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
                                viewModel.register(name, email, phone, password, role)
                            }
                        }
                    )

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
                Text("Already have an account? ", color = IvoryWhite.copy(0.8f))
                Text(
                    "Login", 
                    color = PremiumGold, 
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
            }
        }

        SnackbarHost(snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}
