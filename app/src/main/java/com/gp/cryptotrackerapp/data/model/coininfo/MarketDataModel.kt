package com.gp.cryptotrackerapp.data.model.coininfo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarketDataModel(
    @SerializedName("current_price")
    var currentPrice: PriceModel? = null,
    @SerializedName("last_updated")
    var lastUpdate: String? = null,
    @SerializedName("price_change_percentage_24h")
    var priceChangePerc24: Float? = null,
    @SerializedName("total_volume")
    var totalVolumeCurrency: PriceModel? = null
) : Parcelable