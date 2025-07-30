package com.example.lawbot.config

object ApiConfig {
    // Retry configuration
    const val MAX_RETRY_ATTEMPTS = 3
    const val INITIAL_RETRY_DELAY_MS = 1000L
    const val MAX_RETRY_DELAY_MS = 8000L
    
    // Rate limiting
    const val MIN_REQUEST_INTERVAL_MS = 1000L
    
    // Timeout configuration
    const val CONNECT_TIMEOUT_SECONDS = 30L
    const val READ_TIMEOUT_SECONDS = 60L
    const val WRITE_TIMEOUT_SECONDS = 30L
    
    // OpenRouter API endpoints
    const val OPENROUTER_BASE_URL = "https://openrouter.ai"
    const val OPENROUTER_MODEL = "openai/gpt-3.5-turbo" // or "anthropic/claude-3.5-sonnet" or "google/gemini-1.5-flash-exp"
    
    // Legacy Gemini endpoints (for reference)
    const val GEMINI_BASE_URL = "https://generativelanguage.googleapis.com"
    const val GEMINI_MODEL = "gemini-1.5-flash-latest"
    
    fun calculateBackoffDelay(attempt: Int): Long {
        return (INITIAL_RETRY_DELAY_MS * (1 shl (attempt - 1))).coerceAtMost(MAX_RETRY_DELAY_MS)
    }
} 