package com.example.lawbot.android

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.DialogProperties
import com.example.lawbot.data.ChatMessage
import com.example.lawbot.viewmodel.ChatViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    onThemeChange: (Boolean) -> Unit = {},
    onLogout: () -> Unit = {},
    onCoverLetterClick: () -> Unit = {},
    onResumeClick: () -> Unit = {}
) {
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isRetrying by viewModel.isRetrying.collectAsState()
    val apiKey by viewModel.apiKey.collectAsState()

    var messageText by remember { mutableStateOf("") }
    var showApiKeyDialog by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(false) }
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var scope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.loadStoredApiKey()
    }

    LaunchedEffect(apiKey) {
        if (apiKey.isEmpty()) {
            showApiKeyDialog = true
        }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            colors = if (isDarkMode) listOf(
                                Color(0xFF0F1419),
                                Color(0xFF1A2332),
                                Color(0xFF2D3748)
                            ) else listOf(
                                Color(0xFFF3E5F5),
                                Color(0xFFE1BEE7),
                                Color(0xFFCE93D8)
                            )
                        )
                    )
                    .fillMaxHeight(),
                drawerContainerColor = Color.Transparent
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = null,
                        tint = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Attorney-I",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkMode) Color.White else Color(0xFF4A148C)
                    )
                    Text(
                        "Legal Assistant Assignment",
                        fontSize = 14.sp,
                        color = if (isDarkMode) Color.White.copy(alpha = 0.7f) else Color(0xFF6A1B9A)
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Description, contentDescription = null) },
                    label = { Text("Cover Letter") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onCoverLetterClick()
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Download, contentDescription = null) },
                    label = { Text("View Resume") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onResumeClick()
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.ExitToApp, contentDescription = null) },
                    label = { Text("Logout") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        FirebaseAuth.getInstance().signOut()
                        onLogout()
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = if (isDarkMode) listOf(
                            Color(0xFF0A0E1A),
                            Color(0xFF1A2332),
                            Color(0xFF2D3748),
                            Color(0xFF4A5568)
                        ) else listOf(
                            Color(0xFFF8F5FF),
                            Color(0xFFF3E5F5),
                            Color(0xFFE1BEE7),
                            Color(0xFFCE93D8)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .navigationBarsPadding()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                colors = if (isDarkMode) listOf(
                                    Color(0xFF1E3A8A).copy(alpha = 0.4f),
                                    Color(0xFF3B82F6).copy(alpha = 0.3f),
                                    Color(0xFF60A5FA).copy(alpha = 0.2f)
                                ) else listOf(
                                    Color(0xFF9C27B0).copy(alpha = 0.4f),
                                    Color(0xFFBA68C8).copy(alpha = 0.3f),
                                    Color(0xFFCE93D8).copy(alpha = 0.2f)
                                )
                            )
                        )
                        .border(
                            width = 0.05.dp,
                            color = Color.White.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    shadowElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "LegalBot", 
                                fontWeight = FontWeight.Bold, 
                                fontSize = 18.sp,
                                color = if (isDarkMode) Color.White else Color(0xFF4A148C)
                            )
                            Text(
                                text = if (isLoading) if (isRetrying) "Retrying..." else "Researching..." else "Legal Assistant",
                                fontSize = 12.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.7f) else Color(0xFF6A1B9A)
                            )
                        }
                        IconButton(onClick = {
                            isDarkMode = !isDarkMode
                            onThemeChange(isDarkMode)
                        }) {
                            Icon(
                                imageVector = if (isDarkMode) Icons.Default.NightsStay else Icons.Default.WbSunny,
                                contentDescription = "Toggle Theme",
                                tint = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0)
                            )
                        }
                        IconButton(onClick = { showApiKeyDialog = true }) {
                            Icon(
                                Icons.Default.Settings, 
                                contentDescription = "API Key",
                                tint = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0)
                            )
                        }
                        IconButton(onClick = viewModel::clearChat) {
                            Icon(
                                Icons.Default.Clear, 
                                contentDescription = "Clear",
                                tint = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0)
                            )
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    if (messages.isEmpty()) item { WelcomeMessage(isDarkMode, apiKey.isNotEmpty()) }
                    items(messages) {
                        ChatMessageItem(it, viewModel::retryLastMessage, isDarkMode)
                    }
                    if (isLoading) item { LoadingMessage(isDarkMode) }
                }

                ChatInput(
                    value = messageText,
                    onValueChange = { messageText = it },
                    onSendClick = {
                        if (messageText.isNotBlank()) {
                            viewModel.sendMessage(messageText)
                            messageText = ""
                        }
                    },
                    isLoading = isLoading,
                    isDarkMode = isDarkMode
                )
            }

            if (showApiKeyDialog) {
                ApiKeyDialog(
                    currentKey = apiKey,
                    onKeySet = {
                        viewModel.setApiKey(it)
                        showApiKeyDialog = false
                    },
                    onDismiss = { showApiKeyDialog = false },
                    isDarkMode = isDarkMode
                )
            }
        }
    }
}

