package com.gp.cryptotrackerapp.scene.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gp.cryptotrackerapp.BuildConfig
import com.gp.cryptotrackerapp.base.BaseViewModel
import com.gp.cryptotrackerapp.data.model.coininfo.CoinInfoModel
import com.gp.cryptotrackerapp.data.model.common.Resource
import com.gp.cryptotrackerapp.data.repository.CryptoServiceRepository
import com.gp.cryptotrackerapp.util.extension.models.toIUModel
import com.gp.cryptotrackerapp.util.extension.models.toIUModelFromEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cryptoServiceRepository: CryptoServiceRepository
) : BaseViewModel() {

    private val _coinListData = MutableLiveData<Resource<CoinInfoModel>>()
    val coinListData: LiveData<Resource<CoinInfoModel>> get() = _coinListData

    lateinit var coinListJob : Job

    fun getCoinList() {
        makeRequest(
            requestFunc = {
                cryptoServiceRepository.getCoinList() // from database
            },
            onSuccess = {
                it.toIUModelFromEntity().let { res ->
                    res.forEach { coinInfo ->
                        coinInfo.id?.let { id ->
                            getCoinCurrentData(id)
                        }
                    }
                }
                Timber.d("$this")
            },
            onFail = {
                Timber.d("$this")
            }

        )
    }

    fun getCoinListContinuously(){
        coinListJob = viewModelScope.launch(Dispatchers.IO) {
            var first = true
            while (true) {
                if (!first)
                    delay(BuildConfig.SYNC_TIME)
                getCoinList()
                first = false
            }
        }
    }

    private fun getCoinCurrentData(id: String) {
        makeRequest(
            requestFunc = {
                cryptoServiceRepository.getCoinCurrentData(id)
            },
            onSuccess = {
                _coinListData.value = Resource.success(it.toIUModel())
            },
            onFail = {
                _coinListData.value = Resource.error(it.getMsg().toString(), null)
            }
        )
    }

    fun setAlertValuesForCoin(id: String, max: String?, min: String?) {

        var maxV = max
        var minV = min

        if(max == null || max == "")
            maxV = "0"
        if(min == null || min == "")
            minV = "0"

        makeRequest(
            requestFunc = {
                cryptoServiceRepository.setAlertValuesForCoin(id, (maxV as String).toDouble(), (minV as String).toDouble())
            },
            onSuccess = {
                Timber.i(it.toString())
            },
            onFail = {
                Timber.i(it.toString())
            }
        )
    }
}