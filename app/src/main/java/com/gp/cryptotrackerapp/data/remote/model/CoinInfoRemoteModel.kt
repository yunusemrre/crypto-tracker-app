package com.gp.cryptotrackerapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.gp.cryptotrackerapp.data.model.coininfo.MarketDataModel
import kotlinx.parcelize.Parcelize

/**
 * Entity model of coin info
 */
@Parcelize
data class CoinInfoRemoteModel(
    var id: String? = null,
    var name: String? = null,
    var symbol: String? = null,
    @SerializedName("market_data")
    var marketData: MarketDataModel? = null
) : Parcelable