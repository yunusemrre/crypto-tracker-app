package com.gp.cryptotrackerapp.data.remote.api

import com.gp.cryptotrackerapp.data.model.PingModel
import com.gp.cryptotrackerapp.data.remote.model.CoinDataHistoryRemoteModel
import com.gp.cryptotrackerapp.data.remote.model.CoinInfoRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoService {

    @GET("ping")
    suspend fun pingService(): PingModel

    @GET("coins/{id}")
    suspend fun getCoinsData(
        @Path("id") id: String,
        @Query("localization") l: Boolean = false,
        @Query("tickers") t: Boolean = false,
        @Query("community_data") c: Boolean = false,
        @Query("developer_data") d: Boolean = false,
        @Query("sparkline") s: Boolean = true
    ): CoinInfoRemoteModel

    @GET("coins/{id}/market_chart")
    suspend fun getCoinHistory(
        @Path("id") id: String,
        @Query("vs_currency") currency: String,
        @Query("days") days: String
    ): CoinDataHistoryRemoteModel
}