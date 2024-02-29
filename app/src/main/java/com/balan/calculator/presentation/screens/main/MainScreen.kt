package com.balan.calculator.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly
import com.balan.calculator.ui.theme.BackGround
import com.balan.calculator.ui.theme.LocalDimen
import com.balan.calculator.ui.theme.LocalProperty
import com.balan.calculator.ui.theme.Orange
import com.balan.calculator.ui.theme.Turquoise

@Composable
fun CalculatorNumber(button: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth()) {
        Button(
            onClick = onClick,
            Modifier
                .fillMaxWidth()
                .padding(LocalDimen.current.buttonPadding),
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange,
                contentColor = White
            ),
            shape = MaterialTheme.shapes.extraLarge,
        ) {
            Text(
                text = button,
                fontSize = LocalDimen.current.buttonTextSize,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
fun CalculatorText(button: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth()) {
        Button(
            onClick = onClick,
            Modifier
                .fillMaxWidth()
                .padding(LocalDimen.current.buttonPadding),
            colors = ButtonDefaults.buttonColors(
                containerColor = Turquoise,
                contentColor = White
            ),
            shape = MaterialTheme.shapes.extraLarge,
        ) {
            Text(
                text = button,
                fontSize = LocalDimen.current.buttonTextSize,
                fontWeight = FontWeight.Black
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackGround),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "",
            fontSize = LocalDimen.current.resultTextSize,
            fontWeight = FontWeight.Black,
            color = White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "",
            fontSize = LocalDimen.current.buttonTextSize,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.End,
            color = White,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(top = LocalDimen.current.spaceBetween))

        LazyVerticalGrid(GridCells.Fixed(LocalProperty.current.count)) {
            items(list) { element ->
                if (element.isDigitsOnly()) {
                    CalculatorNumber(button = element, onClick = {})
                } else {
                    CalculatorText(button = element, onClick = {})
                }
            }
        }
    }
}

val list = listOf(
    "C",
    "Del",
    "%",
    "C8",
    "7",
    "8",
    "9",
    "+",
    "4",
    "5",
    "6",
    "-",
    "1",
    "2",
    "3",
    "/",
    ".",
    "0",
    "=",
    "*"
)