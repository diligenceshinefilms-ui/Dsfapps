package com.example.feature.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.CinematicIcons
import com.example.core.ui.CinematicEmptyState
import com.example.core.ui.CinematicTopBar

@Composable
fun EditorFoundationView() {
    Column(modifier = Modifier.fillMaxSize()) {
        CinematicTopBar(
            title = "Cinematic Studio Editor",
            subtitle = "Media3 Timeline & Multi-track Sequencing"
        )
        CinematicEmptyState(
            title = "Video Editor Ready",
            description = "Trim, reorder, splice, grade, and synchronize lip-sync tracks.",
            icon = CinematicIcons.Cut
        )
    }
}
