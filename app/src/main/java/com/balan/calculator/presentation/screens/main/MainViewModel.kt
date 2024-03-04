package com.balan.calculator.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.balan.calculator.data.CalculatorRepositoryImpl.Companion.CLEAR
import com.balan.calculator.data.MyCustomException
import com.balan.calculator.domain.model.ButtonText
import com.balan.calculator.domain.repository.CalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val calculatorRepository: CalculatorRepository
) : ViewModel() {
    private val _state: MutableStateFlow<MainState> =
        MutableStateFlow(MainState())
    val state = _state.asStateFlow()



    fun addNumber(number: String) {
        calculatorRepository.addNumber(number)
        val expression = calculatorRepository.getExpression()
        viewModelScope.launch {
            _state.update {
                it.copy(expression = expression, errorMessage = null)
            }
        }
    }

    private fun clear() {
        calculatorRepository.clear()
        viewModelScope.launch {
            _state.update {
                it.copy(expression = CLEAR, errorMessage = null)
            }
        }
    }

    private fun del() {
        val expression = calculatorRepository.delete()
        viewModelScope.launch {
            _state.update {
                it.copy(expression = expression, errorMessage = null)
            }
        }
    }

    private fun percent() {
        calculatorRepository.addPercent()
        val expression = calculatorRepository.getExpression()
        viewModelScope.launch {
            _state.update {
                it.copy(expression = expression, errorMessage = null)
            }
        }
    }

    private fun plus() {
        calculatorRepository.addPlus()
        val expression = calculatorRepository.getExpression()
        viewModelScope.launch {
            _state.update {
                it.copy(expression = expression, errorMessage = null)
            }
        }
    }

    private fun minus() {
        calculatorRepository.addMinus()
        val expression = calculatorRepository.getExpression()
        viewModelScope.launch {
            _state.update {
                it.copy(expression = expression, errorMessage = null)
            }
        }
    }

    private fun division() {
        calculatorRepository.addDivision()
        val expression = calculatorRepository.getExpression()
        viewModelScope.launch {
            _state.update {
                it.copy(expression = expression, errorMessage = null)
            }
        }
    }

    private fun dot() {
        calculatorRepository.addDot()
        val expression = calculatorRepository.getExpression()
        viewModelScope.launch {
            _state.update {
                it.copy(expression = expression, errorMessage = null)
            }
        }
    }

    private fun multiply() {
        calculatorRepository.addMultiply()
        val expression = calculatorRepository.getExpression()
        viewModelScope.launch {
            _state.update {
                it.copy(expression = expression, errorMessage = null)
            }
        }
    }

    private fun result() {
        viewModelScope.launch {
            try {
                val expression = calculatorRepository.getResult()
                val formattedExpression = String.format("%.2f", expression)
                _state.update {
                    it.copy(expression = formattedExpression)
                }
            }catch (e : Exception){
                if(e is MyCustomException){
                    _state.update {
                        it.copy(errorMessage = e.messageResId)
                    }
                }
            }
        }
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
