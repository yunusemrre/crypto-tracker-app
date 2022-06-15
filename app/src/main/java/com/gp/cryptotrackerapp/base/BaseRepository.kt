package com.gp.cryptotrackerapp.base

import com.gp.cryptotrackerapp.data.model.common.GeneralException
import com.gp.cryptotrackerapp.data.model.common.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {

    internal suspend fun <T> safeDBCall(databaseCall: suspend () -> T): ResultWrapper<T> {
        return withContext(Dispatchers.IO){
            try {
                val response = databaseCall.invoke()
                ResultWrapper.Success(response)
            } catch (t: Throwable){
                ResultWrapper.Fail(value = GeneralException())
            }
        }
    }

    internal suspend fun <T> safeDBCallVoid(databaseCall: suspend () -> T) {
        return withContext(Dispatchers.IO){
            try {
                val response = databaseCall.invoke()
                ResultWrapper.Success(response)
            } catch (t: Throwable){
                ResultWrapper.Fail(value = GeneralException())
            }
        }
    }

}