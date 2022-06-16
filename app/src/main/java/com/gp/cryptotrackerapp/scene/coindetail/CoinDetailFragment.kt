package com.gp.cryptotrackerapp.scene.coindetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.adapter.CoinAlertHistoryAdapter
import com.gp.cryptotrackerapp.adapter.CoinHistoryAdapter
import com.gp.cryptotrackerapp.base.BaseFragment
import com.gp.cryptotrackerapp.data.model.CoinInfo.CoinInfoModel
import com.gp.cryptotrackerapp.data.model.common.Resource
import com.gp.cryptotrackerapp.databinding.FragmentCoinDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailFragment: BaseFragment<FragmentCoinDetailBinding,CoinDetailViewModel>() {
    override val viewModel: CoinDetailViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_coin_detail

    private val args: CoinDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        getCoinHistories()
    }

    private fun initObservers(){

        viewModel.coinDataHistory.observe(viewLifecycleOwner){ res ->
            when(res.status){
                Resource.Status.SUCCESS -> {
                    res.data?.prices?.let {
                        initializeCoinHistoryAdapter(it)
                    }
                }
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {

                }
            }
        }

        viewModel.coinMaxMinHistory.observe(viewLifecycleOwner){ res ->
            when(res.status){
                Resource.Status.SUCCESS -> {
                    res.data?.let {
                        initializeCoinAlertHistoryAdapter(it as ArrayList<CoinInfoModel>)
                    }
                }
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {

                }
            }
        }
    }

    private fun getCoinHistories(){
        args.coinId.let { id ->
            viewModel.getCoinHistory(id)
            viewModel.getCoinAlertHistory(id)
        }
    }

    private fun initializeCoinHistoryAdapter(prices: ArrayList<ArrayList<Int>>){
        binding.rvCoinDetailHistory.adapter = CoinHistoryAdapter(prices)
    }

    private fun initializeCoinAlertHistoryAdapter(historyList: ArrayList<CoinInfoModel>){
        binding.tvCoinDetailAlertHistory.adapter = CoinAlertHistoryAdapter(historyList)
    }
}