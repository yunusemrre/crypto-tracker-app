package com.gp.cryptotrackerapp.scene.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.base.BaseActivity
import com.gp.cryptotrackerapp.databinding.ActivityMainBinding
import com.gp.cryptotrackerapp.scene.home.HomeFragment
import com.gp.cryptotrackerapp.service.CoinControlService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main) {
    override val viewModel: MainViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startCoinControlService()
    }

    private fun startCoinControlService(){
        val startIntent = Intent(this, CoinControlService::class.java)
        startService(startIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment_main)
        return navController.navigateUp()
    }
}