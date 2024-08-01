package com.target.targetcasestudy.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = PaletteColors(
    primary = colorPrimaryDark,
    secondary = colorSecondary,
    tertiary = grayColor,
    isLight = false,
    background = nightBackground,
    backgroundShade = nightBackgroundShade,
    primaryText = nightPrimaryText,
    secondaryText = nightSecondaryText,
    divider = nightDivider,
    buttonText = buttonText
)

private val LightColorScheme = PaletteColors(
    primary = colorPrimary,
    secondary = colorSecondary,
    tertiary = grayColor,
    isLight = true,
    background = dayBackground,
    backgroundShade = dayBackgroundShade,
    primaryText = dayPrimaryText,
    secondaryText = daySecondaryText,
    divider = dayDivider,
    buttonText = buttonText
)

@Stable
class PaletteColors(
    primary: Color,
    secondary: Color,
    tertiary: Color,
    background: Color,
    backgroundShade: Color,
    primaryText: Color,
    secondaryText: Color,
    isLight: Boolean,
    divider: Color,
    buttonText: Color
) {
    var primary by mutableStateOf(primary)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var tertiary by mutableStateOf(tertiary)
        private set
    var background by mutableStateOf(background)
        private set
    var backgroundShade by mutableStateOf(backgroundShade)
        private set
    var primaryText by mutableStateOf(primaryText)
        private set
    var secondaryText by mutableStateOf(secondaryText)
        private set
    var isLight by mutableStateOf(isLight)
        private set
    var divider by mutableStateOf(divider)
        private set
    var buttonText by mutableStateOf(buttonText)
        private set

    fun update(other: PaletteColors) {
        primary = other.primary
        secondary = other.secondary
        tertiary = other.tertiary
        background = other.background
        backgroundShade = other.backgroundShade
        primaryText = other.primaryText
        secondaryText = other.secondaryText
        isLight = other.isLight
        divider = other.divider
        buttonText = other.buttonText
    }
}

@Composable
fun ProvideColors(
    colors: PaletteColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalColors provides colorPalette, content = content)
}

val LocalColors = staticCompositionLocalOf<PaletteColors> {
    error("Please use the Theme DSL around your view")
}

fun materialColorWrapper(
    paletteColors: PaletteColors
) = Colors (
    primary = paletteColors.primary,
    primaryVariant = paletteColors.primary,
    secondary = paletteColors.secondary,
    secondaryVariant = paletteColors.secondary,
    background = paletteColors.background,
    surface = paletteColors.background,
    error = Color.Unspecified,
    onPrimary = paletteColors.primaryText,
    onSecondary = paletteColors.secondaryText,
    onBackground = paletteColors.background,
    onSurface = paletteColors.background,
    onError = Color.Unspecified,
    isLight = paletteColors.isLight
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val color = if (darkTheme) DarkColorScheme else LightColorScheme
    ProvideColors(color) {
        MaterialTheme(
            colors = materialColorWrapper(color),
            typography = MaterialTheme.typography,
            content = content
        )
    }
}

object Themes {
    val colors: PaletteColors
        @Composable
        get() = LocalColors.current
}