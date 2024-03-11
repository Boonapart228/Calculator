package com.balan.calculator.data

import com.balan.calculator.R
import com.balan.calculator.domain.model.CalculatorButtons
import com.balan.calculator.domain.repository.CalculatorRepository
import com.balan.calculator.presentation.exeption.CalculatorArithmeticalException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.mariuszgromada.math.mxparser.Expression


class CalculatorRepositoryImpl : CalculatorRepository {
    companion object {
        const val PLUS = '+'
        const val MINUS = '-'
        const val PERCENT = '#'
        const val DIVISION = '/'
        const val DOT = '.'
        const val MULTIPLY = '*'
        const val ARITHMETICAL_FORMAT = "%.2f"
        const val LEFT_PARENTHESIS = "("
        const val RIGHT_PARENTHESIS = ")"
        const val EXPONENTIATION = "^"
    }

    private var expression = MutableStateFlow("")
    override fun addNumber(number: String) {
        expression.value += number
    }

    override fun getExpression(): Flow<String> = expression

    override fun calculate() {
        val result = Expression(expression.value).calculate()
        if (result.isNaN()) {
            throw CalculatorArithmeticalException(R.string.arithmetical_error)
        }
        expression.value = String.format(ARITHMETICAL_FORMAT, result)
    }

    private fun addMark() {
        if (expression.value.isNotEmpty() && expression.value.last().isDigit()) {
            val withoutLast = expression.value.dropLast(1)
            expression.value =
                if (withoutLast.isNotEmpty() && withoutLast.last() == MINUS) {
                    withoutLast + expression.value.last()
                } else {
                    withoutLast + MINUS + expression.value.last()
                }
        }
    }

    override fun calculatorAction(symbol: String) {
        val isSymbol = isLastDigitOrOperator()
        when (symbol) {
            CalculatorButtons.LEFT_PARENTHESIS.text -> {
                expression.value += LEFT_PARENTHESIS
            }

            CalculatorButtons.RIGHT_PARENTHESIS.text -> {
                expression.value += RIGHT_PARENTHESIS
            }

            CalculatorButtons.EXPONENTIATION.text -> {
                if (isSymbol) expression.value += EXPONENTIATION
            }

            CalculatorButtons.MARK.text -> {
                addMark()
            }

            CalculatorButtons.C.text -> {
                expression.value = ""
            }

            CalculatorButtons.Del.text -> {
                expression.value = expression.value.dropLast(1)
            }

            CalculatorButtons.PERCENT.text -> {
                if (isSymbol) expression.value += PERCENT
            }

            CalculatorButtons.PLUS.text -> {
                if (isSymbol) expression.value += PLUS
            }

            CalculatorButtons.MINUS.text -> {
                if (isSymbol) expression.value += MINUS
            }

            CalculatorButtons.DIVISION.text -> {
                if (isSymbol) expression.value += DIVISION
            }

            CalculatorButtons.DOT.text -> {
                if (isSymbol) expression.value += DOT
            }

            CalculatorButtons.MULTIPLY.text -> {
                if (isSymbol) expression.value += MULTIPLY
            }
        }
    }

    private fun isLastDigitOrOperator(): Boolean {
        return if (expression.value.isNotEmpty()) {
            expression.value.last().isDigit()
        } else {
            false
        }
    }
}