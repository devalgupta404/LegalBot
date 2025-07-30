# OpenRouter Integration for LawBot

## Overview

LawBot has been successfully migrated from direct Gemini API to OpenRouter API, providing access to multiple AI models and potentially better reliability.

## What Changed

### 1. **API Endpoint Migration**

- **From**: `https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent`
- **To**: `https://openrouter.ai/api/v1/chat/completions`

### 2. **Request Format**

- **Old**: Gemini-specific format with `contents` and `parts`
- **New**: OpenAI-compatible format with `messages` array

### 3. **Authentication**

- **Old**: API key as query parameter
- **New**: Bearer token in Authorization header

## Available Models

You can easily switch between different AI models by changing the `OPENROUTER_MODEL` in `ApiConfig.kt`:

```kotlin
// Current model (Gemini 1.5 Flash)
const val OPENROUTER_MODEL = "google/gemini-1.5-flash"

// Alternative models you can use:
// const val OPENROUTER_MODEL = "anthropic/claude-3.5-sonnet"  // Claude 3.5 Sonnet
// const val OPENROUTER_MODEL = "openai/gpt-4"                 // GPT-4
// const val OPENROUTER_MODEL = "openai/gpt-3.5-turbo"         // GPT-3.5 Turbo
// const val OPENROUTER_MODEL = "meta-llama/llama-3.1-8b-instruct" // Llama 3.1
```

## Setup Instructions

### 1. **Get OpenRouter API Key**

1. Visit [openrouter.ai](https://openrouter.ai)
2. Sign up for an account
3. Go to "Keys" section
4. Create a new API key
5. Copy the key (starts with `sk-or-v1-`)

### 2. **Update API Key**

Replace the placeholder in both files:

- `shared/src/androidMain/kotlin/com/example/lawbot/viewmodel/AndroidChatViewModel.kt`
- `shared/src/iosMain/kotlin/com/example/lawbot/viewmodel/IOSChatViewModel.kt`

```kotlin
actual fun getDefaultApiKey(): String {
    return "sk-or-v1-your-actual-api-key-here"
}
```

### 3. **Choose Your Model**

Edit `ApiConfig.kt` to select your preferred model:

```kotlin
// For Gemini 1.5 Flash (current)
const val OPENROUTER_MODEL = "google/gemini-1.5-flash"

// For Claude 3.5 Sonnet
const val OPENROUTER_MODEL = "anthropic/claude-3.5-sonnet"

// For GPT-4
const val OPENROUTER_MODEL = "openai/gpt-4"
```

## Benefits of OpenRouter

### 1. **Multiple Models**

- Access to 100+ AI models
- Easy switching between providers
- Best model for your use case

### 2. **Better Reliability**

- Multiple fallback options
- Less likely to hit rate limits
- Better uptime

### 3. **Cost Optimization**

- Compare pricing across models
- Choose most cost-effective option
- Pay-per-use model

### 4. **Advanced Features**

- Model comparison
- Usage analytics
- Custom routing rules

## Model Comparison

| Model                         | Provider  | Speed  | Quality   | Cost   |
| ----------------------------- | --------- | ------ | --------- | ------ |
| `google/gemini-1.5-flash`     | Google    | Fast   | High      | Low    |
| `anthropic/claude-3.5-sonnet` | Anthropic | Medium | Very High | Medium |
| `openai/gpt-4`                | OpenAI    | Slow   | Very High | High   |
| `openai/gpt-3.5-turbo`        | OpenAI    | Fast   | Good      | Low    |

## Error Handling

The app includes comprehensive error handling for OpenRouter:

- **401**: Invalid API key
- **429**: Rate limit exceeded
- **503**: Service temporarily unavailable
- **Network errors**: Automatic retry with exponential backoff

## Usage Examples

### Legal Research

```
User: "What are the key elements of a valid contract?"
Model: google/gemini-1.5-flash
```

### Case Analysis

```
User: "Explain the Miranda rights in simple terms"
Model: anthropic/claude-3.5-sonnet
```

### Document Review

```
User: "Help me understand this employment agreement"
Model: openai/gpt-4
```

## Configuration Options

All settings are in `ApiConfig.kt`:

```kotlin
object ApiConfig {
    // Model selection
    const val OPENROUTER_MODEL = "google/gemini-1.5-flash"

    // Retry settings
    const val MAX_RETRY_ATTEMPTS = 3
    const val INITIAL_RETRY_DELAY_MS = 1000L

    // Rate limiting
    const val MIN_REQUEST_INTERVAL_MS = 1000L

    // Timeouts
    const val CONNECT_TIMEOUT_SECONDS = 30L
    const val READ_TIMEOUT_SECONDS = 60L
}
```

## Troubleshooting

### Common Issues

1. **"Invalid API key"**

   - Check your OpenRouter API key
   - Ensure it starts with `sk-or-v1-`

2. **"Model not found"**

   - Verify the model name in `ApiConfig.kt`
   - Check OpenRouter's model list

3. **Rate limiting**

   - The app automatically handles this
   - Consider upgrading your OpenRouter plan

4. **Network errors**
   - Automatic retry is enabled
   - Check your internet connection

## Migration Notes

- ✅ All retry logic preserved
- ✅ Error handling enhanced
- ✅ Rate limiting maintained
- ✅ UI unchanged
- ✅ Cross-platform compatibility

## Next Steps

1. **Get your OpenRouter API key**
2. **Update the key in the code**
3. **Choose your preferred model**
4. **Test the app**
5. **Monitor usage and costs**

## Support

- OpenRouter Documentation: [docs.openrouter.ai](https://docs.openrouter.ai)
- Model List: [openrouter.ai/models](https://openrouter.ai/models)
- Pricing: [openrouter.ai/pricing](https://openrouter.ai/pricing)
