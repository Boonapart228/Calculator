package com.balan.calculator.ui.theme

import androidx.compose.runtime.compositionLocalOf

val LocalProperty = compositionLocalOf {
    Property()
}

data class Property(
   val count: Int = 4
)