package com.example.domain.voice

enum class SupportedLanguage(val code: String, val displayName: String) {
    ENGLISH("en", "English"),
    HINDI("hi", "Hindi (हिन्दी)"),
    MARATHI("mr", "Marathi (मराठी)")
}

enum class VoiceStyle(val label: String) {
    DEEP("Deep"),
    MATURE("Mature"),
    EMOTIONAL("Emotional"),
    POWERFUL("Powerful"),
    CINEMATIC("Cinematic"),
    CALM("Calm"),
    DRAMATIC("Dramatic")
}

data class VoiceSettings(
    val language: SupportedLanguage = SupportedLanguage.ENGLISH,
    val gender: String = "Male",
    val style: VoiceStyle = VoiceStyle.CINEMATIC,
    val speed: Float = 1.0f,
    val pitch: Float = 1.0f,
    val intensity: Float = 0.85f
)

data class VoiceProfile(
    val id: String,
    val name: String,
    val language: SupportedLanguage,
    val gender: String,
    val previewAudioUrl: String? = null,
    val defaultStyle: VoiceStyle = VoiceStyle.CINEMATIC
)
