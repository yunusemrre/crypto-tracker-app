package com.gp.cryptotrackerapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.gp.cryptotrackerapp.data.local.entities.CoinMaxMinAlertEntity
import com.gp.cryptotrackerapp.data.model.common.IException
import com.gp.cryptotrackerapp.data.model.common.ResultWrapper
import com.gp.cryptotrackerapp.data.repository.CryptoServiceRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CoinControlService : Service() {

    @Inject
    lateinit var repository: CryptoServiceRepository

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        makeRequest(
            requestFunc = {
                repository.getCoinAlertHistory("bitcoin")
            },
            onSuccess = {

            },
            onFail = {

            }
        )

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    fun <T> makeRequest(
        requestFunc: suspend () -> ResultWrapper<T>,
        onSuccess: ((value: T) -> Unit)? = null,
        onFail: ((value: IException) -> Unit)? = null
    ) {
        GlobalScope.launch(Dispatchers.IO) {
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