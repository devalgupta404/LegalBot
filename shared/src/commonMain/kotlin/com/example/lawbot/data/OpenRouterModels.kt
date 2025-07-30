package com.example.lawbot.data

import kotlinx.serialization.Serializable

@Serializable
data class OpenRouterRequest(
    val model: String,
    val messages: List<OpenRouterMessage>,
    val temperature: Double = 0.7,
    val max_tokens: Int = 1000
)

@Serializable
data class OpenRouterMessage(
    val role: String, // "user", "assistant", "system"
    val content: String
)

@Serializable
data class OpenRouterResponse(
    val choices: List<OpenRouterChoice>?,
    val usage: OpenRouterUsage?,
    val error: OpenRouterError?
)

@Serializable
data class OpenRouterChoice(
    val message: OpenRouterMessage,
    val finish_reason: String?
)

@Serializable
data class OpenRouterUsage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

@Serializable
data class OpenRouterError(
    val message: String,
    val type: String?,
    val code: String?
)

 