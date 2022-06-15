package com.gp.cryptotrackerapp.data.model.CoinInfo

import com.google.gson.annotations.SerializedName

data class MarketDataModel(
    @SerializedName("current_price")
    var currentPrice: PriceModel? = null
)