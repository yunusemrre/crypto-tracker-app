package com.gp.cryptotrackerapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gp.cryptotrackerapp.data.model.common.IException
import com.gp.cryptotrackerapp.data.model.common.ResultWrapper
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel(){

    /**
     * Base method for Backend Api, returns the API response or error message.
     */
    fun <T> makeNetworkRequest(
        requestFunc: suspend () -> ResultWrapper<T>,
        onSuccess: ((value: T) -> Unit)? = null,
        onFail: ((value: IException) -> Unit)? = null
    ) {
        viewModelScope.launch {
            when (val response = requestFunc.invoke()) {
                is ResultWrapper.Fail -> {
                    onFail?.invoke(response.value)
                }
                is ResultWrapper.Success -> {
                    onSuccess?.invoke(response.value)
                }
            }
        }
    }
}