package com.resurrection.imkb.ui.base.data

import android.os.Bundle
import com.resurrection.imkb.ui.base.util.modelToString
import com.resurrection.imkb.ui.base.util.stringToModel
import javax.inject.Inject

class DataHolderManager @Inject constructor(private val dataHolder: Bundle) {
    fun save(key:String,any: Any) = dataHolder.putString(key, modelToString(any))
    fun <T> get(key:String) = stringToModel<T>(dataHolder.getString(key)!!)
    fun clear() = dataHolder.clear()
}