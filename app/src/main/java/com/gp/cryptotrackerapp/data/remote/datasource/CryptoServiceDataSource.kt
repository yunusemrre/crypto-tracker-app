package com.gp.cryptotrackerapp.data.remote.datasource

import com.gp.cryptotrackerapp.data.model.PingModel
import com.gp.cryptotrackerapp.data.model.common.ResultWrapper

interface CryptoServiceDataSource {

    suspend fun ping(): ResultWrapper<PingModel>

}