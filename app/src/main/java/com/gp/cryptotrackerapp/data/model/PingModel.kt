package com.gp.cryptotrackerapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PingModel(
    @SerializedName("gecko_says")
    val geckoSays: String
) : Parcelable