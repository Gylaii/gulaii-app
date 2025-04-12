package org.gulaii.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.gulaii.app.R.font.futura_heavy
import org.gulaii.app.R.font.futura_light
import org.gulaii.app.R.font.futura_medium

val FuturaFamily = FontFamily(
    Font(futura_heavy, weight = FontWeight.Bold),
    Font(futura_medium, weight = FontWeight.Medium),
    Font(futura_light, weight = FontWeight.Light)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FuturaFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FuturaFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FuturaFamily,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

)