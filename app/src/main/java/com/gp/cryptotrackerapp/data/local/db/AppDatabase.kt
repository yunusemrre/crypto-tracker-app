package com.gp.cryptotrackerapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gp.cryptotrackerapp.data.local.dao.CryptoDAO
import com.gp.cryptotrackerapp.data.local.entities.CoinInfoModelEntity
import com.gp.cryptotrackerapp.data.local.entities.CoinMaxMinAlertEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [CoinInfoModelEntity::class, CoinMaxMinAlertEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cryptoDao(): CryptoDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }.apply {
                populateInitialData()
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "CryptoTrackerApp"
            )
                .fallbackToDestructiveMigration()
                .build()
    }

    private fun populateInitialData() {
        GlobalScope.launch(Dispatchers.IO) {
            if (cryptoDao().getCoinsCount() == 0) {
                runInTransaction {
                    cryptoDao().insertCryptos(
                        mutableListOf(
                            CoinInfoModelEntity(id = "bitcoin", name = "Bitcoin", symbol = "BTC"),
                            CoinInfoModelEntity(id = "ethereum", name = "Ethereum", symbol = "ETH"),
                            CoinInfoModelEntity(id = "ripple", name = "XRP", symbol = "XRP"),
                        )
                    )
                }
            }
        }
    }
}