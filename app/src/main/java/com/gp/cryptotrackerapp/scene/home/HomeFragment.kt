package com.gp.cryptotrackerapp.scene.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.gp.cryptotrackerapp.BuildConfig
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.adapter.CoinListAdapter
import com.gp.cryptotrackerapp.adapter.CoinListAdapterListener
import com.gp.cryptotrackerapp.base.BaseFragment
import com.gp.cryptotrackerapp.data.model.coininfo.CoinInfoModel
import com.gp.cryptotrackerapp.data.model.common.Resource
import com.gp.cryptotrackerapp.databinding.FragmentHomeBinding
import com.gp.cryptotrackerapp.util.extension.createCoinAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.lang.Exception

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

    private fun initializeListener(){
        binding.spnHomeCurrency.setSelection(0,false)
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

    private fun initializeAdapter() {
        adapter = CoinListAdapter()
        adapter.listener = object : CoinListAdapterListener {
            override fun onSelect(id: String,name:String) {
                navigateFragment(HomeFragmentDirections.actionHomeFragmentToCoinDetailFragment(id,name))
            }

            override fun onAlert(id: String) {
                requireContext().createCoinAlertDialog(
                    id,
                    onPositive = { max, min ->
                        viewModel.setAlertValuesForCoin(id, max, min)
                    }
                ).show()
            }
        }
        binding.rvHomeCryptoList.adapter = adapter
    }

    private fun updateCoinListAdapter(coinInfo: CoinInfoModel) {
        Log.i("asdfasdf","updated")
        adapter.setData(coinInfo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.coinListJob.cancel()
    }
}