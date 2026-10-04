package com.example.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.CinematicIcons
import com.example.core.ui.CinematicEmptyState
import com.example.core.ui.CinematicTopBar

@Composable
fun SettingsFoundationView() {
    Column(modifier = Modifier.fillMaxSize()) {
        CinematicTopBar(
            title = "Studio Preferences",
            subtitle = "Theme, Cache & Account Management"
        )
        CinematicEmptyState(
            title = "Preferences",
            description = "Dark/Light theme toggle, default aspect ratio, and account deletion controls.",
            icon = CinematicIcons.Settings
        )
    }
}
