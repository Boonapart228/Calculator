package com.balan.calculator.domain.repository

import kotlinx.coroutines.flow.Flow

interface CalculatorRepository {
    fun addNumber(number: String)

    fun getExpression(): Flow<String>

    fun calculate()


    fun calculatorAction(symbol : String)

}