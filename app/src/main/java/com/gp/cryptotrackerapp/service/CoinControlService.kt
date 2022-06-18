package com.gp.cryptotrackerapp.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.gp.cryptotrackerapp.BuildConfig
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.data.model.common.IException
import com.gp.cryptotrackerapp.data.model.common.ResultWrapper
import com.gp.cryptotrackerapp.data.repository.CryptoServiceRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


@OptIn(DelicateCoroutinesApi::class)
@AndroidEntryPoint
class CoinControlService : Service() {

    @Inject
    lateinit var repository: CryptoServiceRepository

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        GlobalScope.launch {
            while (true) {
                checkCoinsAlertValue()
                delay(BuildConfig.SYNC_TIME)
            }
        }
        return START_STICKY
    }

    private fun checkCoinsAlertValue() {
        makeRequest(
            requestFunc = {
                repository.getCoinAlertHistoryForAll() //from db
            },
            onSuccess = { maxMinList -> //get alert values from db
                val coinNames =
                    mutableListOf<String>() // collect coin names of values // bitcoin

                maxMinList.forEach { eachMaxMinItem ->
                    if (!coinNames.contains(eachMaxMinItem.id))
                        coinNames.add(eachMaxMinItem.id)
                }

                coinNames.forEach { eachCoinToCheck -> // for each coin names to check make request to current data // bitcoin
                    makeRequest(
                        requestFunc = {
                            repository.getCoinCurrentData(eachCoinToCheck) // check for bitcoin current value
                        },
                        onSuccess = { coinCurrentValue -> // compare current value with alert value

                            maxMinList.filter { it.id == eachCoinToCheck }
                                .forEach { maxMinCheck ->
                                    val current =
                                        coinCurrentValue.marketData?.currentPrice?.usd ?: 0.0
                                    val max = maxMinCheck.maxVal?.toDouble() ?: 0.0
                                    val min = maxMinCheck.minVal?.toDouble() ?: 0.0

                                    if (current > max) {
                                        val text =
                                            "${coinCurrentValue.name} is higher than $max"
                                        showNotification(text)
                                    }
                                    if (current < min) {
                                        val text =
                                            "${coinCurrentValue.name} is lower than $min"
                                        showNotification(text)
                                    }
                                }
                        }
                    )
                }
            },
            onFail = {

            }
        )
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun <T> makeRequest(
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

    private fun showNotification(text: String) {
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, "CryptoTracker")
                .setSmallIcon(R.drawable.alert_icon_64)
                .setContentTitle("Coin value alert")
                .setContentText(text)
                .setAutoCancel(true)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1234
        createChannel(notificationManager)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun createChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT < 26) {
            return
        }
        val channel =
            NotificationChannel(
                "CryptoTracker",
                "CryptoTracker",
                NotificationManager.IMPORTANCE_DEFAULT
            )
        channel.description = "Coin value alert"
        notificationManager.createNotificationChannel(channel)
    }

}