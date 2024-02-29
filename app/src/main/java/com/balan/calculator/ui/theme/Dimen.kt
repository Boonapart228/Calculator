package com.balan.calculator.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val LocalDimen = compositionLocalOf {
    Dimension()
}

data class Dimension(
    val buttonTextSize: TextUnit = 24.sp,
    val numberTextSize: TextUnit = 18.sp,
    val resultTextSize: TextUnit = 28.sp,
    val spaceBetween: Dp = 100.dp,
    val buttonPadding: Dp = 4.dp
)