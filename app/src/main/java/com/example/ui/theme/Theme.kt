package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
  primary = ToyRedPrimary,
  onPrimary = Color.White,
  primaryContainer = ToyRedLight,
  onPrimaryContainer = ToyRedDark,
  secondary = ToyBlueSecondary,
  onSecondary = Color.White,
  secondaryContainer = ToyBlueLight,
  onSecondaryContainer = ToyBlueDark,
  tertiary = ToyYellowTertiary,
  onTertiary = Color.Black,
  tertiaryContainer = ToyYellowLight,
  onTertiaryContainer = Color(0xFF5D4037),
  background = BackgroundClean,
  onBackground = TextPrimaryDark,
  surface = SurfaceWhite,
  onSurface = TextPrimaryDark,
  surfaceVariant = SurfaceVariantSoft,
  onSurfaceVariant = TextSecondaryMuted,
  outline = BorderSubtle
)

private val DarkColorScheme = darkColorScheme(
  primary = ToyRedPrimary,
  onPrimary = Color.White,
  primaryContainer = Color(0xFF5C0A0A),
  onPrimaryContainer = ToyRedLight,
  secondary = ToyBlueSecondary,
  onSecondary = Color.White,
  secondaryContainer = Color(0xFF003865),
  onSecondaryContainer = ToyBlueLight,
  tertiary = ToyYellowTertiary,
  onTertiary = Color.Black,
  background = Color(0xFF0F172A),
  surface = Color(0xFF1E293B),
  onBackground = Color.White,
  onSurface = Color.White,
  surfaceVariant = Color(0xFF334155),
  onSurfaceVariant = Color(0xFFCBD5E1),
  outline = Color(0xFF475569)
)

@Composable
fun ToysMartTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep consistent toy store branding
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
