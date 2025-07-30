package com.example.lawbot.viewmodel

import android.content.Context
import android.content.SharedPreferences
import com.example.lawbot.api.AndroidOpenRouterApiService
import kotlinx.coroutines.Dispatchers

object ContextProvider {
    private var context: Context? = null
    
    fun setContext(ctx: Context) {
        context = ctx.applicationContext
    }
    
    fun getContext(): Context {
        return context ?: throw IllegalStateException("Context not initialized")
    }
}

actual fun getApiService(): com.example.lawbot.api.GeminiApiService {
    return AndroidOpenRouterApiService()
}

actual fun getMainDispatcher(): kotlinx.coroutines.CoroutineDispatcher {
    return Dispatchers.Main
}

actual fun getDefaultApiKey(): String {
    return getStoredApiKey()
}

actual fun getUseDefaultKey(): Boolean {
    return getStoredApiKey().isNotEmpty()
}

actual fun getCurrentTime(): Long {
    return System.currentTimeMillis()
}

actual fun saveApiKeyToStorage(key: String) {
    saveApiKey(key)
}

private fun getStoredApiKey(): String {
    val context = ContextProvider.getContext()
    val prefs: SharedPreferences = context.getSharedPreferences("LegalBotPrefs", Context.MODE_PRIVATE)
    return prefs.getString("api_key", "") ?: ""
}

fun saveApiKey(key: String) {
    val context = ContextProvider.getContext()
    val prefs: SharedPreferences = context.getSharedPreferences("LegalBotPrefs", Context.MODE_PRIVATE)
    prefs.edit().putString("api_key", key).apply()
}

fun clearApiKey() {
    val context = ContextProvider.getContext()
    val prefs: SharedPreferences = context.getSharedPreferences("LegalBotPrefs", Context.MODE_PRIVATE)
    prefs.edit().remove("api_key").apply()
} 