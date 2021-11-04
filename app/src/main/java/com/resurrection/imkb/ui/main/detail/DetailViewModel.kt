package com.resurrection.imkb.ui.main.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.resurrection.imkb.data.model.imkb.DetailRequest
import com.resurrection.imkb.data.model.imkb.DetailResponse
import com.resurrection.imkb.data.repository.ImkbRepository
import com.resurrection.imkb.ui.base.BaseViewModel
import com.resurrection.imkb.util.Resource
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
    var detail : MutableLiveData<Resource<DetailResponse>> = _detail


        fun getDetail(authStr:String,detailRequest: DetailRequest) = viewModelScope.launch{
            imkbRepository.getRequestDetail(authStr,detailRequest)
                .onStart {}
                .catch {  }
                .collect {
                    _detail.postValue(it)
                }
        }



}