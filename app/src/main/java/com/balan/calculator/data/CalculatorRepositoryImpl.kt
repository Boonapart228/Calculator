package com.balan.calculator.data

import com.balan.calculator.domain.repository.CalculatorRepository

class CalculatorRepositoryImpl : CalculatorRepository {
    private var expression = ""
    override fun addNumber(number: String) {
        expression += number
    }

    override fun getExpression(): String {
        return expression
    }

    override fun clear() {
        expression = ""
    }

    override fun del() : String {
        expression = expression.dropLast(1)
        return expression
    }

    override fun addPercent() {
        expression += "%"
    }

    override fun addPlus() {
        expression += "+"
    }

    override fun addMinus() {
        expression += "-"
    }

    override fun addDivision() {
        expression += "/"
    }

    override fun addDot() {
        expression += "."
    }
   override fun addMultiply(){
       expression += "*"
   }

    override fun getResult(): String {
        if (expression.isEmpty()) {
            return "0.0"
        }
        val operators = listOf('+', '-', '*', '/', '%')
        val currentNumber = StringBuilder()
        var currentOperator = '+'
        var result = 0.0
        if(expression.last() in operators){
            return "0.0"
        }
        for ((index, char) in expression.withIndex()) {
            if (char.isDigit() || (char == '.' && currentNumber.isNotEmpty()) ||
                (char == '-' && (index == 0 || operators.contains(expression[index - 1])))) {
                currentNumber.append(char)
            } else if (operators.contains(char)) {
                if (currentOperator == '/' && currentNumber.toString().toDouble() == 0.0) {
                    throw ArithmeticException("Ділення на нуль неможливе")
                }
                result = performOperation(result, currentNumber.toString().toDouble(), currentOperator)
                currentOperator = char
                currentNumber.clear()
            }
        }
        // Обробка останньої частини виразу
        result = performOperation(result, currentNumber.toString().toDouble(), currentOperator)
        return result.toString()
    }
    fun performOperation(left: Double, right: Double, operator: Char): Double {
        return when (operator) {
            '+' -> left + right
            '-' -> left - right
            '*' -> left * right
            '/' -> left / right
            '%' -> left % right
            else -> throw IllegalArgumentException("Невідомий оператор")
        }
    }
}