package com.balan.calculator.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.balan.calculator.domain.model.buttonsText
import com.balan.calculator.presentation.screens.main.layout.CalculatorNumber
import com.balan.calculator.presentation.screens.main.layout.CalculatorText
import com.balan.calculator.ui.theme.Background
import com.balan.calculator.ui.theme.LocalDimen
import com.balan.calculator.ui.theme.LocalProperty


@Preview(showSystemUi = true)
@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    if (state.errorMessage == null) {
        Calculator(expression = state.expression, viewModel = viewModel)
    } else {
        ErrorMessage(errorMessageRes = state.errorMessage!!, viewModel)
    }

}

@Composable
fun Calculator(expression: String, viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = expression,
            fontSize = LocalDimen.current.resultTextSize,
            fontWeight = FontWeight.Black,
            color = White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(top = LocalDimen.current.spaceBetween))

        LazyVerticalGrid(columns = GridCells.Fixed(count = LocalProperty.current.columnCount)) {
            items(buttonsText) { element ->
                if (element.isDigitsOnly()) {
                    CalculatorNumber(number = element, onClick = { viewModel.addNumber(element) })
                } else {
                    CalculatorText(action = element, onClick = { viewModel.onClick(element) })
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(errorMessageRes: Int, viewModel: MainViewModel) {
    val error = stringResource(id = errorMessageRes)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = error,
            fontSize = LocalDimen.current.resultTextSize,
            fontWeight = FontWeight.Black,
            color = White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(top = LocalDimen.current.spaceBetween))

        LazyVerticalGrid(columns = GridCells.Fixed(count = LocalProperty.current.columnCount)) {
            items(buttonsText) { element ->
                if (element.isDigitsOnly()) {
                    CalculatorNumber(number = element, onClick = { viewModel.addNumber(element) })
                } else {
                    CalculatorText(action = element, onClick = { viewModel.onClick(element) })
                }
            }
        }
    }
}