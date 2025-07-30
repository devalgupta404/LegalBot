package com.example.lawbot.viewmodel

import com.example.lawbot.api.GeminiApiService
import com.example.lawbot.data.ChatMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel {
    private val apiService = getApiService()
    private val coroutineScope = CoroutineScope(getMainDispatcher())
    
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _isRetrying = MutableStateFlow(false)
    val isRetrying: StateFlow<Boolean> = _isRetrying.asStateFlow()
    
    private val _apiKey = MutableStateFlow(getDefaultApiKey())
    val apiKey: StateFlow<String> = _apiKey.asStateFlow()
    
    val useDefaultKey: Boolean = getUseDefaultKey()
    
    fun setApiKey(key: String) {
        _apiKey.value = key
        saveApiKeyToStorage(key)
    }
    
    fun loadStoredApiKey() {
        val storedKey = getDefaultApiKey()
        if (storedKey.isNotEmpty()) {
            _apiKey.value = storedKey
        }
    }
    
    fun sendMessage(message: String) {
        if (message.isBlank() || _apiKey.value.isBlank()) return
        
        val userMessage = ChatMessage(message, true, getCurrentTime())
        _messages.value = _messages.value + userMessage
        
        _isLoading.value = true
        _isRetrying.value = false
        
        coroutineScope.launch {
            try {
                val response = apiService.sendMessage(message, _apiKey.value)
                val botMessage = ChatMessage(response, false, getCurrentTime())
                _messages.value = _messages.value + botMessage
            } catch (e: Exception) {
                val errorMessage = ChatMessage("Error: ${e.message}", false, getCurrentTime(), isError = true)
                _messages.value = _messages.value + errorMessage
            } finally {
                _isLoading.value = false
                _isRetrying.value = false
            }
        }
    }
    
    fun retryLastMessage() {
        val lastUserMessage = _messages.value.lastOrNull { it.isUser }
        if (lastUserMessage != null) {
            val messagesWithoutError = _messages.value.dropLastWhile { it.isError }
            _messages.value = messagesWithoutError
            sendMessage(lastUserMessage.content)
        }
    }
    
    fun clearChat() {
        _messages.value = emptyList()
    }
    
    fun dispose() {
    }
}

expect fun getApiService(): GeminiApiService
expect fun getMainDispatcher(): kotlinx.coroutines.CoroutineDispatcher
expect fun getDefaultApiKey(): String
expect fun getUseDefaultKey(): Boolean
expect fun getCurrentTime(): Long
expect fun saveApiKeyToStorage(key: String) 