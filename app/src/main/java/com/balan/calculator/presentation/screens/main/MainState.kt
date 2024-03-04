package com.balan.calculator.presentation.screens.main

import androidx.annotation.StringRes

data class MainState(
    val expression: String = "",
    @StringRes val errorMessage : Int? = null
)
