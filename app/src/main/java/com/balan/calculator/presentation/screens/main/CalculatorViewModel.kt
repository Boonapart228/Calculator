package com.balan.calculator.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.balan.calculator.data.exeption.ArithmeticalException
import com.balan.calculator.domain.model.ButtonText
import com.balan.calculator.domain.repository.CalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        CoroutineScope(Dispatchers.IO).launch {
            calculatorRepository.getExpression().distinctUntilChangedBy { it }
                .collect { setExpression(it) }
        }
    }

    private fun setExpression(expression: String) {
        _state.update {
            it.copy(expression = expression, errorMessage = null)
        }
    }

    fun addNumber(number: String) {
        calculatorRepository.addNumber(number)
    }

    private fun clear() {
        calculatorRepository.clear()

    }

    private fun deleteLastElement() {
        calculatorRepository.delete()
    }

    private fun percent() {
        calculatorRepository.addPercent()
    }

    private fun plus() {
        calculatorRepository.addPlus()
    }

    private fun minus() {
        calculatorRepository.addMinus()
    }

    private fun division() {
        calculatorRepository.addDivision()
    }

    private fun dot() {
        calculatorRepository.addDot()
    }

    private fun multiply() {
        calculatorRepository.addMultiply()
    }

    private fun result() {
        viewModelScope.launch {
            try {
                val expression = calculatorRepository.getResult()
                val formattedExpression = String.format("%.2f", expression)
                setExpression(formattedExpression)
            } catch (e: ArithmeticalException) {
                _state.update {
                    it.copy(errorMessage = e.messageResId)
                }
            }
        }
    }

    fun onButtonClick(text: String) {
        when (text) {
            ButtonText.C.action -> clear()
            ButtonText.Del.action -> deleteLastElement()
            ButtonText.PERCENT.action -> percent()
//            ButtonText.SWITCH -> TODO()
            ButtonText.PLUS.action -> plus()
            ButtonText.MINUS.action -> minus()
            ButtonText.DIVISION.action -> division()
            ButtonText.DOT.action -> dot()
            ButtonText.RESULT.action -> result()
            ButtonText.MULTIPLY.action -> multiply()
        }
    }
}
