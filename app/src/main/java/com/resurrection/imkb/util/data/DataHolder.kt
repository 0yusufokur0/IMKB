package com.resurrection.imkb.util.data

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.internal.Primitives
import java.lang.reflect.Type
import javax.inject.Inject

class DataHolder @Inject constructor(private val dataHolder: Bundle) {

    fun save(key:String,any: Any) = dataHolder.putString(key, Gson().toJson(any))
    fun <T> get(key:String,typeOfT: Type?) = Gson().fromJson<T>(dataHolder.getString(key),typeOfT)
    fun clear() = dataHolder.clear()

}