@Composable
fun ChatMessageItem(message: ChatMessage, onRetry: () -> Unit, isDarkMode: Boolean) {
    val isUser = message.isUser
    val isError = message.isError

    val textColor = when {
        isUser -> Color.White
        isError -> Color.White
        else -> if (isDarkMode) Color.White else Color(0xFF2D3748)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isUser) IconBox(Icons.Default.Info, isDarkMode)
        Column {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = when {
                            isUser -> Brush.linearGradient(
                                colors = if (isDarkMode) listOf(
                                    Color(0xFF3B82F6),
                                    Color(0xFF1E3A8A)
                                ) else listOf(
                                    Color(0xFF9C27B0),
                                    Color(0xFF7B1FA2)
                                )
                            )
                            isError -> Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFEF4444),
                                    Color(0xFFDC2626)
                                )
                            )
                            else -> Brush.linearGradient(
                                colors = if (isDarkMode) listOf(
                                    Color(0xFF2D3748).copy(alpha = 0.9f),
                                    Color(0xFF4A5568).copy(alpha = 0.7f)
                                ) else listOf(
                                    Color.White.copy(alpha = 0.95f),
                                    Color(0xFFF3E5F5).copy(alpha = 0.8f)
                                )
                            )
                        }
                    )
                    .shadow(2.dp, RoundedCornerShape(10.dp))
                    .padding(10.dp)
                    .widthIn(max = 300.dp)
            ) {
                Text(message.content, color = textColor, fontSize = 15.sp)
            }

            if (isError && !isUser) {
                TextButton(onClick = onRetry) {
                    Icon(Icons.Default.Refresh, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Retry")
                }
            }
        }
        if (isUser) IconBox(Icons.Default.Person, isDarkMode)
    }
}

