package com.balan.calculator.data

import com.balan.calculator.R
import com.balan.calculator.data.exeption.ArithmeticalException
import com.balan.calculator.domain.repository.CalculatorRepository
import java.util.Stack


class CalculatorRepositoryImpl() : CalculatorRepository {
    companion object {
        const val PLUS = '+'
        const val MINUS = '-'
        const val CLEAR = ""
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

    private var expression = CLEAR
    override fun addNumber(number: String) {
        expression += number
    }

    override fun getExpression(): String {
        return expression
    }

    override fun clear() {
        expression = CLEAR
    }

    override fun delete() {
        expression = expression.dropLast(COUNT_ELEMENT)
    }

    override fun addPercent() {
        expression += PERCENT
    }

    override fun addPlus() {
        expression += PLUS
    }

    override fun addMinus() {
        expression += MINUS
    }

    override fun addDivision() {
        expression += DIVISION
    }

    override fun addDot() {
        expression += DOT
    }

    override fun addMultiply() {
        expression += MULTIPLY
    }

    override fun getResult(): Double {
        val outputQueue = convertToPostfix(expression)
        val result = evaluatePostfix(outputQueue)
        expression = String.format(RESULT_FORMAT, result)
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