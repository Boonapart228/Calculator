package com.balan.calculator.di

import com.balan.calculator.data.CalculatorRepositoryImpl
import com.balan.calculator.domain.repository.CalculatorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CalculatorModule {

    @Provides
    @Singleton
    fun provideCalculatorRepository(): CalculatorRepository {
        return CalculatorRepositoryImpl()
    }
}