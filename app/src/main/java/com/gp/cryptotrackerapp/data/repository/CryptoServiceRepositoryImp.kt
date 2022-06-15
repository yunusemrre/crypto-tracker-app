package com.gp.cryptotrackerapp.data.repository

import com.gp.cryptotrackerapp.base.BaseRepository
import com.gp.cryptotrackerapp.data.local.db.AppDatabase
import com.gp.cryptotrackerapp.data.local.entities.CoinInfoModelEntity
import com.gp.cryptotrackerapp.data.local.entities.CoinMaxMinAlertEntity
import com.gp.cryptotrackerapp.data.model.PingModel
import com.gp.cryptotrackerapp.data.model.common.ResultWrapper
import com.gp.cryptotrackerapp.data.remote.datasource.CryptoServiceDataSource
import com.gp.cryptotrackerapp.data.remote.model.CoinInfoRemoteModel

class CryptoServiceRepositoryImp(
    private var cryptoServiceDataSource: CryptoServiceDataSource,
    private var localDataSource: AppDatabase
) :
    BaseRepository(), CryptoServiceRepository {

    override suspend fun ping(): ResultWrapper<PingModel> {
        return cryptoServiceDataSource.ping()
    }

    override suspend fun getCoinCurrentData(id: String): ResultWrapper<CoinInfoRemoteModel> {
        return cryptoServiceDataSource.getCoinsData(id)
    }

    override suspend fun getCoinList(): ResultWrapper<List<CoinInfoModelEntity>> {
        return safeDBCall {
            localDataSource.cryptoDao().getCoinsInfo()
        }
    }
    override suspend fun setAlertValuesForCoin(
        id: String,
        max: Float,
        min: Float
    ) : ResultWrapper<Boolean> {
        safeDBCallVoid {
            localDataSource.cryptoDao().insertMaxMinVal(
                CoinMaxMinAlertEntity(
                    id = id,
                    maxVal = max,
                    minVal = min
                )
            )
        }
        return ResultWrapper.Success(true)
    }
}