package com.gp.cryptotrackerapp.scene.coindetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

@AndroidEntryPoint
class CoinDetailFragment : BaseFragment<FragmentCoinDetailBinding, CoinDetailViewModel>() {
    override val viewModel: CoinDetailViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_coin_detail

    private val args: CoinDetailFragmentArgs by navArgs()

    private lateinit var coinHistoryAdapter: CoinHistoryAdapter
    private lateinit var alertHistoryAdapter: CoinAlertHistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListener()
        initObservers()
        getCoinData()
    }

    /**
     * Initialize UI elements
     */
    private fun initUI() {
        (args.coinName + " / " + globalCurrency).also { binding.mtvCoinInfo.text = it }
        initializeCoinAlertHistoryAdapter()
    }

    /**
     * Initialize listeners
     */
    private fun initListener() {
        binding.ivDetailBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    /**
     * Initialize observers
     */
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
                        updateAlertHistoryAdapter(it as ArrayList<CoinInfoModel>)
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
                                        coin.marketData?.currentPrice?.euro ?: 0.0,
                                        coin.marketData?.lastUpdate?.toDoubleDate() ?: 0.0
                                    )
                                }
                                CurrencyEnum.TRY.name -> {
                                    coinHistoryAdapter.setData(
                                        coin.marketData?.currentPrice?.turkishLira ?: 0.0,
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

    /**
     * Get all coin data by [CoinDetailViewModel]
     */
    private fun getCoinData() {
        showProgressDialog()
        args.coinId.let { id ->
            viewModel.getCoinHistory(id, globalCurrency)
            viewModel.getCoinAlertHistory(id)
            viewModel.getCoinCurrentData(id)
        }
    }

    /**
     * Initialize [CoinHistoryAdapter]
     */
    private fun initializeCoinHistoryAdapter(prices: ArrayList<ArrayList<Double>>) {
        coinHistoryAdapter = CoinHistoryAdapter()
        binding.rvCoinDetailHistory.adapter = coinHistoryAdapter
        coinHistoryAdapter.setList(prices)
    }

    /**
     * Initialize [CoinAlertHistoryAdapter]
     */
    private fun initializeCoinAlertHistoryAdapter() {
        alertHistoryAdapter = CoinAlertHistoryAdapter(object : CoinAlertHistoryAdapterListener {
            override fun setAlertState(entityId: Int, state: Boolean) {
                viewModel.updateAlertState(entityId, state)
            }
        })
        binding.rvCoinDetailAlertHistory.adapter = alertHistoryAdapter
    }

    /**
     * Update [CoinAlertHistoryAdapter] on each update of data change
     */
    private fun updateAlertHistoryAdapter(historyList: ArrayList<CoinInfoModel>) {
        alertHistoryAdapter.setData(historyList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.cancelJobs()
    }
}