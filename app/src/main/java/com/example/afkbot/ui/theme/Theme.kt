package com.example.afkbot.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightGreen = Color(0xFF2E7D32)
private val LightGreenVariant = Color(0xFF81C784)
private val DarkGreen = Color(0xFF1B5E20)
private val BackgroundLight = Color(0xFFF5F7F5)
private val SurfaceLight = Color.White

private val DarkGreenLight = Color(0xFF81C784)
private val DarkGreenDark = Color(0xFF2E7D32)
private val DarkGreenVariant = Color(0xFFA5D6A7)
private val BackgroundDark = Color(0xFF101510)
private val SurfaceDark = Color(0xFF1B211C)

private val LightColors = lightColorScheme(
    primary = LightGreen,
    onPrimary = Color.White,
    primaryContainer = LightGreenVariant,
    secondary = LightGreenVariant,
    onSecondary = Color.Black,
    tertiary = DarkGreen,
    background = BackgroundLight,
    surface = SurfaceLight,
    outline = Color(0xFF999999)
)

private val DarkColors = darkColorScheme(
    primary = DarkGreenLight,
    onPrimary = Color.Black,
    primaryContainer = DarkGreenDark,
    secondary = DarkGreenDark,
    onSecondary = Color.White,
    tertiary = DarkGreenVariant,
    background = BackgroundDark,
    surface = SurfaceDark,
    outline = Color(0xFF666666)
)

@Composable
fun MinecraftAfkBotTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
