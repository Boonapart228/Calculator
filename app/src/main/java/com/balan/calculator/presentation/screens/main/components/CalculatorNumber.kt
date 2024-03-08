package com.balan.calculator.presentation.screens.main.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.balan.calculator.ui.theme.LocalDimen
import com.balan.calculator.ui.theme.Orange

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalculatorNumber(number: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalDimen.current.buttonPadding)
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onClick
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange,
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.extraLarge,
        ) {
            Text(
                text = number,
                fontSize = LocalDimen.current.buttonTextSize,
                fontWeight = FontWeight.Black
            )
        }
    }
}