package com.example.feature.voice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.CinematicIcons
import com.example.core.ui.CinematicEmptyState
import com.example.core.ui.CinematicTopBar

@Composable
fun VoiceFoundationView() {
    Column(modifier = Modifier.fillMaxSize()) {
        CinematicTopBar(
            title = "Voice & Dialogue Studio",
            subtitle = "Hindi • Marathi • English Synthesis"
        )
        CinematicEmptyState(
            title = "Voice Profiles",
            description = "Explore Deep, Emotional, Powerful, and Cinematic voice actors.",
            icon = CinematicIcons.Mic
        )
    }
}
