package com.resurrection.imkb.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.resurrection.imkb.data.model.handshake.HandshakeRequest
import com.resurrection.imkb.data.model.handshake.HandshakeResponse
import com.resurrection.imkb.data.model.imkb.ListRequest
import com.resurrection.imkb.data.model.imkb.ListResponse
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
class HomeViewModel @Inject constructor(private val imkbRepository: ImkbRepository) :
    BaseViewModel() {
    private var _auth = MutableLiveData<Resource<HandshakeResponse>>()
    val auth: LiveData<Resource<HandshakeResponse>> = _auth

    private var _listResponse = MutableLiveData<Resource<ListResponse>>()
    var listResponse: MutableLiveData<Resource<ListResponse>> = _listResponse

    fun getAuth(request: HandshakeRequest) = viewModelScope.launch {
        imkbRepository.getHandShake(request)
            .onStart { }
            .catch { }
            .collect { _auth.postValue(it) }
    }

    fun getResponseList(XVPAuthorization: String, request: ListRequest) = viewModelScope.launch {
        imkbRepository.getRequestList(XVPAuthorization, request)
            .onStart { _listResponse.postValue(Resource.Loading()) }
            .catch { println("view model catch de hata var") }
            .collect { _listResponse.postValue(it) }

    }
}
