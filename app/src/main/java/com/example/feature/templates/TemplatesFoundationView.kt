package com.example.feature.templates

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.CinematicIcons
import com.example.core.ui.CinematicEmptyState
import com.example.core.ui.CinematicTopBar

@Composable
fun TemplatesFoundationView() {
    Column(modifier = Modifier.fillMaxSize()) {
        CinematicTopBar(
            title = "Cinema Presets",
            subtitle = "Hollywood, Bollywood & Noir Styles"
        )
        CinematicEmptyState(
            title = "Curated Templates",
            description = "Explore director-grade lighting, camera movement, and aesthetic presets.",
            icon = CinematicIcons.TemplatesFilled
        )
    }
}
