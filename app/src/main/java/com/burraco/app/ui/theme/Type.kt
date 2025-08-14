package com.burraco.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.burraco.app.R

val GochiHands = FontFamily(
    Font(R.font.gochi_hand)
)
val PermanentMarker = FontFamily(
    Font(R.font.permanent_marker)
)



// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = GochiHands,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = PermanentMarker,
        fontWeight = FontWeight.Normal,
        fontSize = 85.sp,
        lineHeight = 90.sp,
        letterSpacing = 0.sp,
        color = BurracoBlack
    )

    /*
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)