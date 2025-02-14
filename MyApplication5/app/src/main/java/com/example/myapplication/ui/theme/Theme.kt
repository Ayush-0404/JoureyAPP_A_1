package com.example.myapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme


@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,  // You can add dark theme logic later
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme,  // Use default scheme for now
        typography = MaterialTheme.typography,    // Default typography
        content = content
    )
}
private val LightColors = lightColorScheme(
    primary = Color(0xFF0D47A1),
    secondary = Color(0xFF1976D2),
    tertiary = Color(0xFF2196F3),
    surface = Color.White,
    onPrimary = Color.White,
    onSurface = Color.Black
//    primary = Color(0xFF0D47A1),
//    secondary = Color(0xFF1976D2),
//    tertiary = Color(0xFF2196F3),
//    surface = Color(0xFFFFFFFF),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onSurface = Color(0xFF000000),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF1565C0),
    secondary = Color(0xFF1E88E5),
    tertiary = Color(0xFF42A5F5),
    surface = Color(0xFF121212),
    onPrimary = Color.White,
    onSurface = Color.White
)
//    primary = Color(0xFF1565C0),
//    secondary = Color(0xFF1E88E5),
//    tertiary = Color(0xFF42A5F5),
//    surface = Color(0xFF121212),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onSurface = Color.White,


@Composable
fun DistTrackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }




    @Composable
    fun MyApplicationTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        val colorScheme = if (darkTheme) DarkColors else LightColors

        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}