package com.gp.cryptotrackerapp.base

import com.gp.cryptotrackerapp.data.model.common.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

abstract class BaseRemoteDataSource {

    internal suspend fun <T> safeApiCallApi(apiCall: suspend () -> T): ResultWrapper<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.invoke()
                ResultWrapper.Success(response)
            } catch (t: Throwable) {
                when (t) {
                    is UnknownHostException -> ResultWrapper.Fail(value = ServiceUnreachableException())
                    else -> ResultWrapper.Fail(value = GeneralException())
                }
            }
        }
    }
}