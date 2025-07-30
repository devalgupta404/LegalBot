package com.example.lawbot.api

import com.example.lawbot.config.ApiConfig
import com.example.lawbot.data.OpenRouterRequest
import com.example.lawbot.data.OpenRouterResponse
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface OpenRouterApi {
    @POST("api/v1/chat/completions")
    suspend fun chatCompletions(
        @Header("Authorization") authorization: String,
        @Header("HTTP-Referer") referer: String = "https://lawbot.app",
        @Header("X-Title") title: String = "LawBot",
        @Body request: OpenRouterRequest
    ): OpenRouterResponse
}

class AndroidOpenRouterApiService : GeminiApiService {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(ApiConfig.CONNECT_TIMEOUT_SECONDS, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(ApiConfig.READ_TIMEOUT_SECONDS, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(ApiConfig.WRITE_TIMEOUT_SECONDS, java.util.concurrent.TimeUnit.SECONDS)
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiConfig.OPENROUTER_BASE_URL + "/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    private val api = retrofit.create(OpenRouterApi::class.java)
    
    override suspend fun sendMessage(message: String, apiKey: String): String {
        // Enforce rate limiting
        GeminiApiService.enforceRateLimit()
        
        val maxRetries = ApiConfig.MAX_RETRY_ATTEMPTS
        var lastException: Exception? = null
        
        for (attempt in 1..maxRetries) {
            try {
                val legalSystemPrompt = """
                    You are LegalBot, a specialized AI legal assistant focused on laws and advocacy practices. Your role is to:
                    
                    1. **Provide legal information and guidance** on various areas of law
                    2. **Explain legal concepts** in clear, understandable terms
                    3. **Discuss advocacy practices** and strategies
                    4. **Offer general legal education** and awareness
                    5. **Help with legal research** and understanding legal documents
                    
                    **Important Disclaimers:**
                    - You provide general legal information only, not specific legal advice
                    - Always recommend consulting with qualified legal professionals for specific cases
                    - You cannot represent clients or provide attorney-client relationships
                    - Information provided is for educational purposes only
                    
                    **Areas of Focus:**
                    - Constitutional Law
                    - Civil Rights and Civil Liberties
                    - Criminal Law and Procedure
                    - Family Law
                    - Employment Law
                    - Immigration Law
                    - Environmental Law
                    - Human Rights Advocacy
                    - Legal Research Methods
                    - Court Procedures
                    - Legal Writing and Documentation
                    
                    Respond in a professional, helpful manner while maintaining legal accuracy and ethical boundaries.
                """.trimIndent()
                
                val request = OpenRouterRequest(
                    model = ApiConfig.OPENROUTER_MODEL,
                    messages = listOf(
                        com.example.lawbot.data.OpenRouterMessage(
                            role = "system",
                            content = legalSystemPrompt
                        ),
                        com.example.lawbot.data.OpenRouterMessage(
                            role = "user",
                            content = message
                        )
                    ),
                    temperature = 0.7,
                    max_tokens = 1000
                )
                
                println("Sending request to OpenRouter API (attempt $attempt/$maxRetries) with model: ${ApiConfig.OPENROUTER_MODEL}")
                
                val response = api.chatCompletions(
                    authorization = "Bearer $apiKey",
                    request = request
                )
                println("Received response on attempt $attempt: $response")
                
                // Check for errors first
                if (response.error != null) {
                    throw Exception("OpenRouter API Error: ${response.error.message}")
                }
                
                return response.choices?.firstOrNull()?.message?.content 
                    ?: "Sorry, I couldn't generate a response."
                    
            } catch (e: Exception) {
                lastException = e
                println("API Error on attempt $attempt: ${e.message}")
                
                // Check if this is a retryable error
                val isRetryable = isRetryableError(e)
                
                if (attempt < maxRetries && isRetryable) {
                    val delayMs = ApiConfig.calculateBackoffDelay(attempt)
                    println("Retrying in ${delayMs}ms...")
                    delay(delayMs.toLong())
                } else {
                    break
                }
            }
        }
        
        // If we get here, all retries failed
        return handleFinalError(lastException)
    }
    
    private fun isRetryableError(exception: Exception): Boolean {
        return when {
            // Network-related errors
            exception is SocketTimeoutException -> true
            exception is UnknownHostException -> true
            exception.message?.contains("timeout", ignoreCase = true) == true -> true
            
            // Server errors (5xx)
            exception.message?.contains("500") == true -> true
            exception.message?.contains("502") == true -> true
            exception.message?.contains("503") == true -> true
            exception.message?.contains("504") == true -> true
            
            // Rate limiting and temporary unavailability
            exception.message?.contains("429") == true -> true
            exception.message?.contains("overloaded", ignoreCase = true) == true -> true
            exception.message?.contains("busy", ignoreCase = true) == true -> true
            exception.message?.contains("temporarily", ignoreCase = true) == true -> true
            exception.message?.contains("unavailable", ignoreCase = true) == true -> true
            
            // Connection errors
            exception.message?.contains("connection", ignoreCase = true) == true -> true
            exception.message?.contains("network", ignoreCase = true) == true -> true
            
            else -> false
        }
    }
    
    private fun handleFinalError(exception: Exception?): String {
        return when {
            exception?.message?.contains("503") == true -> 
                "The AI service is temporarily busy. Please try again in a few minutes."
            exception?.message?.contains("429") == true -> 
                "Rate limit exceeded. Please wait a moment before trying again."
            exception?.message?.contains("overloaded", ignoreCase = true) == true -> 
                "The AI model is currently overloaded. Please try again in 5-10 minutes."
            exception?.message?.contains("timeout", ignoreCase = true) == true -> 
                "Request timed out. Please check your internet connection and try again."
            exception?.message?.contains("connection", ignoreCase = true) == true -> 
                "Connection error. Please check your internet connection and try again."
            exception?.message?.contains("401") == true -> 
                "Invalid API key. Please check your OpenRouter API key."
            exception?.message?.contains("403") == true -> 
                "Access denied. Please check your API key and permissions."
            exception?.message?.contains("400") == true -> 
                "Invalid request. Please check your input and try again."
            exception?.message?.contains("OpenRouter API Error") == true ->
                "OpenRouter API Error: ${exception.message}"
            else -> "Sorry, I encountered an unexpected error. Please try again later."
        }
    }
} 