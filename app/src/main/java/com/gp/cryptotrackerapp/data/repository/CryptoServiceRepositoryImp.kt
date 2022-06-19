package com.gp.cryptotrackerapp.data.repository

import com.gp.cryptotrackerapp.base.BaseRepository
import com.gp.cryptotrackerapp.data.local.db.AppDatabase
import com.gp.cryptotrackerapp.data.local.entities.CoinInfoModelEntity
import com.gp.cryptotrackerapp.data.local.entities.CoinMaxMinAlertEntity
import com.gp.cryptotrackerapp.data.model.PingModel
import com.gp.cryptotrackerapp.data.model.common.ResultWrapper
import com.gp.cryptotrackerapp.data.remote.datasource.CryptoServiceDataSource
import com.gp.cryptotrackerapp.data.remote.model.CoinDataHistoryRemoteModel
import com.gp.cryptotrackerapp.data.remote.model.CoinInfoRemoteModel
import java.util.*

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

    override suspend fun getCoinHistory(
        id: String,
        currency: String
    ): ResultWrapper<CoinDataHistoryRemoteModel> {
        return cryptoServiceDataSource.getCoinHistory(id, currency)
    }

    override suspend fun getCoinList(): ResultWrapper<List<CoinInfoModelEntity>> {
        return safeDBCall {
            localDataSource.cryptoDao().getCoinsInfo()
        }
    }

    override suspend fun setAlertValuesForCoin(
        list: List<CoinMaxMinAlertEntity>
    ): ResultWrapper<Boolean> {
        safeDBCall {
            localDataSource.cryptoDao().insertMaxMinVal(
                list
            )
        }
        return ResultWrapper.Success(true)
    }

    override suspend fun getCoinAlertHistory(id: String): ResultWrapper<List<CoinMaxMinAlertEntity>> {
        return safeDBCall {
            localDataSource.cryptoDao().getCoinAlertHistory(id)
        }
    }

    override suspend fun getCoinAlertHistoryForAll(): ResultWrapper<List<CoinMaxMinAlertEntity>> {
        return safeDBCall {
            localDataSource.cryptoDao().getCoinAlertHistoryForAll()
        }
    }

    override suspend fun updateAlertState(id: Int, state: Boolean): ResultWrapper<Boolean> {
        safeDBCall {
            localDataSource.cryptoDao().updateAlertState(
                id, state
            )
        }
        return ResultWrapper.Success(true)
    }
}