package com.gp.cryptotrackerapp.data.repository

import com.gp.cryptotrackerapp.data.local.entities.CoinListModel
import com.gp.cryptotrackerapp.data.model.PingModel
import com.gp.cryptotrackerapp.data.model.common.ResultWrapper

interface CryptoServiceRepository {

    suspend fun ping(): ResultWrapper<PingModel>

    suspend fun getCoinList(): ResultWrapper<List<CoinListModel>>
}