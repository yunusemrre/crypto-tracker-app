package com.gp.cryptotrackerapp.scene.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gp.cryptotrackerapp.base.BaseViewModel
import com.gp.cryptotrackerapp.data.local.entities.CoinListModel
import com.gp.cryptotrackerapp.data.model.common.Resource
import com.gp.cryptotrackerapp.data.repository.CryptoServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cryptoServiceRepository: CryptoServiceRepository
) : BaseViewModel() {

    private val _coinList = MutableLiveData<Resource<List<CoinListModel>>>()
    val coinList: LiveData<Resource<List<CoinListModel>>> get() = _coinList

    fun getCoinList() {

        makeNetworkRequest(
            requestFunc = {
                cryptoServiceRepository.getCoinList()
            },
            onSuccess = {
                Timber.d("$this")
                _coinList.value = Resource.success(it)
            },
            onFail = {
                Timber.d("$this")
                _coinList.postValue(Resource.error(it.getMsg().toString(), null))
            }

        )
    }
}