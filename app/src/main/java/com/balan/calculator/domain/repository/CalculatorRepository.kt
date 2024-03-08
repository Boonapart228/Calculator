package com.balan.calculator.domain.repository

import kotlinx.coroutines.flow.Flow

interface CalculatorRepository {
    fun addNumber(number: String)

    fun getExpression(): Flow<String>

    fun clear()

    fun delete()

    fun addPercent()

    fun addPlus()

    fun addMinus()

    fun addDivision()

    fun addDot()

    fun addMultiply()

    fun getResult(): Double

    fun addRightParenthesis()
    fun addLeftParenthesis()
    fun addExponentiation()
    fun addMark()

}