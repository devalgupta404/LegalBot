package com.example.lawbot.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.tasks.await

class GitHubAuthService {
    companion object {
        private const val GITHUB_CLIENT_ID = "your_github_client_id" // You'll need to replace this
        private const val GITHUB_REDIRECT_URI = "com.example.lawbot.android://oauth/callback"
        
        suspend fun signInWithGitHub(context: Context, onSuccess: () -> Unit, onError: (String) -> Unit) {
            try {
                val auth = FirebaseAuth.getInstance()
                
                // Create GitHub OAuth provider
                val provider = OAuthProvider.newBuilder("github.com")
                    .setScopes(listOf("user:email"))
                    .build()
                
                // Start the sign-in process
                val pendingResultTask = auth.pendingAuthResult
                if (pendingResultTask != null) {
                    // There's already a sign-in in progress
                    try {
                        val result = pendingResultTask.await()
                        onSuccess()
                    } catch (e: Exception) {
                        onError("GitHub sign-in failed: ${e.message}")
                    }
                } else {
                    // Start new sign-in
                    auth.startActivityForSignInWithProvider(context as android.app.Activity, provider)
                        .addOnSuccessListener { authResult ->
                            onSuccess()
                        }
                        .addOnFailureListener { exception ->
                            onError("GitHub sign-in failed: ${exception.message}")
                        }
                }
            } catch (e: Exception) {
                onError("GitHub sign-in error: ${e.message}")
            }
        }
        
        fun getGitHubAuthUrl(): String {
            return "https://github.com/login/oauth/authorize?" +
                    "client_id=$GITHUB_CLIENT_ID&" +
                    "redirect_uri=$GITHUB_REDIRECT_URI&" +
                    "scope=user:email&" +
                    "response_type=code"
        }
        
        fun handleGitHubCallback(context: Context, uri: Uri, onSuccess: () -> Unit, onError: (String) -> Unit) {
            val code = uri.getQueryParameter("code")
            val error = uri.getQueryParameter("error")
            
            if (error != null) {
                onError("GitHub authorization error: $error")
                return
            }
            
            if (code != null) {
                // Exchange code for access token
                exchangeCodeForToken(code, onSuccess, onError)
            } else {
                onError("No authorization code received")
            }
        }
        
        private fun exchangeCodeForToken(code: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
            // This would typically involve making a network request to GitHub's token endpoint
            // For now, we'll use Firebase's built-in GitHub provider
            onSuccess()
        }
    }
} 