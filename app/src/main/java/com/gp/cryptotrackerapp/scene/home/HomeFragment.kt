package com.gp.cryptotrackerapp.scene.home

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.adapter.CoinListAdapter
import com.gp.cryptotrackerapp.adapter.CoinListAdapterListener
import com.gp.cryptotrackerapp.base.BaseFragment
import com.gp.cryptotrackerapp.data.model.coininfo.CoinInfoModel
import com.gp.cryptotrackerapp.data.model.common.Resource
import com.gp.cryptotrackerapp.databinding.FragmentHomeBinding
import com.gp.cryptotrackerapp.util.extension.createCoinAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_home

    private lateinit var adapter: CoinListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeAdapter()
        initializeListener()
        viewModel.getCoinListContinuously()
    }

    /**
     * Initialize observers
     */
    private fun initObservers() {
        viewModel.coinListData.observe(requireActivity()) { result ->
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

    /**
     * Initialize listeners
     */
    private fun initializeListener() {
        binding.spnHomeCurrency.setSelection(0, false)
        binding.spnHomeCurrency.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                globalCurrency = binding.spnHomeCurrency.selectedItem.toString()
                adapter.currency = globalCurrency
                adapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }
        }
    }

    /**
     * Initialize [CoinListAdapter]
     */
    private fun initializeAdapter() {
        adapter = CoinListAdapter()
        adapter.listener = object : CoinListAdapterListener {
            override fun onSelect(id: String, name: String) {
                navigateFragment(
                    HomeFragmentDirections.actionHomeFragmentToCoinDetailFragment(
                        id,
                        name
                    )
                )
            }

            override fun onAlert(id: String) {
                requireContext().createCoinAlertDialog(
                    id,
                    globalCurrency,
                    onPositive = { max, min ->
                        viewModel.setAlertValuesForCoin(id, max, min, globalCurrency)

                        Toast.makeText(
                            requireContext(),
                            "Coin alert is successfully added",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ).show()
            }
        }
        binding.rvHomeCryptoList.adapter = adapter
    }

    /**
     * Update [CoinListAdapter] with new market data
     */
    private fun updateCoinListAdapter(coinInfo: CoinInfoModel) {
        adapter.setData(coinInfo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.coinListJob.cancel()
    }
}