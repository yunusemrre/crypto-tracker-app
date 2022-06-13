package com.gp.cryptotrackerapp.data.model.common

import java.lang.Exception

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Fail(val value: IException) : ResultWrapper<Nothing>()
}