package com.gp.cryptotrackerapp.data.model.CoinInfo

import com.google.gson.annotations.SerializedName

data class PriceModel(
    @SerializedName("eur")
    var euro: Double? = null,
    @SerializedName("usd")
    var usd: Double? = null,
    @SerializedName("try")
    var turkishLira: Double? = null
)
