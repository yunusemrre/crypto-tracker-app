package com.gp.cryptotrackerapp.data.local.dao

import androidx.room.*
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
     * UPDATE
     */
    @Query("UPDATE coin_max_min SET state = :state WHERE entityId = :id")
    fun updateAlertState(id: Int, state: Boolean)


    /**
     * LIST
     */

    @Query("SELECT * FROM cryptos")
    fun getCoinsInfo(): List<CoinInfoModelEntity>

    @Query("SELECT COUNT(id) FROM cryptos")
    fun getCoinsCount(): Int

    @Query("SELECT * FROM coin_max_min WHERE coin_id = :id ORDER BY set_date DESC")
    fun getCoinAlertHistory(id: String): List<CoinMaxMinAlertEntity>

    @Query("SELECT * FROM coin_max_min WHERE state = :state")
    fun getCoinAlertHistoryForAll(state: Boolean = true): List<CoinMaxMinAlertEntity>


}