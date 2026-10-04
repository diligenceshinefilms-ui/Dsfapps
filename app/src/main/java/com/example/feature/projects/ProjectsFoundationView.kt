package com.example.feature.projects

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.CinematicIcons
import com.example.core.ui.CinematicEmptyState
import com.example.core.ui.CinematicTopBar

@Composable
fun ProjectsFoundationView() {
    Column(modifier = Modifier.fillMaxSize()) {
        CinematicTopBar(
            title = "My Cinema Projects",
            subtitle = "Recent, Drafts & Completed Masters"
        )
        CinematicEmptyState(
            title = "No Projects Yet",
            description = "Start your first cinematic project using the creation engine.",
            icon = CinematicIcons.ProjectsFilled
        )
    }
}
