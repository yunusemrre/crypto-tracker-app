package com.gp.cryptotrackerapp.data.local.entities

import android.support.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_max_min")
data class CoinMaxMinAlertEntity(
    @PrimaryKey
    @NonNull
    var id: String,
    @ColumnInfo(name = "max_value_alert")
    var maxVal: Float? = null,
    @ColumnInfo(name = "min_value_alert")
    var minVal: Float? = null
)