package com.gp.cryptotrackerapp.data.remote.api

import com.gp.cryptotrackerapp.data.model.CoinListModel
import com.gp.cryptotrackerapp.data.model.PingModel
import retrofit2.http.GET

interface CryptoService {

    @GET("ping")
    suspend fun pingService(): PingModel

    @GET("coins/list")
    suspend fun getCoinList(): List<CoinListModel>
}