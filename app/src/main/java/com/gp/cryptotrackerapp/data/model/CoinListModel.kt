package com.gp.cryptotrackerapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinListModel(
    val id: String,
    val symbol: String,
    val name: String
) : Parcelable