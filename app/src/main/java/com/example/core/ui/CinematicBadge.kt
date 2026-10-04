package com.example.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.ElectricCyan
import com.example.core.designsystem.GoldDark
import com.example.core.designsystem.GoldPrimary
import com.example.core.designsystem.TagShape

enum class BadgeStyle {
    GOLD,
    CYAN,
    DEFAULT,
    MUTED
}

@Composable
fun CinematicBadge(
    text: String,
    modifier: Modifier = Modifier,
    style: BadgeStyle = BadgeStyle.GOLD
) {
    val (bgColor, textColor, borderColor) = when (style) {
        BadgeStyle.GOLD -> Triple(
            Color(0x2BF5C518),
            GoldPrimary,
            Color(0x66F5C518)
        )
        BadgeStyle.CYAN -> Triple(
            Color(0x2B38BDF8),
            ElectricCyan,
            Color(0x6638BDF8)
        )
        BadgeStyle.DEFAULT -> Triple(
            Color(0x24FFFFFF),
            Color.White,
            Color(0x33FFFFFF)
        )
        BadgeStyle.MUTED -> Triple(
            Color(0x1A9CA3AF),
            Color(0xFF9CA3AF),
            Color(0x1F9CA3AF)
        )
    }

    Box(
        modifier = modifier
            .clip(TagShape)
            .background(bgColor)
            .border(0.75.dp, borderColor, TagShape)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
    }
}
