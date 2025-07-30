package com.example.lawbot.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lawbot.viewmodel.ChatViewModel
import com.example.lawbot.viewmodel.ContextProvider
import com.example.lawbot.viewmodel.saveApiKey
import com.example.lawbot.android.ChatScreen
import com.example.lawbot.android.CoverLetterScreen
import com.example.lawbot.android.ResumeScreen
import com.example.lawbot.android.LoginScreen
import com.example.lawbot.android.SignUpScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        ContextProvider.setContext(this)
        
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }
            var isLoggedIn by remember { mutableStateOf(false) }
            var showSignUp by remember { mutableStateOf(false) }
            var currentScreen by remember { mutableStateOf("chat") }
            var showSplash by remember { mutableStateOf(true) }
            
            LaunchedEffect(Unit) {
                delay(2000)
                showSplash = false
            }
            
            LaunchedEffect(Unit) {
                isLoggedIn = FirebaseAuth.getInstance().currentUser != null
            }
            
            MyApplicationTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when {
                        showSplash -> {
                            SplashScreen()
                        }
                        !isLoggedIn && !showSignUp -> {
                            LoginScreen(
                                onLoginSuccess = { isLoggedIn = true },
                                onNavigateToSignUp = { showSignUp = true }
                            )
                        }
                        !isLoggedIn && showSignUp -> {
                            SignUpScreen(
                                onSignUpSuccess = { isLoggedIn = true },
                                onNavigateToLogin = { showSignUp = false }
                            )
                        }
                        isLoggedIn -> {
                            val viewModel = remember { ChatViewModel() }
                            
                            DisposableEffect(viewModel) {
                                onDispose {
                                    viewModel.dispose()
                                }
                            }
                            
                            when (currentScreen) {
                                "chat" -> {
                                    ChatScreen(
                                        viewModel = viewModel,
                                        onThemeChange = { isDarkMode = it },
                                        onLogout = { 
                                            isLoggedIn = false
                                            FirebaseAuth.getInstance().signOut()
                                        },
                                        onCoverLetterClick = { currentScreen = "coverLetter" },
                                        onResumeClick = { currentScreen = "resume" }
                                    )
                                }
                                "coverLetter" -> {
                                    CoverLetterScreen(
                                        onBackClick = { currentScreen = "chat" },
                                        isDarkMode = isDarkMode
                                    )
                                }
                                "resume" -> {
                                    ResumeScreen(
                                        onBackClick = { currentScreen = "chat" },
                                        isDarkMode = isDarkMode
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen() {
    var animationPlayed by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        animationPlayed = true
    }
    
    val scale by animateFloatAsState(
        targetValue = if (animationPlayed) 1f else 0f,
        animationSpec = tween(durationMillis = 1000, easing = EaseOutBack),
        label = "scale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (animationPlayed) 1f else 0f,
        animationSpec = tween(durationMillis = 800, delayMillis = 200),
        label = "alpha"
    )
    
    val rotation by animateFloatAsState(
        targetValue = if (animationPlayed) 360f else 0f,
        animationSpec = tween(durationMillis = 1500, easing = EaseInOutCubic),
        label = "rotation"
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0E1A),
                        Color(0xFF1A2332),
                        Color(0xFF2D3748),
                        Color(0xFF4A5568)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF3B82F6),
                                Color(0xFF1E3A8A)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Gavel,
                    contentDescription = "LegalBot Logo",
                    tint = Color.White,
                    modifier = Modifier
                        .size(60.dp)
                        .scale(scale)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                "LegalBot",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(alpha)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "Your AI Legal Assistant",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(alpha)
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .scale(scale),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .rotate(rotation),
                    color = Color(0xFF64B5F6),
                    strokeWidth = 3.dp
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "Loading...",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(alpha)
            )
        }
        
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .alpha(alpha)
        ) {
            Text(
                "Powered by OpenRouter AI",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        val viewModel = remember { ChatViewModel() }
        ChatScreen(
            viewModel = viewModel,
            onThemeChange = {},
            onLogout = {},
            onCoverLetterClick = {},
            onResumeClick = {}
        )
    }
}
