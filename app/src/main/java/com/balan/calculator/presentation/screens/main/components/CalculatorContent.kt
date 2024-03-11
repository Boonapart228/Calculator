package com.balan.calculator.presentation.screens.main.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.core.text.isDigitsOnly
import com.balan.calculator.presentation.screens.calculator_symbols.calculatorSymbols
import com.balan.calculator.ui.theme.Background
import com.balan.calculator.ui.theme.LocalDimen
import com.balan.calculator.ui.theme.LocalProperty

@Composable
fun CalculatorContent(
    expression: String,
    errorMessage: Int?,
    onNumberClick: (String) -> Unit,
    onButtonClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        if (errorMessage == null) {
            Text(
                text = expression,
                fontSize = LocalDimen.current.resultTextSize,
                fontWeight = FontWeight.Black,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(
                text = stringResource(id = errorMessage),
                fontSize = LocalDimen.current.resultTextSize,
                fontWeight = FontWeight.Black,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.padding(top = LocalDimen.current.spaceBetween))

        LazyVerticalGrid(columns = GridCells.Fixed(count = LocalProperty.current.columnCount)) {
            items(calculatorSymbols) { element ->
                if (element.isDigitsOnly()) {
                    CalculatorNumber(number = element, onClick = { onNumberClick(element) })
                } else {
                    CalculatorSymbol(action = element, onClick = { onButtonClick(element) })
                }
            }
        }
    }
}
