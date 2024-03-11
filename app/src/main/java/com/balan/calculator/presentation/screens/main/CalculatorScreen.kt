package com.balan.calculator.presentation.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.balan.calculator.presentation.screens.main.components.CalculatorContent

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    val state by viewModel.state.collectAsState()
    CalculatorContent(
        expression = state.expression,
        onNumberClick = viewModel::onNumberClick,
        onButtonClick = viewModel::onButtonClick,
        errorMessage = state.errorMessage
    )
}
