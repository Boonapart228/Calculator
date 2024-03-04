package com.balan.calculator.presentation.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.balan.calculator.presentation.screens.main.components.CalculatorContent
import com.balan.calculator.presentation.screens.main.components.ErrorMessage


@Composable
fun CalculatorScreen(viewModel : CalculatorViewModel) {
    val state by viewModel.state.collectAsState()

    if (state.errorMessage == null) {
        CalculatorContent(expression = state.expression, viewModel = viewModel)
    } else {
        ErrorMessage(messageResId = state.errorMessage!!, viewModel)
    }
}


