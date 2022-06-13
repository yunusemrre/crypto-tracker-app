package com.gp.cryptotrackerapp.scene.main

import android.os.Bundle
import androidx.activity.viewModels
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.base.BaseActivity
import com.gp.cryptotrackerapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override val viewModel: MainViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {}
}