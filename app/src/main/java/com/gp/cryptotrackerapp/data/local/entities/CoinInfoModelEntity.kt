package com.gp.cryptotrackerapp.data.local.entities

import android.support.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity model of coin info
 */
@Entity(tableName = "cryptos")
data class CoinInfoModelEntity(
    @PrimaryKey
    @NonNull
    var id: String,
    @ColumnInfo(name = "crypto_name")
    var name: String? = null,
    @ColumnInfo(name = "crypto_symbol")
    var symbol: String? = null
)