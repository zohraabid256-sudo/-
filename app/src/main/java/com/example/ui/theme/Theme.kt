package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SanadColorScheme = darkColorScheme(
    primary = GoldBright,
    onPrimary = RoyalBlueDeep,
    primaryContainer = RoyalBluePrimary,
    onPrimaryContainer = GoldLight,
    secondary = PinkGlossy,
    onSecondary = Color.White,
    secondaryContainer = RoyalBlueSecondary,
    onSecondaryContainer = PinkSoft,
    tertiary = GoldAccent,
    onTertiary = RoyalBlueDeep,
    background = RoyalBlueDeep,
    onBackground = TextLightPrimary,
    surface = RoyalBlueSurface,
    onSurface = TextLightPrimary,
    surfaceVariant = CardBackground,
    onSurfaceVariant = TextLightSecondary,
    outline = CardBorder
)

private val SanadLightColorScheme = lightColorScheme(
    primary = RoyalBluePrimary,
    onPrimary = Color.White,
    primaryContainer = GoldLight,
    onPrimaryContainer = RoyalBlueDeep,
    secondary = PinkBadge,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFE4E6),
    onSecondaryContainer = Color(0xFF881337),
    tertiary = GoldMuted,
    onTertiary = Color.White,
    background = Color(0xFFF8FAFC),
    onBackground = Color(0xFF0F172A),
    surface = Color.White,
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = Color(0xFF334155),
    outline = Color(0xFFCBD5E1)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to the signature Royal Blue & Gold theme
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) SanadColorScheme else SanadLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
