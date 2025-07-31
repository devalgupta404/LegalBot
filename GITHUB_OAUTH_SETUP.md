# GitHub OAuth Setup Guide

## Prerequisites
1. A GitHub account
2. Firebase project with Authentication enabled
3. Android app configured in Firebase

## Step 1: Create GitHub OAuth App

1. Go to [GitHub Developer Settings](https://github.com/settings/developers)
2. Click "New OAuth App"
3. Fill in the following details:
   - **Application name**: LawBot
   - **Homepage URL**: `https://your-domain.com` (can be any valid URL)
   - **Authorization callback URL**: `com.example.lawbot.android://oauth/callback`
4. Click "Register application"
5. Note down the **Client ID** and **Client Secret**

## Step 2: Configure Firebase Authentication

1. Go to your [Firebase Console](https://console.firebase.google.com)
2. Select your project
3. Go to Authentication > Sign-in method
4. Enable "GitHub" provider
5. Enter the **Client ID** and **Client Secret** from Step 1
6. Save the configuration

## Step 3: Update Android App Configuration

1. In your Firebase project, go to Project Settings
2. Add your Android app if not already added
3. Download the `google-services.json` file and place it in the `LawBot/` directory
4. Make sure the package name matches: `com.example.lawbot.android`

## Step 4: Test the Implementation

1. Build and run your Android app
2. Go to the login screen
3. Click "Continue with GitHub"
4. You should be redirected to GitHub for authorization
5. After authorization, you should be signed in to your app

## Troubleshooting

### Common Issues:

1. **"Invalid redirect URI" error**:
   - Make sure the callback URL in GitHub OAuth app matches exactly: `com.example.lawbot.android://oauth/callback`

2. **"Client ID not found" error**:
   - Verify that the Client ID and Secret are correctly entered in Firebase Console

3. **"App not found" error**:
   - Ensure your Android app is properly registered in Firebase
   - Check that the package name matches in both Firebase and your app

4. **Callback not working**:
   - Verify the intent filter in AndroidManifest.xml is correct
   - Check that the scheme matches: `com.example.lawbot.android`

## Security Notes

- Never commit your Client Secret to version control
- Use environment variables or secure storage for sensitive credentials
- Regularly rotate your OAuth app credentials
- Monitor your OAuth app usage in GitHub Developer Settings

## Additional Configuration

For production apps, consider:
- Adding additional scopes if needed (e.g., `repo` for repository access)
- Implementing proper error handling
- Adding user profile information retrieval
- Setting up proper session management 