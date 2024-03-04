package com.balan.calculator.data

import com.balan.calculator.R
import com.balan.calculator.data.exeption.ArithmeticalException
import com.balan.calculator.domain.repository.CalculatorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Stack


class CalculatorRepositoryImpl : CalculatorRepository {
    companion object {
        const val PLUS = '+'
        const val MINUS = '-'
        const val PERCENT = '%'
        const val DIVISION = '/'
        const val DOT = '.'
        const val MULTIPLY = '*'
        const val ZERO = 0
        const val ONE = 1
        const val COUNT_ELEMENT = 1
        const val TWO = 2
        const val RESULT_FORMAT = "%.2f"
    }

    private var expression  = MutableStateFlow("")
    override fun addNumber(number: String) {
        expression.value += number
    }

    override fun getExpression():Flow<String> = expression



    override fun clear() {
        expression.value = ""
    }

    override fun delete() {
        expression.value = expression.value.dropLast(COUNT_ELEMENT)
    }

    override fun addPercent() {
        expression.value += PERCENT
    }

    override fun addPlus() {
        expression.value += PLUS
    }

    override fun addMinus() {
        expression.value += MINUS
    }

    override fun addDivision() {
        expression.value += DIVISION
    }

    override fun addDot() {
        expression.value += DOT
    }

    override fun addMultiply() {
        expression.value += MULTIPLY
    }

    override fun getResult(): Double {
        val outputQueue = convertToPostfix(expression.value)
        val result = evaluatePostfix(outputQueue)
        expression.value = String.format(RESULT_FORMAT, result)
        return result
    }

    private fun convertToPostfix(infixExpression: String): List<String> {
        val operatorsPrecedence = mapOf(PLUS to ONE, MINUS to ONE, MULTIPLY to TWO, DIVISION to TWO, PERCENT to TWO)
        val outputQueue = mutableListOf<String>()
        val operatorStack = Stack<Char>()
        val numberBuffer = StringBuilder()
        var isNegativeNumber = false

        infixExpression.forEach { char ->
            when {
                char.isDigit() || char == DOT -> numberBuffer.append(char)
                char == MINUS && numberBuffer.isEmpty() -> isNegativeNumber = true
                operatorsPrecedence.keys.contains(char) -> {
                    processNumberBuffer(numberBuffer, isNegativeNumber, outputQueue)
                    while (operatorStack.isNotEmpty() &&
                        operatorsPrecedence[operatorStack.peek()]!! >= operatorsPrecedence[char]!!) {
                        outputQueue.add(operatorStack.pop().toString())
                    }
                    operatorStack.push(char)
                }
                char == '(' -> operatorStack.push(char)
                char == ')' -> {
                    processNumberBuffer(numberBuffer, isNegativeNumber, outputQueue)
                    while (operatorStack.peek() != '(') {
                        outputQueue.add(operatorStack.pop().toString())
                    }
                    operatorStack.pop()
                }
            }
        }
        processNumberBuffer(numberBuffer, isNegativeNumber, outputQueue)
        while (operatorStack.isNotEmpty()) {
            outputQueue.add(operatorStack.pop().toString())
        }
        return outputQueue
    }

    private fun processNumberBuffer(numberBuffer: StringBuilder, isNegative: Boolean, outputQueue: MutableList<String>) {
        if (numberBuffer.isNotEmpty()) {
            if (isNegative) {
                numberBuffer.insert(ZERO, MINUS)
            }
            outputQueue.add(numberBuffer.toString())
            numberBuffer.clear()
        }
    }

    private fun evaluatePostfix(postfixExpression: List<String>): Double {
        val stack = Stack<Double>()

        postfixExpression.forEach { token ->
            if (token.isNumeric()) {
                stack.push(token.toDouble())
            } else {
                val b = stack.pop()
                val a = stack.pop()
                val result = when (token) {
                    PLUS.toString() -> a + b
                    MINUS.toString() -> a - b
                    MULTIPLY.toString() -> a * b
                    DIVISION.toString() -> {
                        if (b == ZERO.toDouble()) {
                            throw ArithmeticalException(R.string.division_by_zero_error)
                        }
                        a / b
                    }
                    PERCENT.toString() -> a % b
                    else -> throw ArithmeticalException(R.string.operation_error)
                }
                stack.push(result)
            }
        }
        return stack.pop()
    }

    private fun String.isNumeric(): Boolean {
        return try {
            this.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

}