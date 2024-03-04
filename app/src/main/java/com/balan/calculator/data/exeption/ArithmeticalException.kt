package com.balan.calculator.data.exeption

import androidx.annotation.StringRes

class ArithmeticalException(@StringRes val messageResId: Int) : Exception()