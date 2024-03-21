package ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

val consolasFontFamily = FontFamily(
    Font("fonts/consolas_regular.ttf", FontWeight.Normal),
    Font("fonts/consolas_bold.ttf", FontWeight.Bold),
)

val unfocusedLineNumberTextStyle = TextStyle(
    fontFamily = consolasFontFamily,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Normal,
    fontSize = 20.sp,
    color = numberColor
)

val codeTextStyle = TextStyle(
    fontFamily = consolasFontFamily,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Normal,
    fontSize = 20.sp,
)