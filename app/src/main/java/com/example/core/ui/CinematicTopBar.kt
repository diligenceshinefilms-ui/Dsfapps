package com.example.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.CinematicIcons
import com.example.core.designsystem.GoldPrimary
import com.example.core.designsystem.LocalCinematicColors
import com.example.core.designsystem.PillShape

@Composable
fun CinematicTopBar(
    title: String = "DILIGENCE SHINE FILMS",
    subtitle: String? = "Turn Your Vision Into Cinema.",
    showBack: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    trailingIcon: ImageVector? = null,
    onTrailingClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val colors = LocalCinematicColors.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.obsidianBackground)
            .windowInsetsPadding(WindowInsets.statusBars)
            .border(
                width = 0.5.dp,
                color = colors.border.copy(alpha = 0.5f)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBack && onBackClick != null) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("topbar_back_button")
                ) {
                    Icon(
                        imageVector = CinematicIcons.Back,
                        contentDescription = "Back",
                        tint = colors.textPrimary
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            } else {
                // Branded Golden Aperture Icon
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(colors.surfaceElevated, PillShape)
                        .border(1.dp, GoldPrimary.copy(alpha = 0.5f), PillShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = CinematicIcons.CreateFilled,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title.uppercase(),
                    color = colors.textPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = GoldPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.3.sp
                    )
                }
            }

            if (trailingIcon != null && onTrailingClick != null) {
                IconButton(
                    onClick = onTrailingClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        tint = colors.textSecondary
                    )
                }
            }
        }
    }
}
