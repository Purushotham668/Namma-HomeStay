package com.nammahomestay.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val NammaLightColorScheme = lightColorScheme(
    primary            = DeepEmerald,
    onPrimary          = IvoryWhite,
    primaryContainer   = EmeraldMedium,
    onPrimaryContainer = IvoryWhite,

    secondary          = PremiumGold,
    onSecondary        = DeepEmeraldDark,
    secondaryContainer = GoldenSun,
    onSecondaryContainer = DeepEmeraldDark,

    tertiary           = LeafAccent,
    onTertiary         = IvoryWhite,

    background         = IvoryWhite,
    onBackground       = MidnightBlue,
    surface            = IvoryWhite,
    onSurface          = MidnightBlue,
    surfaceVariant     = SurfaceSoft,
    onSurfaceVariant   = SlateGray,
    outline            = DividerThin,
    error              = ErrorDark,
    onError            = IvoryWhite
)

private val NammaDarkColorScheme = darkColorScheme(
    primary            = EmeraldMedium,
    onPrimary          = IvoryWhite,
    primaryContainer   = DeepEmerald,
    onPrimaryContainer = IvoryWhite,

    secondary          = GoldenSun,
    onSecondary        = DeepEmeraldDark,

    background         = DeepEmeraldDark,
    onBackground       = IvoryWhite,
    surface            = DeepEmerald,
    onSurface          = IvoryWhite,
    surfaceVariant     = DeepEmeraldDark,
    onSurfaceVariant   = WarmSand,
    outline            = EmeraldMedium,
    error              = ErrorRose,
    onError            = ErrorDark
)

@Composable
fun NammaHomeStayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) NammaDarkColorScheme else NammaLightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = NammaTypography,
        content     = content
    )
}
