package com.balan.calculator.domain.repository

interface CalculatorRepository {
    fun addNumber(number: String)

    fun getExpression(): String

    fun clear()

    fun delete()

    fun addPercent()
    fun addPlus()
    fun addMinus()
    fun addDivision()

    fun addDot()

    fun addMultiply()

    fun getResult(): Double
}