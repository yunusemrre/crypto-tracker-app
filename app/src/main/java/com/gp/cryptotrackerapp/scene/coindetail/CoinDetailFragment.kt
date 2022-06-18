package com.gp.cryptotrackerapp.scene.coindetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.adapter.CoinAlertHistoryAdapter
import com.gp.cryptotrackerapp.adapter.CoinAlertHistoryAdapterListener
import com.gp.cryptotrackerapp.adapter.CoinHistoryAdapter
import com.gp.cryptotrackerapp.base.BaseFragment
import com.gp.cryptotrackerapp.data.model.coininfo.CoinInfoModel
import com.gp.cryptotrackerapp.data.model.common.Resource
import com.gp.cryptotrackerapp.databinding.FragmentCoinDetailBinding
import com.gp.cryptotrackerapp.util.enum.CurrencyEnum
import com.gp.cryptotrackerapp.util.extension.toDoubleDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CoinDetailFragment : BaseFragment<FragmentCoinDetailBinding, CoinDetailViewModel>() {
    override val viewModel: CoinDetailViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_coin_detail

    private val args: CoinDetailFragmentArgs by navArgs()

    private lateinit var coinHistoryAdapter: CoinHistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObservers()
        getCoinHistories()
    }

    private fun initUI() {
        (args.coinName + " / " + globalCurrency).also { binding.mtvCoinInfo.text = it }
    }

    private fun initObservers() {

        viewModel.coinDataHistory.observe(viewLifecycleOwner) { res ->
            when (res.status) {
                Resource.Status.SUCCESS -> {
                    res.data?.prices?.let {
                        initializeCoinHistoryAdapter(it)
                    }
                    hideProgressDialog()
                }
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {
                    hideProgressDialog()
                }
            }
        }

        viewModel.coinMaxMinHistory.observe(viewLifecycleOwner) { res ->
            when (res.status) {
                Resource.Status.SUCCESS -> {
                    res.data?.let {
                        initializeCoinAlertHistoryAdapter(it as ArrayList<CoinInfoModel>)
                    }
                    hideProgressDialog()
                }
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {
                    hideProgressDialog()
                }
            }
        }

        viewModel.coinLastData.observe(viewLifecycleOwner) { lastUpdate ->
            when (lastUpdate.status) {
                Resource.Status.SUCCESS -> {
                    lastUpdate.data?.let { coin ->
                        if (coin.id == args.coinId) {
                            when (globalCurrency) {
                                CurrencyEnum.USD.name -> {
                                    coinHistoryAdapter.setData(
                                        coin.marketData?.currentPrice?.usd ?: 0.0,
                                        coin.marketData?.lastUpdate?.toDoubleDate() ?: 0.0
                                    )
                                }
                                CurrencyEnum.EUR.name -> {
                                    coinHistoryAdapter.setData(
                                        coin.marketData?.currentPrice?.turkishLira ?: 0.0,
                                        coin.marketData?.lastUpdate?.toDoubleDate() ?: 0.0
                                    )
                                }
                                CurrencyEnum.TRY.name -> {
                                    coinHistoryAdapter.setData(
                                        coin.marketData?.currentPrice?.euro ?: 0.0,
                                        coin.marketData?.lastUpdate?.toDoubleDate() ?: 0.0
                                    )
                                }
                            }

                        }
                    }
                }
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {
                }
            }
        }
    }

    private fun getCoinHistories() {
        showProgressDialog()
        args.coinId.let { id ->
            viewModel.getCoinHistory(id, globalCurrency)
            viewModel.getCoinAlertHistory(id)
            GlobalScope.launch(Dispatchers.IO) {
                while (true) {
                    delay(15000)
                    viewModel.getCoinCurrentData(id)
                }
            }
        }
    }

    private fun initializeCoinHistoryAdapter(prices: ArrayList<ArrayList<Double>>) {
        coinHistoryAdapter = CoinHistoryAdapter()
        binding.rvCoinDetailHistory.adapter = coinHistoryAdapter
        coinHistoryAdapter.setList(prices)
    }

    private fun initializeCoinAlertHistoryAdapter(historyList: ArrayList<CoinInfoModel>) {
        binding.rvCoinDetailAlertHistory.adapter =
            CoinAlertHistoryAdapter(historyList, object : CoinAlertHistoryAdapterListener {
                override fun setAlertState(entityId: Int, state: Boolean) {
                    viewModel.updateAlertState(entityId, state)
                }
            })
    }
}