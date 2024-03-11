package com.balan.calculator.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.balan.calculator.domain.model.CalculatorButtons
import com.balan.calculator.domain.repository.CalculatorRepository
import com.balan.calculator.presentation.exeption.CalculatorArithmeticalException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val calculatorRepository: CalculatorRepository
) : ViewModel() {
    private val _state: MutableStateFlow<CalculatorState> =
        MutableStateFlow(CalculatorState())
    val state = _state.asStateFlow()

    init {
       viewModelScope.launch {
           calculatorRepository.getExpression().distinctUntilChangedBy { it }
                .collect { setExpression(it) }
        }
    }

    private fun setExpression(expression: String) {
        _state.update {
            it.copy(expression =  expression, errorMessage = null)
        }
    }

    fun onNumberClick(number: String) {
        calculatorRepository.addNumber(number)
    }


    private fun calculate() {
        viewModelScope.launch {
            try {
                calculatorRepository.calculate()
            } catch (e: CalculatorArithmeticalException) {
                _state.update {
                    it.copy(errorMessage = e.messageResId)
                }
            }
        }
    }

    fun onButtonClick(text: String) {
        when (text) {
            CalculatorButtons.RESULT.text -> calculate()
            else -> {
                calculatorRepository.calculatorAction(text)
            }
        }
    }
}
