package com.balan.calculator.data

import androidx.annotation.StringRes
import com.balan.calculator.R
import com.balan.calculator.domain.repository.CalculatorRepository
import java.util.Stack

class MyCustomException(@StringRes val messageResId: Int) : Exception()

class CalculatorRepositoryImpl() : CalculatorRepository {
    companion object {
        const val PLUS = '+'
        const val MINUS = '-'
        const val CLEAR = ""
        const val PERCENT = '%'
        const val DIVISION = '/'
        const val DOT = '.'
        const val MULTIPLY = '*'
        const val ZERO_DOUBLE = 0.0
        const val ZERO = 0
        const val ONE = 1
        const val TWO = 2
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

    override fun delete(): String {
        expression = expression.dropLast(1)
        return expression
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
        val operators = mapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2, '%' to 2)
        val outputQueue = mutableListOf<String>()
        val operatorStack = Stack<Char>()

        var numberBuffer = StringBuilder()
        var isNegativeNumber = false

        for (char in expression) {
            when {
                char.isDigit() || char == '.' -> numberBuffer.append(char)
                char == '-' && numberBuffer.isEmpty() -> isNegativeNumber = true
                operators.keys.contains(char) -> {
                    if (numberBuffer.isNotEmpty()) {
                        if (isNegativeNumber) {
                            numberBuffer.insert(0, '-')
                            isNegativeNumber = false
                        }
                        outputQueue.add(numberBuffer.toString())
                        numberBuffer.clear()
                    }
                    while (operatorStack.isNotEmpty() &&
                        operators[operatorStack.peek()]!! >= operators[char]!!) {
                        outputQueue.add(operatorStack.pop().toString())
                    }
                    operatorStack.push(char)
                }
                char == '(' -> operatorStack.push(char)
                char == ')' -> {
                    if (numberBuffer.isNotEmpty()) {
                        outputQueue.add(numberBuffer.toString())
                        numberBuffer.clear()
                    }
                    while (operatorStack.peek() != '(') {
                        outputQueue.add(operatorStack.pop().toString())
                    }
                    operatorStack.pop() // remove '('
                }
            }
        }

        if (numberBuffer.isNotEmpty()) {
            if (isNegativeNumber) {
                numberBuffer.insert(0, '-')
            }
            outputQueue.add(numberBuffer.toString())
        }

        while (operatorStack.isNotEmpty()) {
            outputQueue.add(operatorStack.pop().toString())
        }

        val stack = Stack<Double>()

        for (token in outputQueue) {
            if (token.isNumeric()) {
                stack.push(token.toDouble())
            } else {
                val b = stack.pop()
                val a = stack.pop()
                val result = when (token) {
                    "+" -> a + b
                    "-" -> a - b
                    "*" -> a * b
                    "/" -> {
                        if (b == 0.0) {
                            throw MyCustomException(R.string.division_by_zero_error)
                        }
                        a / b
                    }
                    "%" -> a % b
                    else -> throw MyCustomException(R.string.operation_error)
                }
                stack.push(result)
            }
        }
        expression = String.format("%.2f", stack.peek())
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