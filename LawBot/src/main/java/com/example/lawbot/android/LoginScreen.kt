package com.example.lawbot.android

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()
    
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    // Google Sign In
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        println("Google Sign-In result: ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                println("Google Sign-In successful: ${account.email}")
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                
                scope.launch {
                    isLoading = true
                    try {
                        auth.signInWithCredential(credential).await()
                        println("Firebase authentication successful")
                        onLoginSuccess()
                    } catch (e: Exception) {
                        println("Firebase authentication failed: ${e.message}")
                        errorMessage = "Google sign in failed: ${e.message}"
                    } finally {
                        isLoading = false
                    }
                }
            } catch (e: ApiException) {
                println("Google Sign-In API exception: ${e.statusCode} - ${e.message}")
                errorMessage = "Google sign in failed: ${e.message}"
            }
        } else {
            println("Google Sign-In cancelled or failed: ${result.resultCode}")
            errorMessage = "Google sign in was cancelled"
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo and Title
            Spacer(modifier = Modifier.height(60.dp))
            
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(100.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Welcome to LawBot",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Sign in to continue",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    TextButton(
                        onClick = { isPasswordVisible = !isPasswordVisible },
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Text(
                            text = if (isPasswordVisible) "Hide" else "Show",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 12.sp
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                shape = RoundedCornerShape(12.dp)
            )
            
            // Error Message
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Sign In Button
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        try {
                            auth.signInWithEmailAndPassword(email, password).await()
                            onLoginSuccess()
                        } catch (e: Exception) {
                            errorMessage = "Sign in failed: ${e.message}"
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = email.isNotEmpty() && password.isNotEmpty() && !isLoading,
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = "Sign In",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Divider
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
                Text(
                    text = "OR",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
                Divider(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Google Sign In Button
            OutlinedButton(
                onClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("429837322189-l4u7dqms96svdchl76dl9mghhat3p141.apps.googleusercontent.com")
                        .requestEmail()
                        .requestProfile()
                        .build()
                    
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    googleSignInLauncher.launch(googleSignInClient.signInIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isLoading,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Google icon (you can add a proper Google icon resource)
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color.Transparent,
                        modifier = Modifier.size(20.dp)
                    ) {
                        // Placeholder for Google icon
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Red, RoundedCornerShape(4.dp))
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Continue with Google",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // GitHub Sign In Button
            OutlinedButton(
                onClick = {
                    scope.launch {
                        isLoading = true
                        try {
                            val auth = FirebaseAuth.getInstance()
                            
                            // Create GitHub OAuth provider
                            val provider = OAuthProvider.newBuilder("github.com")
                                .setScopes(listOf("user:email"))
                                .build()
                            
                            // Start the sign-in process
                            val pendingResultTask = auth.pendingAuthResult
                            if (pendingResultTask != null) {
                                // There's already a sign-in in progress
                                try {
                                    val result = pendingResultTask.await()
                                    println("GitHub sign-in successful")
                                    onLoginSuccess()
                                } catch (e: Exception) {
                                    println("GitHub sign-in failed: ${e.message}")
                                    errorMessage = "GitHub sign-in failed: ${e.message}"
                                }
                            } else {
                                // Start new sign-in
                                auth.startActivityForSignInWithProvider(context as android.app.Activity, provider)
                                    .addOnSuccessListener { authResult ->
                                        println("GitHub sign-in successful: ${authResult.user?.email}")
                                        onLoginSuccess()
                                    }
                                    .addOnFailureListener { exception ->
                                        println("GitHub sign-in failed: ${exception.message}")
                                        errorMessage = "GitHub sign-in failed: ${exception.message}"
                                    }
                            }
                        } catch (e: Exception) {
                            println("GitHub sign-in error: ${e.message}")
                            errorMessage = "GitHub sign-in error: ${e.message}"
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isLoading,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // GitHub icon
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color.Transparent,
                        modifier = Modifier.size(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFF24292E), RoundedCornerShape(4.dp))
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Continue with GitHub",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Sign Up Link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
                TextButton(
                    onClick = onNavigateToSignUp,
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    Text(
                        text = "Sign Up",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
} 