package com.example.feature.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.CinematicIcons
import com.example.core.ui.CinematicEmptyState
import com.example.core.ui.CinematicTopBar

@Composable
fun ReportFoundationView() {
    Column(modifier = Modifier.fillMaxSize()) {
        CinematicTopBar(
            title = "Content Safety & Moderation",
            subtitle = "Report Generated Video"
        )
        CinematicEmptyState(
            title = "Safety Reporting Engine",
            description = "Report inappropriate or unsafe generated clips for human and AI moderation review.",
            icon = CinematicIcons.Flag
        )
    }
}
