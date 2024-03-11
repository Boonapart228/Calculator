package com.balan.calculator.presentation.screens.main

data class CalculatorState(
    val expression: String = "",
    val errorMessage : Int? = null
)
