package com.example.lawbot.android

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoverLetterScreen(
    onBackClick: () -> Unit,
    isDarkMode: Boolean = false
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
                        Text(
                            "Cover Letter", 
                            fontWeight = FontWeight.Bold, 
                            fontSize = 18.sp,
                            color = if (isDarkMode) Color.White else Color(0xFF4A148C)
                        )
                        Text(
                            "Attorney-I Legal Assistant", 
                            fontSize = 12.sp, 
                            color = if (isDarkMode) Color.White.copy(alpha = 0.7f) else Color(0xFF6A1B9A)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    CoverLetterContent(isDarkMode)
                }
            }
        }
    }
}

@Composable
fun CoverLetterContent(isDarkMode: Boolean) {
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
                        Color.White.copy(alpha = 0.95f),
                        Color(0xFFF3E5F5).copy(alpha = 0.8f)
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
                .padding(20.dp)
        ) {
            Text(
                "Deval Gupta",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkMode) Color.White else Color(0xFF4A148C)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                "devalgupta4@gmail.com | +91-8209055082",
                fontSize = 12.sp,
                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF6A1B9A)
            )
            Text(
                "linkedin.com/in/devalgupta4 | github.com/devalgupta404",
                fontSize = 12.sp,
                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF6A1B9A)
            )
            Text(
                "Bengaluru, India",
                fontSize = 12.sp,
                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF6A1B9A)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "July 31, 2025",
                fontSize = 12.sp,
                color = if (isDarkMode) Color.White.copy(alpha = 0.7f) else Color(0xFF6A1B9A)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Hiring Manager",
                fontSize = 12.sp,
                color = if (isDarkMode) Color.White.copy(alpha = 0.7f) else Color(0xFF6A1B9A)
            )
            Text(
                "Attorney-I",
                fontSize = 12.sp,
                color = if (isDarkMode) Color.White.copy(alpha = 0.7f) else Color(0xFF6A1B9A)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Subject: Application for Mobile App Development Intern Position",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkMode) Color.White else Color(0xFF4A148C)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Dear Hiring Manager,",
                fontSize = 12.sp,
                color = if (isDarkMode) Color.White else Color(0xFF4A148C)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "I am writing to express my interest in the Mobile App Development Intern position at Attorney-I. " +
                "With a strong foundation in computer science and a deep interest in mobile development, " +
                "I bring a unique blend of software development expertise and a passion for building " +
                "innovative mobile applications that can make a real impact.",
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = if (isDarkMode) Color.White else Color(0xFF2D3748)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "During my recent internship at 23Ventures, I led the redesign of a mobile application " +
                "using Jetpack Compose and modern UI principles, significantly improving user experience. " +
                "I've also developed ResQ-GPT, an AI-powered offline assistant designed for disaster " +
                "response—a project showcasing my ability to build critical mobile solutions with real-world impact.",
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = if (isDarkMode) Color.White else Color(0xFF2D3748)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "What excites me about your Mobile App Development Intern role at Attorney-I is the opportunity " +
                "to contribute to innovative legal technologies. I recently developed LawBot, a mobile chatbot " +
                "prototype aimed at simplifying access to legal information. I believe such mobile tools can " +
                "play a vital role in empowering individuals and enhancing legal workflows through accessible technology.",
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = if (isDarkMode) Color.White else Color(0xFF2D3748)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "I am eager to bring my mobile development skills, creative problem-solving, and enthusiasm for " +
                "legal-tech innovation to your team. Thank you for considering my application. I would " +
                "welcome the opportunity to discuss how I can contribute to Attorney-I's mobile development mission.",
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = if (isDarkMode) Color.White else Color(0xFF2D3748)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Sincerely,",
                fontSize = 12.sp,
                color = if (isDarkMode) Color.White else Color(0xFF4A148C)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Deval Gupta",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkMode) Color.White else Color(0xFF4A148C)
            )
        }
    }
} 