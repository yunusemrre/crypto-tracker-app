package com.gp.cryptotrackerapp.scene.coindetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gp.cryptotrackerapp.base.BaseViewModel
import com.gp.cryptotrackerapp.data.model.coininfo.CoinHistoryModel
import com.gp.cryptotrackerapp.data.model.coininfo.CoinInfoModel
import com.gp.cryptotrackerapp.data.model.common.Resource
import com.gp.cryptotrackerapp.data.repository.CryptoServiceRepository
import com.gp.cryptotrackerapp.util.extension.models.toIUFromMaxMin
import com.gp.cryptotrackerapp.util.extension.models.toIUModel
import com.gp.cryptotrackerapp.util.extension.models.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private var repository: CryptoServiceRepository
) : BaseViewModel() {

    private val _coinDataHistory = MutableLiveData<Resource<CoinHistoryModel>>()
    val coinDataHistory: LiveData<Resource<CoinHistoryModel>> get() = _coinDataHistory

    private val _coinMaxMinHistory = MutableLiveData<Resource<List<CoinInfoModel>>>()
    val coinMaxMinHistory: LiveData<Resource<List<CoinInfoModel>>> get() = _coinMaxMinHistory

    private val _coinLastData = MutableLiveData<Resource<CoinInfoModel>>()
    val coinLastData : LiveData<Resource<CoinInfoModel>> get() = _coinLastData

    fun getCoinHistory(id: String,currency: String){
        makeRequest(
            requestFunc = {
                repository.getCoinHistory(id,currency)
            },
            onSuccess = {
                _coinDataHistory.value = Resource.success(it.toUIModel())
            },
            onFail = {
                _coinDataHistory.value = Resource.error(it.getMsg().toString(),null)
            }
        )
    }

    fun getCoinAlertHistory(id:String){
        makeRequest(
            requestFunc = {
                repository.getCoinAlertHistory(id)
            },
            onSuccess = {
                _coinMaxMinHistory.value = Resource.success(it.toIUFromMaxMin())
            },
            onFail = {
                _coinDataHistory.value = Resource.error(it.getMsg().toString(),null)
            }
        )
    }

    fun getCoinCurrentData(id: String) {
        makeRequest(
            requestFunc = {
                repository.getCoinCurrentData(id)
            },
            onSuccess = {
                _coinLastData.value = Resource.success(it.toIUModel())
            },
            onFail = {
                _coinLastData.value = Resource.error(it.getMsg().toString(), null)
            }
        )
    }

    fun updateAlertState(id: Int,state : Boolean) {
        makeRequest(
            requestFunc = {
                repository.updateAlertState(id, state)
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