package com.gp.cryptotrackerapp.util

/**
 * Constant values of app
 */
class AppConstant {
    companion object {

        /**
         * SYNC times for all jobs
         */

        var SYNC_TIME_ALERT_SERVICE = 20000L // CoinControlService sync frequency
        var SYNC_TIME_COIN_LIST = 20000L // Coin list current value sync frequency
        var SYNC_TIME_COIN_CURRENT_DATA = 15000L // Coin current data value sync frequency
        var SYNC_TIME_COIN_ALERT_DATA = 10000L // Coin alert history data value sync frequency
    }
}