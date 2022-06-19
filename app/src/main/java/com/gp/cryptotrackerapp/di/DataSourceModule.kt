package com.gp.cryptotrackerapp.di

import com.gp.cryptotrackerapp.data.remote.api.CryptoService
import com.gp.cryptotrackerapp.data.remote.datasource.CryptoServiceDataSource
import com.gp.cryptotrackerapp.data.remote.datasource.CryptoServiceDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Crypto service [Hilt] module
 */
@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideCryptoServiceDataSource(cryptoService: CryptoService): CryptoServiceDataSource =
        CryptoServiceDataSourceImp(cryptoService)
}