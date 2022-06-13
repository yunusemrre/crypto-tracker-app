package com.gp.cryptotrackerapp.scene.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.base.BaseFragment
import com.gp.cryptotrackerapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}