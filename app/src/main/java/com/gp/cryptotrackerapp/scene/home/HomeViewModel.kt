package com.gp.cryptotrackerapp.scene.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gp.cryptotrackerapp.base.BaseViewModel
import com.gp.cryptotrackerapp.data.model.CoinInfo.CoinInfoModel
import com.gp.cryptotrackerapp.data.model.common.Resource
import com.gp.cryptotrackerapp.data.repository.CryptoServiceRepository
import com.gp.cryptotrackerapp.util.extension.models.toIUModel
import com.gp.cryptotrackerapp.util.extension.models.toIUModelFromEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cryptoServiceRepository: CryptoServiceRepository
) : BaseViewModel() {

    private val _coinListData = MutableLiveData<Resource<CoinInfoModel>>()
    val coinListData: LiveData<Resource<CoinInfoModel>> get() = _coinListData

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

    fun setAlertValuesForCoin(id: String, max: Float, min: Float) {
        makeRequest(
            requestFunc = {
                cryptoServiceRepository.setAlertValuesForCoin(id, max, min)
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