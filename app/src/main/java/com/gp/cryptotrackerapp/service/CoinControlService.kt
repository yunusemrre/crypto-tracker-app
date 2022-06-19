package com.gp.cryptotrackerapp.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.data.local.entities.CoinMaxMinAlertEntity
import com.gp.cryptotrackerapp.data.model.common.IException
import com.gp.cryptotrackerapp.data.model.common.ResultWrapper
import com.gp.cryptotrackerapp.data.repository.CryptoServiceRepository
import com.gp.cryptotrackerapp.util.AppConstant
import com.gp.cryptotrackerapp.util.enum.CurrencyEnum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.random.Random

@OptIn(DelicateCoroutinesApi::class)
@AndroidEntryPoint
class CoinControlService : Service() {

    @Inject
    lateinit var repository: CryptoServiceRepository

    private var channelId = "CryptoTracker"

    override fun onCreate() {
        super.onCreate()

        /**
         * Create notification & channel for SDKs higher than 26
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            showServiceNotification()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                checkCoinsAlertValue()
                delay(AppConstant.SYNC_TIME_ALERT_SERVICE)
            }
        }
        return START_STICKY
    }

    /**
     * Get coins alert data from db and compare with current data
     */
    private fun checkCoinsAlertValue() {
        makeRequest(
            requestFunc = {
                repository.getCoinAlertHistoryForAll() //from db
            },
            onSuccess = { maxMinList -> //Get all active alert data from db
                compareDataShowNotification(maxMinList)
            }
        )
    }

    /**
     * @see
     *  Get all active alert data from data -> alertDataList
     *  Collect coin names and currency info from alert data -> coinNamesCurrency
     *  Get current data from api for each coin names -> alertItem
     *  Compare current data with alert value by currency
     *  Show notification if complies condition
     */
    private fun compareDataShowNotification(alertDataList: List<CoinMaxMinAlertEntity>) {
        val coinNamesCurrency =
            mutableMapOf<String, String>() // collect coin names and currency of values

        alertDataList.forEach { eachMaxMinItem ->
            if (!coinNamesCurrency.contains(eachMaxMinItem.id)) {
                coinNamesCurrency[eachMaxMinItem.id] = eachMaxMinItem.currency.toString()
            }
        }

        coinNamesCurrency.forEach { eachCoinToCheck -> // for each coin names to check make request to current data // bitcoin
            makeRequest(
                requestFunc = {
                    repository.getCoinCurrentData(eachCoinToCheck.key) // check for bitcoin current value
                },
                onSuccess = { coinCurrentValue -> // compare current value with alert value

                    alertDataList.filter { it.id == eachCoinToCheck.key }
                        .forEach { alertItem ->
                            var current = 0.0
                            var currencyName = ""
                            when (eachCoinToCheck.value) {
                                CurrencyEnum.USD.name -> {
                                    current = coinCurrentValue.marketData?.currentPrice?.usd
                                        ?: 0.0
                                    currencyName = CurrencyEnum.USD.name
                                }
                                CurrencyEnum.EUR.name -> {
                                    current =
                                        coinCurrentValue.marketData?.currentPrice?.euro
                                            ?: 0.0
                                    currencyName = CurrencyEnum.EUR.name
                                }
                                CurrencyEnum.TRY.name -> {
                                    current =
                                        coinCurrentValue.marketData?.currentPrice?.turkishLira
                                            ?: 0.0
                                    currencyName = CurrencyEnum.TRY.name
                                }
                            }

                            val max = alertItem.maxVal?.toDouble() ?: 0.0
                            val min = alertItem.minVal?.toDouble() ?: 0.0

                            if (max != 0.0 && current > max) {
                                val text =
                                    "${coinCurrentValue.name} is higher than $max $currencyName"
                                showAlertNotification(text, true)
                                disableAlert(alertItem.entityId)
                            }
                            if (min != 0.0 && current < min) {
                                val text =
                                    "${coinCurrentValue.name} is lower than $min $currencyName"
                                showAlertNotification(text, false)
                                disableAlert(alertItem.entityId)
                            }
                        }
                }
            )
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    /**
     * Disable related coin notification after it shown
     */
    private fun disableAlert(alertEntityId: Int) {
        makeRequest(
            requestFunc = {
                repository.updateAlertState(alertEntityId, false)
            }
        )
    }

    /**
     * Make async request
     */
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

    /**
     * Show coin alert notification
     * @param text: Text to shown in notification
     * @param inc: Set the icon according to increase or decrease data
     */
    private fun showAlertNotification(text: String, inc: Boolean) {
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(if (inc) R.drawable.icon_inc else R.drawable.icon_dec)
                .setContentTitle("Coin Value Alert")
                .setContentText(text)
                .setAutoCancel(true)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = Random.nextInt(100)
        createChannel(notificationManager)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    /**
     * Show constant service notification for Android SDK higher than 26
     */
    private fun showServiceNotification() {
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.drawable.icon_notification)
            .setPriority(PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(101, notification);
    }

    /**
     * Create channel for notifications
     */
    private fun createChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT < 26) {
            return
        }
        val channel =
            NotificationChannel(
                channelId,
                channelId,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        channel.description = "CryptoTrackerApp"
        notificationManager.createNotificationChannel(channel)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

}