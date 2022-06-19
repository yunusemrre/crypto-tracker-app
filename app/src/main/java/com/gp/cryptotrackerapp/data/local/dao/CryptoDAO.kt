package com.gp.cryptotrackerapp.data.local.dao

import androidx.room.*
import com.gp.cryptotrackerapp.data.local.dao.converters.DateConverters
import com.gp.cryptotrackerapp.data.local.entities.CoinInfoModelEntity
import com.gp.cryptotrackerapp.data.local.entities.CoinMaxMinAlertEntity
import retrofit2.http.DELETE

@Dao
interface CryptoDAO {

    /**
     * INSERT
     */

    @Insert
    fun insertCoinsInfo(coinInfoEntity: List<CoinInfoModelEntity>)

    @Insert
    fun insertMaxMinVal(coinMinMax: List<CoinMaxMinAlertEntity>)

    /**
     * UPDATE
     */
    @Query("UPDATE coin_max_min_notification SET state = :state WHERE entityId = :id")
    fun updateAlertState(id: Int, state: Boolean)

    /**
     * LIST
     */

    @Query("SELECT * FROM coin_info")
    fun getCoinsInfo(): List<CoinInfoModelEntity>

    @Query("SELECT COUNT(id) FROM coin_info")
    fun getCoinsCount(): Int

    @Query("SELECT * FROM coin_max_min_notification WHERE coin_id = :id ORDER BY set_date DESC")
    fun getCoinAlertHistory(id: String): List<CoinMaxMinAlertEntity>

    @Query("SELECT * FROM coin_max_min_notification WHERE state = :state")
    fun getCoinAlertHistoryForAll(state: Boolean = true): List<CoinMaxMinAlertEntity>

}