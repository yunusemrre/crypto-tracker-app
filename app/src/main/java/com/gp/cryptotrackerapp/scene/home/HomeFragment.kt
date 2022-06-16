package com.gp.cryptotrackerapp.scene.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.adapter.CoinListAdapter
import com.gp.cryptotrackerapp.adapter.CoinListAdapterListener
import com.gp.cryptotrackerapp.base.BaseFragment
import com.gp.cryptotrackerapp.data.model.CoinInfo.CoinInfoModel
import com.gp.cryptotrackerapp.data.model.common.Resource
import com.gp.cryptotrackerapp.databinding.FragmentHomeBinding
import com.gp.cryptotrackerapp.util.extension.createAlertDialogYesNo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_home

    private lateinit var adapter: CoinListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initializeAdapter()
        getCoinData()
    }

    private fun initObservers() {

        viewModel.coinListData.observe(viewLifecycleOwner) { result ->
            when (result.status) {
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

    private fun initializeAdapter() {
        adapter = CoinListAdapter()
        adapter.listener = object : CoinListAdapterListener {
            override fun onSelect(id: String) {
                navigateFragment(HomeFragmentDirections.actionHomeFragmentToCoinDetailFragment(id))
            }

            override fun onAlert(id: String) {
                requireContext().createAlertDialogYesNo(
                    id,
                    onPositive = { max, min ->
                        viewModel.setAlertValuesForCoin(id, 0F, 0F)
                    }
                ).show()
            }
        }
        binding.rvHomeCryptoList.adapter = adapter
    }

    private fun updateCoinListAdapter(coinInfo: CoinInfoModel) {
        adapter.setData(coinInfo)
    }
}