package com.gp.cryptotrackerapp.di

import com.gp.cryptotrackerapp.data.local.db.AppDatabase
import com.gp.cryptotrackerapp.data.remote.datasource.CryptoServiceDataSource
import com.gp.cryptotrackerapp.data.repository.CryptoServiceRepository
import com.gp.cryptotrackerapp.data.repository.CryptoServiceRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCryptoServiceRepository(
        cryptoServiceDataSource: CryptoServiceDataSource,
        roomDatabase: AppDatabase
    ): CryptoServiceRepository =
        CryptoServiceRepositoryImp(cryptoServiceDataSource,roomDatabase)
}