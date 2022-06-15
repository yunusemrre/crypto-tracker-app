package com.gp.cryptotrackerapp.data.repository

import com.gp.cryptotrackerapp.data.local.entities.CoinInfoModelEntity
import com.gp.cryptotrackerapp.data.model.PingModel
import com.gp.cryptotrackerapp.data.model.common.ResultWrapper
import com.gp.cryptotrackerapp.data.remote.model.CoinInfoRemoteModel

interface CryptoServiceRepository {

    suspend fun ping(): ResultWrapper<PingModel>

    suspend fun getCoinList(): ResultWrapper<List<CoinInfoModelEntity>>

    suspend fun getCoinCurrentData(id: String): ResultWrapper<CoinInfoRemoteModel>

    suspend fun setAlertValuesForCoin(id: String, max: Float, min: Float): ResultWrapper<Boolean>
}