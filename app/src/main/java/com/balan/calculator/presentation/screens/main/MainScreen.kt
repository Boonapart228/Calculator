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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.balan.calculator.ui.theme.Black40
import com.balan.calculator.ui.theme.Orange

@Composable
fun NewCalculator(modifier: Modifier = Modifier, button: () -> Unit, number: String) {
    Box(modifier.fillMaxWidth()) {
        Button(
            onClick = { button() },
            modifier
                .fillMaxWidth()
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange,
                contentColor = White
            ),
            shape = MaterialTheme.shapes.extraLarge,
        ) {
            Text(
                number,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CalculatorApp(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Black40),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = White,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth()
        )
        Text(
            text = "",
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.End,
            color = White,
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.padding(top = 100.dp))

        LazyVerticalGrid(GridCells.Fixed(4)) {
            items(list) { element ->
                NewCalculator(button = {}, number = element)
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
