package com.gp.cryptotrackerapp.data.model.CoinInfo

/**
 * UI model of coin info
 */
data class CoinInfoModel(
    var id: String? = null,
    var name: String? = null,
    var symbol: String? = null,
    var marketData: MarketDataModel? = null
)