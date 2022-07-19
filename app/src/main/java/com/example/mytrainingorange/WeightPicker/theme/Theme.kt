package com.example.mytrainingorange.WeightPicker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,

    /* Other default colors to override */
    //background = androidx.compose.ui.graphics.Color.Gray,
    surface = androidx.compose.ui.graphics.Color.LightGray,
    //onPrimary = Color.White,
    ///onSecondary = Color.Black,
    //onBackground = Color.Black,
    onSurface = androidx.compose.ui.graphics.Color.LightGray,
    /**/
)

private val LightColorPalette = lightColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,

    /* Other default colors to override */
    //background = androidx.compose.ui.graphics.Color.Gray,
    surface = androidx.compose.ui.graphics.Color.LightGray,
    //onPrimary = Color.White,
    ///onSecondary = Color.Black,
    //onBackground = Color.Black,
    onSurface = androidx.compose.ui.graphics.Color.LightGray,
    /**/
)

@Composable
fun CanvasWeightPickerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}