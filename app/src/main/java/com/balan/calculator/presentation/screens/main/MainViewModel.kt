package com.balan.calculator.presentation.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.balan.calculator.domain.model.ButtonText
import com.balan.calculator.domain.model.NumericSymbolButtonState
import com.balan.calculator.domain.repository.CalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val calculatorRepositoryImpl: CalculatorRepository
) : ViewModel() {
    private val _state: MutableStateFlow<NumericSymbolButtonState> =
        MutableStateFlow(NumericSymbolButtonState())
    val state = _state.asStateFlow()


    fun addNumber(number: String) {
        calculatorRepositoryImpl.addNumber(number)
        val expression = calculatorRepositoryImpl.getExpression()
        _state.update {
            it.copy(expression = expression)
        }
    }

    fun clear() {
        calculatorRepositoryImpl.clear()
        _state.update {
            it.copy(expression = "")
        }
    }

    fun del() {
        val expression = calculatorRepositoryImpl.del()
        _state.update {
            it.copy(expression = expression)
        }
        Log.d("DEL", expression)
    }

    fun percent() {
        calculatorRepositoryImpl.addPercent()
        val expression = calculatorRepositoryImpl.getExpression()
        _state.update {
            it.copy(expression = expression)
        }
        Log.d("DEL", expression)
    }

    fun plus() {
        calculatorRepositoryImpl.addPlus()
        val expression = calculatorRepositoryImpl.getExpression()
        _state.update {
            it.copy(expression = expression)
        }
    }

    fun minus() {
        calculatorRepositoryImpl.addMinus()
        val expression = calculatorRepositoryImpl.getExpression()
        _state.update {
            it.copy(expression = expression)
        }
    }

    fun division() {
        calculatorRepositoryImpl.addDivision()
        val expression = calculatorRepositoryImpl.getExpression()
        _state.update {
            it.copy(expression = expression)
        }
    }

    fun dot() {
        calculatorRepositoryImpl.addDot()
        val expression = calculatorRepositoryImpl.getExpression()
        _state.update {
            it.copy(expression = expression)
        }
    }

    fun multiply() {
        calculatorRepositoryImpl.addMultiply()
        val expression = calculatorRepositoryImpl.getExpression()
        _state.update {
            it.copy(expression = expression)
        }
    }

    fun result(){
        val expression = calculatorRepositoryImpl.getResult()
        _state.update {
            it.copy(expression = expression)
        }
        Log.d("RESULT", expression)
    }

    fun onClick(buttonText: String) {
        when (buttonText) {
            ButtonText.C.action -> clear()
            ButtonText.Del.action -> del()
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
