import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType

fun main() = runBlocking {
    val client = OkHttpClient()
    
    val json = """
        {
            "contents": [
                {
                    "parts": [
                        {
                            "text": "Hello"
                        }
                    ]
                }
            ]
        }
    """.trimIndent()
    
    val requestBody = json.toRequestBody("application/json".toMediaType())
    
    val request = Request.Builder()
        .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=AIzaSyAlXWpbF54J3KxGwf5zTfKH0z6tJvQKjjg")
        .post(requestBody)
        .build()
    
    try {
        val response = client.newCall(request).execute()
        println("Response Code: ${response.code}")
        println("Response Body: ${response.body?.string()}")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
} 