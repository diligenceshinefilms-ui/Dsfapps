package com.example.feature.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.CinematicIcons
import com.example.core.ui.CinematicEmptyState
import com.example.core.ui.CinematicTopBar

@Composable
fun CreateFoundationView(
    onBackClick: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CinematicTopBar(
            title = "Create Cinema Video",
            subtitle = "10-Step Directed Workflow",
            showBack = true,
            onBackClick = onBackClick
        )
        CinematicEmptyState(
            title = "Creation Pipeline Standby",
            description = "Reference Image, Facial Lock, Prompt Matrix, and Voice synthesizer await Phase 4 activation.",
            icon = CinematicIcons.CreateFilled
        )
    }
}
