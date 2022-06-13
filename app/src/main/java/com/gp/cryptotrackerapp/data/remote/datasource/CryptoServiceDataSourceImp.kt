package com.gp.cryptotrackerapp.data.remote.datasource

import com.gp.cryptotrackerapp.base.BaseRemoteDataSource
import com.gp.cryptotrackerapp.data.model.PingModel
import com.gp.cryptotrackerapp.data.model.common.ResultWrapper
import com.gp.cryptotrackerapp.data.remote.api.CryptoService
import javax.inject.Inject

class CryptoServiceDataSourceImp @Inject constructor(
    private val cryptoService: CryptoService
): BaseRemoteDataSource(),CryptoServiceDataSource{

    override suspend fun ping(): ResultWrapper<PingModel> {
        return safeApiCallApi {
            cryptoService.pingService()
        }
    }
}