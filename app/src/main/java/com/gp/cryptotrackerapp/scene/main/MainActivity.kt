package com.gp.cryptotrackerapp.scene.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.service.CoinControlService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startCoinControlService()
    }

    private fun startCoinControlService() {
        val startIntent = Intent(this, CoinControlService::class.java)
        startService(startIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment_main)
        return navController.navigateUp()
    }
}