package com.example.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.BorderGlowGradient
import com.example.core.designsystem.CinemaCardShape
import com.example.core.designsystem.LocalCinematicColors
import com.example.core.designsystem.LocalSpacing
import com.example.core.designsystem.ObsidianCardGradient

@Composable
fun GlassmorphicCard(
    modifier: Modifier = Modifier,
    shape: Shape = CinemaCardShape,
    borderGlow: Boolean = false,
    contentPadding: Dp = 16.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val colors = LocalCinematicColors.current
    val borderBrush = if (borderGlow) {
        BorderGlowGradient
    } else {
        Brush.verticalGradient(
            colors = listOf(
                colors.border.copy(alpha = 0.8f),
                colors.border.copy(alpha = 0.3f)
            )
        )
    }

    val clickableModifier = if (onClick != null) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .clip(shape)
            .background(colors.cardGradient)
            .border(width = 1.dp, brush = borderBrush, shape = shape)
            .then(clickableModifier)
            .padding(contentPadding),
        content = content
    )
}
