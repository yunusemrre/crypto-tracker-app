package com.gp.cryptotrackerapp.data.model.coininfo

import java.util.*


/**
 * UI model of coin info
 */
data class CoinInfoModel(
    var id: String? = null,
    var name: String? = null,
    var symbol: String? = null,
    var marketData: MarketDataModel? = null,

    //alert
    var alertEntityId: Int? = null,
    var maxAlert: Double? = null,
    var minAlert: Double? = null,
    var insertDate: Date? = null,
    var active: Boolean? = null
)