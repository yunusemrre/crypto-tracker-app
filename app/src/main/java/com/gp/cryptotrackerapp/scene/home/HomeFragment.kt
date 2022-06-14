package com.gp.cryptotrackerapp.scene.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.base.BaseFragment
import com.gp.cryptotrackerapp.data.model.common.Resource
import com.gp.cryptotrackerapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        getCoinList()
    }

    private fun initObservers(){

        viewModel.coinList.observe(viewLifecycleOwner){ result ->
            when(result.status){
                Resource.Status.SUCCESS -> {
                    hideProgressDialog()
                    Toast.makeText(requireContext(),result.data?.get(2)?.name,Toast.LENGTH_LONG).show()
                    Timber.i(result.data?.size.toString())
                }
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {
                    hideProgressDialog()
                }
            }
        }

    }

    private fun getCoinList(){
        showProgressDialog()
        viewModel.getCoinList()
    }

}