# Firebase Authentication Setup Guide

This guide will help you set up Firebase Authentication for Google and GitHub login in your LawBot app.

## Prerequisites

1. A Google account
2. Android Studio
3. Your LawBot project

## Step 1: Create a Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click "Create a project" or "Add project"
3. Enter a project name (e.g., "LawBot")
4. Choose whether to enable Google Analytics (recommended)
5. Click "Create project"

## Step 2: Add Android App to Firebase

1. In your Firebase project console, click the Android icon (</>) to add an Android app
2. Enter your package name: `com.example.lawbot.android`
3. Enter app nickname: "LawBot"
4. Click "Register app"
5. Download the `google-services.json` file
6. Replace the placeholder `google-services.json` file in the `LawBot/` directory with the downloaded file

## Step 3: Enable Authentication Methods

### Email/Password Authentication

1. In Firebase Console, go to "Authentication" → "Sign-in method"
2. Click on "Email/Password"
3. Enable "Email/Password" provider
4. Click "Save"

### Google Authentication

1. In Firebase Console, go to "Authentication" → "Sign-in method"
2. Click on "Google"
3. Enable Google provider
4. Add your support email
5. Click "Save"

### GitHub Authentication (Optional)

1. In Firebase Console, go to "Authentication" → "Sign-in method"
2. Click on "GitHub"
3. Enable GitHub provider
4. You'll need to create a GitHub OAuth app:
   - Go to GitHub Settings → Developer settings → OAuth Apps
   - Click "New OAuth App"
   - Application name: "LawBot"
   - Homepage URL: `https://your-project-id.firebaseapp.com`
   - Authorization callback URL: `https://your-project-id.firebaseapp.com/__/auth/handler`
5. Copy the Client ID and Client Secret to Firebase
6. Click "Save"

## Step 4: Update Google Sign-In Configuration

1. In Firebase Console, go to "Project settings" → "General"
2. Scroll down to "Your apps" section
3. Find your Android app and click on it
4. Copy the "Web client ID" (it looks like: `123456789-abcdefghijklmnop.apps.googleusercontent.com`)
5. Update the `LoginScreen.kt` and `SignUpScreen.kt` files:
   - Replace `"your-web-client-id"` with your actual Web client ID

## Step 5: Test the Authentication

1. Build and run your app
2. Try signing up with email/password
3. Try signing in with Google
4. Test the logout functionality

## Troubleshooting

### Common Issues:

1. **"Google sign in failed"**

   - Make sure you've enabled Google Authentication in Firebase
   - Verify the Web client ID is correct
   - Check that the package name matches

2. **"Sign in failed: The email address is already in use"**

   - This means the email is already registered with a different provider
   - Try signing in with the original provider

3. **"Network error"**
   - Check your internet connection
   - Verify Firebase project is properly configured

### For GitHub Authentication:

If you want to implement GitHub authentication, you'll need to:

1. Set up GitHub OAuth app as described above
2. Add GitHub OAuth implementation to the login/signup screens
3. Handle GitHub authentication flow using Firebase Auth

## Security Notes

1. Never commit your actual `google-services.json` file to version control
2. Add `google-services.json` to your `.gitignore` file
3. Keep your API keys secure
4. Use Firebase Security Rules to protect your data

## Next Steps

After setting up authentication:

1. Add user profile management
2. Implement password reset functionality
3. Add email verification
4. Set up Firebase Security Rules
5. Add user data storage in Firestore (if needed)

## Support

If you encounter issues:

1. Check Firebase Console for error logs
2. Review Firebase Authentication documentation
3. Check Android Studio logs for detailed error messages
4. Verify all configuration steps are completed correctly
