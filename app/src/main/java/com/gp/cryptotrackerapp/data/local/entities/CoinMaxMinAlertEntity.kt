package com.gp.cryptotrackerapp.data.local.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "coin_max_min_notification")
data class  CoinMaxMinAlertEntity(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var entityId: Int = 0,

    @ColumnInfo(name = "coin_id")
    var id: String,

    @ColumnInfo(name = "max_value_alert")
    var maxVal: Double? = null,

    @ColumnInfo(name = "min_value_alert")
    var minVal: Double? = null,

    @ColumnInfo(name = "set_date")
    var date: Date? = null,

    @ColumnInfo(name = "state")
    var active: Boolean? = null,

    @ColumnInfo(name = "currency")
    var currency: String? = null,

    @ColumnInfo(name = "is_alerted")
    var isAlerted: Boolean? = false
)