@Composable
fun IconBox(icon: ImageVector, isDarkMode: Boolean) {
    Surface(
        shape = CircleShape,
        modifier = Modifier
            .size(32.dp)
            .padding(4.dp)
            .background(
                Brush.radialGradient(
                    colors = if (isDarkMode) listOf(
                        Color(0xFF3B82F6).copy(alpha = 0.4f),
                        Color(0xFF1E3A8A).copy(alpha = 0.2f)
                    ) else listOf(
                        Color(0xFF9C27B0).copy(alpha = 0.4f),
                        Color(0xFF7B1FA2).copy(alpha = 0.2f)
                    )
                ),
                shape = CircleShape
            )
            .shadow(2.dp, CircleShape)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Icon(
                icon, 
                contentDescription = null, 
                tint = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0), 
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun LoadingMessage(isDarkMode: Boolean) {
    Row(modifier = Modifier.fillMaxWidth()) {
        IconBox(Icons.Default.Info, isDarkMode)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.linearGradient(
                        colors = if (isDarkMode) listOf(
                            Color(0xFF2D3748).copy(alpha = 0.9f),
                            Color(0xFF4A5568).copy(alpha = 0.7f)
                        ) else listOf(
                            Color.White.copy(alpha = 0.95f),
                            Color(0xFFF3E5F5).copy(alpha = 0.8f)
                        )
                    )
                )
                .shadow(2.dp, RoundedCornerShape(16.dp))
                .padding(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Thinking...", 
                    fontSize = 14.sp,
                    color = if (isDarkMode) Color.White else Color(0xFF2D3748)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    isLoading: Boolean,
    isDarkMode: Boolean
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    colors = if (isDarkMode) listOf(
                        Color(0xFF1E3A8A).copy(alpha = 0.4f),
                        Color(0xFF3B82F6).copy(alpha = 0.3f),
                        Color(0xFF60A5FA).copy(alpha = 0.2f)
                    ) else listOf(
                        Color(0xFF7B1FA2).copy(alpha = 0.4f),
                        Color(0xFF9C27B0).copy(alpha = 0.3f),
                        Color(0xFFBA68C8).copy(alpha = 0.2f)
                    )
                )
            )
            .border(
                width = 0.05.dp,
                color = Color.White.copy(alpha = 0.3f)
            ),
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text("Type a message...")},
                modifier = Modifier
                    .weight(1f)
                    .shadow(1.dp, RoundedCornerShape(10.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send,
                    autoCorrect = true
                ),
                keyboardActions = KeyboardActions(
                    onSend = { onSendClick() }
                ),
                maxLines = 4,
                singleLine = false
            )
            Spacer(modifier = Modifier.width(6.dp))
            IconButton(
                onClick = { if (!isLoading && value.isNotBlank()) onSendClick() },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        brush = if (value.isNotBlank()) {
                            Brush.radialGradient(
                                colors = if (isDarkMode) listOf(
                                    Color(0xFF3B82F6),
                                    Color(0xFF1E3A8A)
                                ) else listOf(
                                    Color(0xFF9C27B0),
                                    Color(0xFF7B1FA2)
                                )
                            )
                        } else {
                            Brush.radialGradient(
                                colors = if (isDarkMode) listOf(
                                    Color(0xFF2D3748).copy(alpha = 0.3f),
                                    Color(0xFF4A5568).copy(alpha = 0.1f)
                                ) else listOf(
                                    Color(0xFFE1BEE7).copy(alpha = 0.3f),
                                    Color(0xFFCE93D8).copy(alpha = 0.1f)
                                )
                            )
                        },
                        shape = CircleShape
                    )
            ) {
                Icon(
                    Icons.Default.Send,
                    contentDescription = "Send",
                    tint = if (value.isNotBlank()) Color.White else if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiKeyDialog(currentKey: String, onKeySet: (String) -> Unit, onDismiss: () -> Unit, isDarkMode: Boolean) {
    var key by remember { mutableStateOf(currentKey) }
    var showInstructions by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { 
            Text(
                "Enter OpenRouter API Key",
                color = if (isDarkMode) Color.White else Color(0xFF4A148C)
            ) 
        },
        text = {
            Column {
                Text(
                    "For security reasons, you need to provide your own OpenRouter API key.",
                    fontSize = 14.sp,
                    color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2D3748)
                )
                Spacer(Modifier.height(8.dp))
                
                TextButton(
                    onClick = { showInstructions = !showInstructions },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0)
                    )
                ) {
                    Icon(
                        if (showInstructions) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(if (showInstructions) "Hide Instructions" else "How to get API Key")
                }
                
                if (showInstructions) {
                    Spacer(Modifier.height(8.dp))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isDarkMode) Color(0xFF1A2332).copy(alpha = 0.8f)
                                else Color(0xFFF3E5F5).copy(alpha = 0.8f)
                            ),
                        color = Color.Transparent
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                "Step-by-step guide:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0)
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "1. Go to openrouter.ai",
                                fontSize = 11.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2D3748)
                            )
                            Text(
                                "2. Click 'Sign Up' or 'Login'",
                                fontSize = 11.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2D3748)
                            )
                            Text(
                                "3. Go to 'API Keys' section",
                                fontSize = 11.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2D3748)
                            )
                            Text(
                                "4. Click 'Create Key'",
                                fontSize = 11.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2D3748)
                            )
                            Text(
                                "5. Copy the generated key",
                                fontSize = 11.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2D3748)
                            )
                            Text(
                                "6. Paste it below",
                                fontSize = 11.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2D3748)
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "Note: Free tier includes 100 requests/day",
                                fontSize = 10.sp,
                                color = if (isDarkMode) Color(0xFF64B5F6).copy(alpha = 0.8f) else Color(0xFF9C27B0).copy(alpha = 0.8f),
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }
                    }
                }
                
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = key, 
                    onValueChange = { key = it }, 
                    label = { Text("API Key") }, 
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0),
                        unfocusedBorderColor = if (isDarkMode) Color(0xFF4A5568) else Color(0xFFCE93D8)
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onKeySet(key) }, 
                enabled = key.isNotBlank()
            ) { 
                Text(
                    "Set Key",
                    color = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0)
                ) 
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { 
                Text(
                    "Cancel",
                    color = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0)
                ) 
            }
        },
        containerColor = if (isDarkMode) Color(0xFF2D3748) else Color.White,
        titleContentColor = if (isDarkMode) Color.White else Color(0xFF4A148C),
        textContentColor = if (isDarkMode) Color.White else Color(0xFF2D3748)
    )
}

@Composable
fun WelcomeMessage(isDarkMode: Boolean, hasApiKey: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconBox(Icons.Default.Info, isDarkMode)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Welcome to LegalBot", 
            fontSize = 22.sp, 
            fontWeight = FontWeight.Bold,
            color = if (isDarkMode) Color.White else Color(0xFF4A148C)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "I can help with legal rights, court procedures, and advocacy strategies.",
            fontSize = 14.sp,
            color = if (isDarkMode) Color.White.copy(alpha = 0.7f) else Color(0xFF6A1B9A),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        
        if (!hasApiKey) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Please enter your OpenRouter API key to start chatting.",
                fontSize = 12.sp,
                color = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Tap the settings icon (⚙️) above to enter your key",
                fontSize = 10.sp,
                color = if (isDarkMode) Color.White.copy(alpha = 0.6f) else Color(0xFF6A1B9A).copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SuggestedTopicChip("Civil Rights", Icons.Default.Info, isDarkMode)
            SuggestedTopicChip("Legal Aid", Icons.Default.Info, isDarkMode)
        }
    }
}

@Composable
fun SuggestedTopicChip(text: String, icon: ImageVector, isDarkMode: Boolean) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (isDarkMode) Color(0xFF2D3748).copy(alpha = 0.8f) else Color(0xFFF3E5F5).copy(alpha = 0.9f)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon, 
                contentDescription = null, 
                modifier = Modifier.size(16.dp),
                tint = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text, 
                fontSize = 13.sp,
                color = if (isDarkMode) Color.White else Color(0xFF4A148C)
            )
        }
    }
}
