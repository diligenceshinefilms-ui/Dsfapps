package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.core.designsystem.CinematicColors
import com.example.core.designsystem.CinematicEmerald
import com.example.core.designsystem.CinematicRed
import com.example.core.designsystem.CinematicShapes
import com.example.core.designsystem.ElectricCyan
import com.example.core.designsystem.GoldDark
import com.example.core.designsystem.GoldLight
import com.example.core.designsystem.GoldPrimary
import com.example.core.designsystem.GoldSecondary
import com.example.core.designsystem.LightBackground
import com.example.core.designsystem.LightCard
import com.example.core.designsystem.LightCardBorder
import com.example.core.designsystem.LightSurface
import com.example.core.designsystem.LightTextPrimary
import com.example.core.designsystem.LightTextSecondary
import com.example.core.designsystem.LocalCinematicColors
import com.example.core.designsystem.LocalSpacing
import com.example.core.designsystem.ObsidianBlack
import com.example.core.designsystem.ObsidianBorder
import com.example.core.designsystem.ObsidianCard
import com.example.core.designsystem.ObsidianElevated
import com.example.core.designsystem.ObsidianSurface
import com.example.core.designsystem.Spacing
import com.example.core.designsystem.TextPrimary
import com.example.core.designsystem.TextSecondary

// Default Dark Cinematic Scheme - The signature aesthetic of Diligence Shine Films
private val CinematicDarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = ObsidianBlack,
    primaryContainer = GoldDark,
    onPrimaryContainer = GoldLight,
    secondary = GoldSecondary,
    onSecondary = ObsidianBlack,
    secondaryContainer = ObsidianElevated,
    onSecondaryContainer = GoldLight,
    tertiary = ElectricCyan,
    onTertiary = ObsidianBlack,
    background = ObsidianBlack,
    onBackground = TextPrimary,
    surface = ObsidianSurface,
    onSurface = TextPrimary,
    surfaceVariant = ObsidianCard,
    onSurfaceVariant = TextSecondary,
    surfaceContainer = ObsidianCard,
    surfaceContainerHigh = ObsidianElevated,
    outline = ObsidianBorder,
    outlineVariant = Color(0xFF232330),
    error = CinematicRed,
    onError = Color.White
)

// Supported Clean Light Theme
private val CinematicLightColorScheme = lightColorScheme(
    primary = GoldDark,
    onPrimary = Color.White,
    primaryContainer = GoldLight,
    onPrimaryContainer = ObsidianBlack,
    secondary = GoldSecondary,
    onSecondary = ObsidianBlack,
    secondaryContainer = LightCard,
    onSecondaryContainer = LightTextPrimary,
    tertiary = ElectricCyan,
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightCard,
    onSurfaceVariant = LightTextSecondary,
    outline = LightCardBorder,
    error = CinematicRed,
    onError = Color.White
)

@Composable
fun DsfTheme(
    darkTheme: Boolean = true, // Default to dark cinematic experience
    dynamicColor: Boolean = false, // Keep branded cinematic palette by default
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> CinematicDarkColorScheme
        else -> CinematicLightColorScheme
    }

    val cinematicColors = if (darkTheme) {
        CinematicColors(
            primaryGold = GoldPrimary,
            secondaryGold = GoldSecondary,
            obsidianBackground = ObsidianBlack,
            surface = ObsidianSurface,
            surfaceCard = ObsidianCard,
            surfaceElevated = ObsidianElevated,
            border = ObsidianBorder,
            textPrimary = TextPrimary,
            textSecondary = TextSecondary,
            accentCyan = ElectricCyan,
            accentRed = CinematicRed,
            accentGreen = CinematicEmerald,
            isDark = true
        )
    } else {
        CinematicColors(
            primaryGold = GoldDark,
            secondaryGold = GoldSecondary,
            obsidianBackground = LightBackground,
            surface = LightSurface,
            surfaceCard = LightCard,
            surfaceElevated = LightSurface,
            border = LightCardBorder,
            textPrimary = LightTextPrimary,
            textSecondary = LightTextSecondary,
            accentCyan = ElectricCyan,
            accentRed = CinematicRed,
            accentGreen = CinematicEmerald,
            isDark = false
        )
    }

    val spacing = Spacing()

    CompositionLocalProvider(
        LocalCinematicColors provides cinematicColors,
        LocalSpacing provides spacing
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = CinematicShapes,
            content = content
        )
    }
}

// Backwards-compatible alias for existing callers
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    DsfTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
        content = content
    )
}
