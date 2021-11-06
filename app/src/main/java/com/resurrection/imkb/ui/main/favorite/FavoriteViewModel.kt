package com.resurrection.imkb.ui.main.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.resurrection.imkb.data.model.imkb.Stock
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
class FavoriteViewModel @Inject constructor(private val imkbRepository: ImkbRepository) :
    BaseViewModel() {

    private var _stocks = MutableLiveData<Resource<List<Stock>>>()
    var stocks: MutableLiveData<Resource<List<Stock>>> = _stocks

    fun getStocks() = viewModelScope.launch {
        imkbRepository.getStocks()
            .onStart { }
            .catch { }
            .collect { _stocks.postValue(it) }
    }
}