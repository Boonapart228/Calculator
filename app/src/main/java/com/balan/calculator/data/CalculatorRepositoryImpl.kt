package com.balan.calculator.data

import com.balan.calculator.R
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
        const val COUNT_ELEMENT = 1
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

    override fun clear() {
        expression.value = ""
    }

    override fun delete() {
        expression.value = expression.value.dropLast(COUNT_ELEMENT)
    }

    override fun addPercent() {
        val symbol = isLastDigitOrOperator()
        if (symbol) expression.value += PERCENT
    }

    override fun addPlus() {
        val symbol = isLastDigitOrOperator()
        if (symbol) expression.value += PLUS
    }

    override fun addMinus() {
        val symbol = isLastDigitOrOperator()
        if (symbol) expression.value += MINUS
    }

    override fun addDivision() {
        val symbol = isLastDigitOrOperator()
        if (symbol) expression.value += DIVISION
    }

    override fun addDot() {
        val symbol = isLastDigitOrOperator()
        if (symbol) expression.value += DOT
    }

    override fun addMultiply() {
        val symbol = isLastDigitOrOperator()
        if (symbol) expression.value += MULTIPLY
    }

    override fun getResult(): Double {
        val result = Expression(expression.value).calculate()
        if (result.isNaN()) {
            throw CalculatorArithmeticalException(R.string.arithmetical_error)
        }
        expression.value = String.format(ARITHMETICAL_FORMAT, result)
        return result
    }

    override fun addRightParenthesis() {
        expression.value += RIGHT_PARENTHESIS
    }

    override fun addLeftParenthesis() {
        expression.value += LEFT_PARENTHESIS
    }

    override fun addExponentiation() {
        val symbol = isLastDigitOrOperator()
        if (symbol) expression.value += EXPONENTIATION
    }

    override fun addMark() {
        if (expression.value.isNotEmpty() && expression.value.last().isDigit()) {
            val withoutLast = expression.value.dropLast(1)
            expression.value = if (withoutLast.isNotEmpty() && withoutLast.last() == '-') {
                withoutLast + expression.value.last()
            } else {
                withoutLast + "-" + expression.value.last()
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