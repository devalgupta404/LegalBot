package com.example.lawbot.data

data class GeminiRequest(
    val contents: List<Content>
)

data class Content(
    val parts: List<Part>
)

data class Part(
    val text: String
)

data class GeminiResponse(
    val candidates: List<Candidate>?
)

data class Candidate(
    val content: Content
)

data class ChatMessage(
    val content: String,
    val isUser: Boolean,
    val timestamp: Long = 0L,
    val isError: Boolean = false
) 