package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = LuxuryGold,
    onPrimary = Color.Black,
    primaryContainer = LuxuryGoldDark,
    onPrimaryContainer = LuxuryGoldLight,
    secondary = EmeraldGreen,
    onSecondary = Color.Black,
    secondaryContainer = EmeraldGreenDark,
    onSecondaryContainer = EmeraldGreenLight,
    tertiary = LuxuryGoldLight,
    onTertiary = Color.Black,
    background = LuxuryNavyDark,
    onBackground = TextPrimary,
    surface = LuxuryNavyMedium,
    onSurface = TextPrimary,
    surfaceVariant = LuxuryNavySurface,
    onSurfaceVariant = TextSecondary,
    outline = LuxuryNavyBorder,
    error = DangerRed,
    onError = Color.White
)

private val LightColorScheme = darkColorScheme( // We prefer a rich luxury dark theme by default for real estate prestige
    primary = LuxuryGold,
    onPrimary = Color.Black,
    primaryContainer = LuxuryGoldDark,
    onPrimaryContainer = LuxuryGoldLight,
    secondary = EmeraldGreen,
    onSecondary = Color.Black,
    secondaryContainer = EmeraldGreenDark,
    onSecondaryContainer = EmeraldGreenLight,
    background = LuxuryNavyDark,
    onBackground = TextPrimary,
    surface = LuxuryNavyMedium,
    onSurface = TextPrimary,
    surfaceVariant = LuxuryNavySurface,
    onSurfaceVariant = TextSecondary,
    outline = LuxuryNavyBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // We enforce the luxury Royal Navy & Gold palette
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
