package com.gp.cryptotrackerapp.data.remote.model

import com.google.gson.annotations.SerializedName
import com.gp.cryptotrackerapp.data.model.CoinInfo.MarketDataModel

/**
 * Entity model of coin info
 */
data class CoinInfoRemoteModel(
    var id: String? = null,
    var name: String? = null,
    var symbol: String? = null,
    @SerializedName("market_data")
    var marketData: MarketDataModel? = null
)