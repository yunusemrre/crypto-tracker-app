package com.gp.cryptotrackerapp.data.repository

import com.gp.cryptotrackerapp.base.BaseRepository
import com.gp.cryptotrackerapp.data.local.db.AppDatabase
import com.gp.cryptotrackerapp.data.local.entities.CoinListModel
import com.gp.cryptotrackerapp.data.model.PingModel
import com.gp.cryptotrackerapp.data.model.common.ResultWrapper
import com.gp.cryptotrackerapp.data.remote.datasource.CryptoServiceDataSource

class CryptoServiceRepositoryImp(
    private var cryptoServiceDataSource: CryptoServiceDataSource,
    private var localDataSource: AppDatabase
) :
    BaseRepository(),CryptoServiceRepository {

    override suspend fun ping(): ResultWrapper<PingModel> {
        return cryptoServiceDataSource.ping()
    }

    override suspend fun getCoinList(): ResultWrapper<List<CoinListModel>> {
        return safeDBCall {
            localDataSource.cryptoDao().getCryptoInfo()
        }
    }
}