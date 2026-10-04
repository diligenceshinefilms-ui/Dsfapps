package com.example.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.CinematicIcons
import com.example.core.ui.CinematicEmptyState
import com.example.core.ui.CinematicTopBar

@Composable
fun ProfileFoundationView() {
    Column(modifier = Modifier.fillMaxSize()) {
        CinematicTopBar(
            title = "Director Profile",
            subtitle = "Subscription & Generation Quota"
        )
        CinematicEmptyState(
            title = "Account & Settings",
            description = "Manage your cloud sync, plan entitlements, and custom voice models.",
            icon = CinematicIcons.ProfileFilled
        )
    }
}
