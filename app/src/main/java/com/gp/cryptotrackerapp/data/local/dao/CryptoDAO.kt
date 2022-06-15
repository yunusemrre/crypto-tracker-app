package com.gp.cryptotrackerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gp.cryptotrackerapp.data.local.entities.CoinInfoModelEntity
import com.gp.cryptotrackerapp.data.local.entities.CoinMaxMinAlertEntity

@Dao
interface CryptoDAO {

    @Insert
    fun insertCryptos(coinInfoEntity: List<CoinInfoModelEntity>)

    @Query("SELECT * FROM cryptos")
    fun getCoinsInfo(): List<CoinInfoModelEntity>

    @Query("SELECT COUNT(id) FROM cryptos")
    fun getCoinsCount(): Int

    @Insert
    fun insertMaxMinVal(coinMinMax: CoinMaxMinAlertEntity)

}