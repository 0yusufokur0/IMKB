package com.resurrection.imkb.ui.base.data

import android.os.Bundle
import com.resurrection.imkb.ui.base.util.modelToString
import com.resurrection.imkb.ui.base.util.stringToModel

class DataHolderManager(val bundle: Bundle) {
    fun put(key:String, any: Any) = bundle.putString(key, modelToString(any))
    fun <T> get(key:String) = stringToModel<T>(bundle.getString(key)!!)
    fun remove(key:String) = bundle.remove(key)
    fun clear() = bundle.clear()
}