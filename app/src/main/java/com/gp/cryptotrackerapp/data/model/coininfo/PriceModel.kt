package com.gp.cryptotrackerapp.data.model.coininfo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PriceModel(
    @SerializedName("eur")
    var euro: Double? = null,
    @SerializedName("usd")
    var usd: Double? = null,
    @SerializedName("try")
    var turkishLira: Double? = null
) : Parcelable
