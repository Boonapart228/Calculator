package com.balan.calculator.ui.theme

import androidx.compose.runtime.compositionLocalOf

val LocalProperty = compositionLocalOf {
    Properties()
}

data class Properties(
   val columnCount: Int = 4
)