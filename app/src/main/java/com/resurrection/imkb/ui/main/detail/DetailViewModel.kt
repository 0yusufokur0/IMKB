package com.resurrection.imkb.ui.main.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.resurrection.imkb.data.model.imkb.DetailRequest
import com.resurrection.imkb.data.model.imkb.DetailResponse
import com.resurrection.imkb.data.model.imkb.Stock
import com.resurrection.imkb.data.repository.ImkbRepository
import com.resurrection.imkb.ui.base.BaseViewModel
import com.resurrection.imkb.util.Resource
import com.resurrection.imkb.util.isValid

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class DetailViewModel @Inject constructor(private val imkbRepository: ImkbRepository) :
    BaseViewModel() {

    private var _detail = MutableLiveData<Resource<DetailResponse>>()
    val detail: MutableLiveData<Resource<DetailResponse>> = _detail
    private var _isAdded = MutableLiveData<Resource<Boolean>>()
    val isAdded: MutableLiveData<Resource<Boolean>> = _isAdded
    private var _isDeleted = MutableLiveData<Resource<Boolean>>()
    val isDeleted: MutableLiveData<Resource<Boolean>> = _isDeleted

    private var _isFavorite = MutableLiveData<Resource<Boolean>>()
    val isFavorite: MutableLiveData<Resource<Boolean>> = _isFavorite

    fun getDetail(authStr: String, detailRequest: DetailRequest) = viewModelScope.launch {
        if (detailRequest.isValid()&& authStr != ""){
            imkbRepository.getRequestDetail(authStr, detailRequest)
                .onStart { _detail.postValue(Resource.Loading()) }
                .catch { _detail.postValue(Resource.Error(it)) }
                .collect { _detail.postValue(it) }
        }else _detail.postValue(Resource.Error(Throwable("request id is empty")))
    }

    fun insertFavorite(stock: Stock) = viewModelScope.launch {
        if (stock.bid.isValid()){
            imkbRepository.insertDetailResponse(stock)
                .onStart { _isAdded.postValue(Resource.Loading()) }
                .catch { _isAdded.postValue(Resource.Error(it)) }
                .collect { _isAdded.postValue(Resource.Success(true)) }
        }else _isAdded.postValue(Resource.Error(Throwable("stock bid is empty")))
    }

    fun deleteFavorite(stock: Stock) = viewModelScope.launch {
        Log.d("Msg",stock.bid.toString())
        if (stock.bid.isValid()){
            imkbRepository.removeDetailResponse(stock)
                .onStart { _isDeleted.postValue(Resource.Loading()) }
                .catch { _isDeleted.postValue(Resource.Error(it)) }
                .collect { _isDeleted.postValue(Resource.Success(true)) }
        } else _isDeleted.postValue(Resource.Error(Throwable("stock bid is empty")))
    }

    fun getFavoriteState(id: Double) = viewModelScope.launch {
            if (id.isValid()){
                imkbRepository.getStock(id)
                    .onStart { _isFavorite.postValue(Resource.Loading()) }
                    .catch { _isFavorite.postValue(Resource.Error(it)) }
                    .collect {
                        it.data?.let {
                            _isFavorite.postValue(Resource.Success(true))
                        } ?: run {
                            _isFavorite.postValue(Resource.Success(false))
                        }
                    }
            } else _isFavorite.postValue(Resource.Error(Throwable("id is empty")))
    }


}