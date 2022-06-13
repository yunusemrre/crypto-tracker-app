package com.gp.cryptotrackerapp.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
    }

    /**
     * Initialize [Timber]
     */
    private fun initTimber() {
        Timber.plant()
    }
}