package com.example.data.voice

import com.example.core.common.Resource
import com.example.domain.voice.SupportedLanguage
import com.example.domain.voice.VoiceProfile
import com.example.domain.voice.VoiceSettings

interface VoiceRepository {
    suspend fun getAvailableVoices(language: SupportedLanguage? = null): Resource<List<VoiceProfile>>
    suspend fun synthesizeSpeechPreview(text: String, settings: VoiceSettings): Resource<String>
}
