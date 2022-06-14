package com.gp.cryptotrackerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gp.cryptotrackerapp.data.local.entities.CoinListModel

@Dao
interface CryptoDAO {

    @Insert
    fun insertCryptos(coinList: List<CoinListModel>)

    @Query("SELECT * FROM cryptos")
    fun getCryptoInfo(): List<CoinListModel>

    @Query("SELECT COUNT(id) FROM cryptos")
    fun getCryptoCount(): Int

}