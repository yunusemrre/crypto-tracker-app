package com.gp.cryptotrackerapp.data.remote.model

import android.util.Log

data class CoinDataHistoryRemoteModel(
    var prices : ArrayList<ArrayList<Double>>? = null
)