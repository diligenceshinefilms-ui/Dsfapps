package com.example.feature.subscription

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.CinematicIcons
import com.example.core.ui.CinematicEmptyState
import com.example.core.ui.CinematicTopBar

@Composable
fun SubscriptionFoundationView() {
    Column(modifier = Modifier.fillMaxSize()) {
        CinematicTopBar(
            title = "Cinematic Membership",
            subtitle = "Free • Creator • Pro • Studio"
        )
        CinematicEmptyState(
            title = "Google Play Subscriptions",
            description = "Free tier: 30s limit. Subscribe for extended duration, priority rendering, and 4K master exports.",
            icon = CinematicIcons.Star
        )
    }
}
