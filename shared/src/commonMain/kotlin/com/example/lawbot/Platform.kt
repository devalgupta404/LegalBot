package com.example.lawbot

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform