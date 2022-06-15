package com.gp.cryptotrackerapp.data.remote.api

import com.gp.cryptotrackerapp.data.model.PingModel
import com.gp.cryptotrackerapp.data.remote.model.CoinInfoRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoService {

    @GET("ping")
    suspend fun pingService(): PingModel

    @GET("coins/{id}")
    suspend fun getCoinsData(@Path("id") id: String): CoinInfoRemoteModel
}