package com.example.lawbot.api

import com.example.lawbot.data.GeminiRequest
import com.example.lawbot.data.GeminiResponse
import kotlinx.coroutines.delay

// Common interface for API service
interface GeminiApiService {
    suspend fun sendMessage(message: String, apiKey: String): String
    
    companion object {
        private var lastRequestTime = 0L
        private const val MIN_REQUEST_INTERVAL_MS = 1000L
        
        suspend fun enforceRateLimit() {
            val currentTime = System.currentTimeMillis()
            val timeSinceLastRequest = currentTime - lastRequestTime
            
            if (timeSinceLastRequest < MIN_REQUEST_INTERVAL_MS) {
                val delayNeeded = MIN_REQUEST_INTERVAL_MS - timeSinceLastRequest
                delay(delayNeeded)
            }
            
            lastRequestTime = System.currentTimeMillis()
        }
    }
} 