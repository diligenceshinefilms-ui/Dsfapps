package com.example.core.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Primary Cinematic Palette - Diligence Shine Films
val GoldPrimary = Color(0xFFF5C518)       // Iconic cinema gold
val GoldSecondary = Color(0xFFE5A910)     // Warm amber cinema sheen
val GoldDark = Color(0xFFB45309)          // Deep burnished gold
val GoldLight = Color(0xFFFDE68A)         // Pale champagne highlight

// Cinematic Obsidian / Dark Surfaces (Default App Experience)
val ObsidianBlack = Color(0xFF0A0A0C)     // Deep space film black
val ObsidianSurface = Color(0xFF131317)   // Primary dark surface
val ObsidianCard = Color(0xFF1B1B22)      // Elevated card container
val ObsidianElevated = Color(0xFF242430)  // Dropdown / dialog surface
val ObsidianBorder = Color(0xFF2E2E3E)    // Subtle card boundary
val ObsidianDivider = Color(0xFF1F1F2A)   // Subtle hairline divider

// Light Theme Alternates (System / Light Support)
val LightSurface = Color(0xFFF8F9FA)
val LightBackground = Color(0xFFFFFFFF)
val LightCard = Color(0xFFF0F1F5)
val LightCardBorder = Color(0xFFE2E4EB)
val LightTextPrimary = Color(0xFF0F172A)
val LightTextSecondary = Color(0xFF475569)

// Accent Colors
val ElectricCyan = Color(0xFF38BDF8)      // Lens flare / aperture cyan
val CinematicRed = Color(0xFFEF4444)      // Film recording / urgent
val CinematicEmerald = Color(0xFF10B981)  // Success / render complete
val StarGold = Color(0xFFFBBF24)          // Favorite / high rating

// Text Colors (Dark Theme Defaults)
val TextPrimary = Color(0xFFF9FAFB)       // High emphasis title & labels
val TextSecondary = Color(0xFF9CA3AF)     // Medium emphasis subtitles
val TextTertiary = Color(0xFF6B7280)      // De-emphasized hints
val TextGold = Color(0xFFFCD34D)         // Golden accent text

// Gradients
val GoldGradient = Brush.horizontalGradient(
    colors = listOf(GoldPrimary, GoldSecondary)
)

val GoldenGlowGradient = Brush.radialGradient(
    colors = listOf(Color(0x33F5C518), Color(0x000A0A0C))
)

val ObsidianCardGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF202029), Color(0xFF131317))
)

val BorderGlowGradient = Brush.linearGradient(
    colors = listOf(Color(0x66F5C518), Color(0x1A38BDF8), Color(0x33FFFFFF))
)

@Immutable
data class CinematicColors(
    val primaryGold: Color = GoldPrimary,
    val secondaryGold: Color = GoldSecondary,
    val obsidianBackground: Color = ObsidianBlack,
    val surface: Color = ObsidianSurface,
    val surfaceCard: Color = ObsidianCard,
    val surfaceElevated: Color = ObsidianElevated,
    val border: Color = ObsidianBorder,
    val textPrimary: Color = TextPrimary,
    val textSecondary: Color = TextSecondary,
    val textTertiary: Color = TextTertiary,
    val accentCyan: Color = ElectricCyan,
    val accentRed: Color = CinematicRed,
    val accentGreen: Color = CinematicEmerald,
    val goldGradient: Brush = GoldGradient,
    val cardGradient: Brush = ObsidianCardGradient,
    val borderGradient: Brush = BorderGlowGradient,
    val isDark: Boolean = true
)

val LocalCinematicColors = staticCompositionLocalOf { CinematicColors() }
