package com.example.lawbot.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumeScreen(
    onBackClick: () -> Unit,
    isDarkMode: Boolean = false
) {
    val context = LocalContext.current

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
                .padding(16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
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
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = if (isDarkMode) Color(0xFF64B5F6) else Color(0xFF9C27B0),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Resume", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Deval Gupta - Legal Assistant", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            colors = if (isDarkMode) listOf(
                                Color(0xFF2D3748).copy(alpha = 0.9f),
                                Color(0xFF4A5568).copy(alpha = 0.7f)
                            ) else listOf(
                                Color.White.copy(alpha = 0.9f),
                                Color.White.copy(alpha = 0.7f)
                            )
                        )
                    )
                    .border(
                        width = 0.05.dp,
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = if (isDarkMode) Color(0xFF5C6BC0) else Color(0xFF4CAF50),
                        modifier = Modifier.size(64.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        "Deval Gupta",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkMode) Color.White else Color(0xFF1B5E20)
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        "Mobile App Developer & AI Enthusiast",
                        fontSize = 16.sp,
                        color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2E7D32)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        "Attorney-I Legal Assistant Application",
                        fontSize = 14.sp,
                        color = if (isDarkMode) Color.White.copy(alpha = 0.7f) else Color(0xFF388E3C)
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Button(
                        onClick = { 
                            openResumePDF(context)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDarkMode) Color(0xFF5C6BC0) else Color(0xFF4CAF50)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.OpenInNew,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Open Resume PDF")
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isDarkMode) Color(0xFF5C6BC0).copy(alpha = 0.1f)
                                else Color(0xFF4CAF50).copy(alpha = 0.1f)
                            )
                            .border(
                                width = 1.dp,
                                color = if (isDarkMode) Color(0xFF5C6BC0).copy(alpha = 0.3f) else Color(0xFF4CAF50).copy(alpha = 0.3f),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        shadowElevation = 2.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                "Resume Preview",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDarkMode) Color.White else Color(0xFF1B5E20)
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                "• Languages: Kotlin, C, C++",
                                fontSize = 12.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2E7D32)
                            )
                            Text(
                                "• Tools: Jetpack Compose, MVVM, REST APIs, RoomDB",
                                fontSize = 12.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2E7D32)
                            )
                            Text(
                                "• Internship: 23Ventures – UI revamp, real-time error tracking",
                                fontSize = 12.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2E7D32)
                            )
                            Text(
                                "• Projects: TrendTrack, ResQ-GPT, QuickOrder, Sports app, ArtSpace",
                                fontSize = 12.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2E7D32)
                            )
                            Text(
                                "• Achievements: 1st place in ByteXync & CodeGenesis, CodeChef rating 1616",
                                fontSize = 12.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2E7D32)
                            )
                            Text(
                                "• Contests: 50+ CP contests, 2 Hackathons, 2 CTFs",
                                fontSize = 12.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2E7D32)
                            )
                            Text(
                                "• Volunteer: Member of Point Blank tech club",
                                fontSize = 12.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF2E7D32)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun openResumePDF(context: Context) {
    try {
        val inputStream = context.assets.open("resume.pdf")
        val cacheFile = File(context.cacheDir, "resume.pdf")
        val outputStream = FileOutputStream(cacheFile)
        
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()
        
        if (!cacheFile.exists() || cacheFile.length() == 0L) {
            android.widget.Toast.makeText(
                context,
                "Error: Resume file not found or empty",
                android.widget.Toast.LENGTH_LONG
            ).show()
            return
        }
        
        val contentUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            cacheFile
        )
        
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(contentUri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        
        val resolveInfo = context.packageManager.resolveActivity(intent, 0)
        if (resolveInfo != null) {
            context.startActivity(intent)
        } else {
            val alternativeIntent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(contentUri, "*/*")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            
            val altResolveInfo = context.packageManager.resolveActivity(alternativeIntent, 0)
            if (altResolveInfo != null) {
                context.startActivity(alternativeIntent)
            } else {
                android.widget.Toast.makeText(
                    context,
                    "No app found to open PDF files. Please install a PDF reader app.",
                    android.widget.Toast.LENGTH_LONG
                ).show()
            }
        }
    } catch (e: Exception) {
        val errorMessage = "Error opening resume: ${e.message}"
        android.widget.Toast.makeText(
            context,
            errorMessage,
            android.widget.Toast.LENGTH_LONG
        ).show()
        e.printStackTrace()
        
        android.util.Log.e("ResumeScreen", "Error opening resume", e)
        android.util.Log.d("ResumeScreen", "Cache directory: ${context.cacheDir}")
        android.util.Log.d("ResumeScreen", "Package name: ${context.packageName}")
    }
}

 