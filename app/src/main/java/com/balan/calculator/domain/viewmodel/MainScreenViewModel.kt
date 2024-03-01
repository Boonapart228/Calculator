package com.balan.calculator.domain.viewmodel

import androidx.lifecycle.ViewModel
import com.balan.calculator.domain.model.NumericSymbolButton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenViewModel : ViewModel() {
   private val _state : MutableStateFlow<NumericSymbolButton> = MutableStateFlow(NumericSymbolButton())
    val state = _state.asStateFlow()
}