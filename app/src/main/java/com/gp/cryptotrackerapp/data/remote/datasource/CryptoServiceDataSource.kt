package com.gp.cryptotrackerapp.data.remote.datasource

import com.gp.cryptotrackerapp.data.model.PingModel
import com.gp.cryptotrackerapp.data.model.common.ResultWrapper
import com.gp.cryptotrackerapp.data.remote.model.CoinDataHistoryRemoteModel
import com.gp.cryptotrackerapp.data.remote.model.CoinInfoRemoteModel

interface CryptoServiceDataSource {

    suspend fun ping(): ResultWrapper<PingModel>

    suspend fun getCoinsData(id: String): ResultWrapper<CoinInfoRemoteModel>

    suspend fun getCoinHistory(id: String): ResultWrapper<CoinDataHistoryRemoteModel>

}