package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalThemeIsLight = staticCompositionLocalOf { false }

private val DarkDeveloperColorScheme = darkColorScheme(
    primary = ElectricCyanPrimary,
    onPrimary = OnPrimary,
    primaryContainer = CyanPrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = SoftBlueSecondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = EmeraldTertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    background = DeepMatteBackground,
    onBackground = TextHighContrastWhite,
    surface = DeepMatteBackground,
    onSurface = TextHighContrastWhite,
    surfaceVariant = SurfaceContainerHigh,
    onSurfaceVariant = TextMutedGrey,
    surfaceContainerLowest = SurfaceContainerLowest,
    surfaceContainerLow = SurfaceContainerLow,
    surfaceContainer = SurfaceContainer,
    surfaceContainerHigh = SurfaceContainerHigh,
    surfaceContainerHighest = SurfaceContainerHighest,
    surfaceBright = SurfaceBright,
    outline = TextOutline,
    outlineVariant = OutlineVariant,
    error = ErrorRed,
    errorContainer = ErrorContainer
)

private val HighContrastLightColorScheme = lightColorScheme(
    primary = HighContrastLightPrimary,
    onPrimary = Color.White,
    primaryContainer = HighContrastLightPrimaryContainer,
    onPrimaryContainer = Color(0xFF0369A1),
    secondary = HighContrastLightSecondary,
    onSecondary = Color.White,
    secondaryContainer = HighContrastLightSecondaryContainer,
    onSecondaryContainer = Color(0xFF1E40AF),
    tertiary = HighContrastLightTertiary,
    onTertiary = Color.White,
    tertiaryContainer = HighContrastLightTertiaryContainer,
    onTertiaryContainer = Color(0xFF065F46),
    background = HighContrastLightBackground,
    onBackground = HighContrastLightTextPrimary,
    surface = HighContrastLightSurface,
    onSurface = HighContrastLightTextPrimary,
    surfaceVariant = HighContrastLightCardHigh,
    onSurfaceVariant = HighContrastLightTextMuted,
    surfaceContainerLowest = HighContrastLightSurface,
    surfaceContainerLow = HighContrastLightBackground,
    surfaceContainer = HighContrastLightCard,
    surfaceContainerHigh = HighContrastLightCardHigh,
    surfaceContainerHighest = HighContrastLightBorder,
    surfaceBright = HighContrastLightSurface,
    outline = HighContrastLightBorderGlow,
    outlineVariant = HighContrastLightBorder,
    error = HighContrastLightError,
    errorContainer = HighContrastLightErrorContainer
)

@Composable
fun StudyFlowTheme(
    isLightMode: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (isLightMode) HighContrastLightColorScheme else DarkDeveloperColorScheme

    CompositionLocalProvider(LocalThemeIsLight provides isLightMode) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

