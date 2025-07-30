package com.example.lawbot.viewmodel

import com.example.lawbot.api.IOSOpenRouterApiService
import kotlinx.coroutines.Dispatchers

actual fun getApiService(): com.example.lawbot.api.GeminiApiService {
    return IOSOpenRouterApiService()
}

actual fun getMainDispatcher(): kotlinx.coroutines.CoroutineDispatcher {
    return Dispatchers.Main
}

actual fun getDefaultApiKey(): String {
    return "" // No default API key for security
}

actual fun getUseDefaultKey(): Boolean {
    return false // Require users to enter their own API key
}

actual fun getCurrentTime(): Long {
    return kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
} 