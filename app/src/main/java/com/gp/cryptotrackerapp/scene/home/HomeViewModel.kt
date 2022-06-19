package com.gp.cryptotrackerapp.scene.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gp.cryptotrackerapp.base.BaseViewModel
import com.gp.cryptotrackerapp.data.local.entities.CoinMaxMinAlertEntity
import com.gp.cryptotrackerapp.data.model.coininfo.CoinInfoModel
import com.gp.cryptotrackerapp.data.model.common.Resource
import com.gp.cryptotrackerapp.data.repository.CryptoServiceRepository
import com.gp.cryptotrackerapp.util.AppConstant
import com.gp.cryptotrackerapp.util.extension.models.toIUModel
import com.gp.cryptotrackerapp.util.extension.models.toIUModelFromEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cryptoServiceRepository: CryptoServiceRepository
) : BaseViewModel() {

    private val _coinListData = MutableLiveData<Resource<CoinInfoModel>>()
    val coinListData: LiveData<Resource<CoinInfoModel>> get() = _coinListData

    lateinit var coinListJob: Job

    /**
     * Get coin list from db -> BTC, ETH, XRP
     */
    private fun getCoinList() {
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

    /**
     * Get coin list data from api continuously
     */
    fun getCoinListContinuously() {
        coinListJob = viewModelScope.launch(Dispatchers.IO) {
            var first = true
            while (true) {
                if (!first)
                    delay(AppConstant.SYNC_TIME_COIN_LIST)
                getCoinList()
                first = false
            }
        }
    }

    /**
     * Get coin current data to update market data
     */
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

    /**
     * Set coin notification data to db
     * @param id: Coin id
     * @param max: Max value if exist
     * @param min: Min value if exist
     * @param currency: Current currency
     */
    fun setAlertValuesForCoin(id: String, max: String?, min: String?, currency: String) {

        val list = arrayListOf<CoinMaxMinAlertEntity>()

        //Check max data is null or empty or zero
        if (max != null && max != "" && max != "0") {
            list.add(
                CoinMaxMinAlertEntity(
                    id = id,
                    maxVal = max.toDouble(),
                    date = Calendar.getInstance().time,
                    active = true,
                    currency = currency
                )
            )
        }

        //Check min data is null or empty or zero
        if (min != null && min != "" && min != "0") {
            list.add(
                CoinMaxMinAlertEntity(
                    id = id,
                    minVal = min.toDouble(),
                    date = Calendar.getInstance().time,
                    active = true,
                    currency = currency
                )
            )
        }

        makeRequest(
            requestFunc = {
                cryptoServiceRepository.setAlertValuesForCoin(
                    list
                )
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