package com.gp.cryptotrackerapp.scene.home

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.adapter.CoinListAdapter
import com.gp.cryptotrackerapp.adapter.CoinListAdapterListener
import com.gp.cryptotrackerapp.base.BaseFragment
import com.gp.cryptotrackerapp.data.model.CoinInfo.CoinInfoModel
import com.gp.cryptotrackerapp.data.model.common.Resource
import com.gp.cryptotrackerapp.databinding.FragmentCoinListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentCoinListBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_coin_list

    lateinit var adapter : CoinListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initializeAdapter()
        getCoinData()
    }

    private fun initObservers() {

        viewModel.coinListData.observe(viewLifecycleOwner){ result ->
            when(result.status){
                Resource.Status.SUCCESS -> {
                    result.data?.let {
                        updateCoinListAdapter(it)
                    }
                }
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {

                }
            }
        }
    }

    private fun getCoinData() {
        viewModel.getCoinList()
    }

    private fun initializeAdapter(){
        adapter = CoinListAdapter()
        adapter.listener = object : CoinListAdapterListener {
            override fun onSelect(id: String) {

            }
        }
        binding.rvHomeCryptoList.adapter = adapter
    }

    private fun updateCoinListAdapter(coinInfo : CoinInfoModel) {
        adapter.setData(coinInfo)
    }
}