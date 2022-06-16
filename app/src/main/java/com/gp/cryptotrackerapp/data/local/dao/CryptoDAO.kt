package com.gp.cryptotrackerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import com.gp.cryptotrackerapp.data.local.dao.converters.DateConverters
import com.gp.cryptotrackerapp.data.local.entities.CoinInfoModelEntity
import com.gp.cryptotrackerapp.data.local.entities.CoinMaxMinAlertEntity

@Dao
interface CryptoDAO {

    /**
     * INSERT
     */

    @Insert
    fun insertCoinsInfo(coinInfoEntity: List<CoinInfoModelEntity>)

    @Insert
    fun insertMaxMinVal(coinMinMax: CoinMaxMinAlertEntity)


    /**
     * LIST
     */

    @Query("SELECT * FROM cryptos")
    fun getCoinsInfo(): List<CoinInfoModelEntity>

    @Query("SELECT COUNT(id) FROM cryptos")
    fun getCoinsCount(): Int

    @Query("SELECT * FROM coin_max_min WHERE coin_id = :id") //TODO
    fun getCoinAlertHistory(id: String): List<CoinMaxMinAlertEntity>


}