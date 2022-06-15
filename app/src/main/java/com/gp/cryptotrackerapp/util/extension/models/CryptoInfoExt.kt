package com.gp.cryptotrackerapp.util.extension.models

import com.gp.cryptotrackerapp.data.local.entities.CoinInfoModelEntity
import com.gp.cryptotrackerapp.data.model.CoinInfo.CoinInfoModel
import com.gp.cryptotrackerapp.data.remote.model.CoinInfoRemoteModel

/**
 * [CoinInfoModelEntity] to [CoinInfoModel]
 */
fun CoinInfoModelEntity.toIUModel(){
    CoinInfoModel(
        id = this.id,
        name = this.name,
        symbol = this.name
    )
}

/**
 * List of [CoinInfoModelEntity] to List of [CoinInfoModel]
 */
fun List<CoinInfoModelEntity>.toIUModelFromEntity(): List<CoinInfoModel>{
    val result = mutableListOf<CoinInfoModel>()
    this.forEach {
        result.add(
            CoinInfoModel(
                id = it.id,
                name = it.name,
                symbol = it.name
            )
        )
    }
    return result
}

/**
 * [CoinInfoRemoteModel] to [CoinInfoModel]
 */
fun CoinInfoRemoteModel.toIUModel() : CoinInfoModel {
    return CoinInfoModel(
        id = this.id,
        name = this.name,
        symbol = this.name,
        marketData = this.marketData
    )
}

/**
 * List of [CoinInfoRemoteModel] to List of [CoinInfoModel]
 */
fun List<CoinInfoRemoteModel>.toIUModelFromRemote(): List<CoinInfoModel>{
    val result = mutableListOf<CoinInfoModel>()
    this.forEach {
        result.add(
            CoinInfoModel(
                id = it.id,
                name = it.name,
                symbol = it.name,
                marketData = it.marketData
            )
        )
    }
    return result
}