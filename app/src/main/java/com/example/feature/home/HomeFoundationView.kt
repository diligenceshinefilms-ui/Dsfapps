package com.example.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.CinematicIcons
import com.example.core.designsystem.LocalCinematicColors
import com.example.core.ui.BadgeStyle
import com.example.core.ui.CinematicBadge
import com.example.core.ui.CinematicButton
import com.example.core.ui.CinematicTopBar
import com.example.core.ui.GlassmorphicCard

@Composable
fun HomeFoundationView(
    onCreateClick: () -> Unit = {}
) {
    val colors = LocalCinematicColors.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CinematicTopBar(
            title = "Diligence Shine Films",
            subtitle = "Turn Your Vision Into Cinema."
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            GlassmorphicCard(
                borderGlow = true,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    CinematicBadge(
                        text = "AI CINEMATIC ENGINE",
                        style = BadgeStyle.GOLD
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Turn Your Vision Into Cinema",
                        color = colors.textPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Generate hyper-consistent character videos with facial identity lock, anamorphic framing, and emotive multi-lingual dialogue.",
                        color = colors.textSecondary,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CinematicButton(
                        text = "CREATE CINEMATIC VIDEO",
                        icon = CinematicIcons.AI,
                        onClick = onCreateClick
                    )
                }
            }
        }
    }
}
