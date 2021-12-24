package com.resurrection.imkb.util

import android.content.Context
import androidx.datastore.preferences.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.resurrection.imkb.App
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import java.lang.reflect.Type

class DataStoreHelper {

    private val dataStore = App.activity!!.createDataStore((App.context!!).packageName)

    fun insertDataStore(key: String, value: Any) =
        runLifeCycleScope { dataStore.edit { it[preferencesKey<String>(key)] = Gson().toJson(value) } }

    fun <T> getDataStore(key: String, typeOfT: Type?,function: (T) -> Unit) =
        runLifeCycleScope { function((Gson().fromJson(dataStore.data.first()[preferencesKey<String>(key)], typeOfT) as T)) }

    fun removeDataStore(key: String) =
        runLifeCycleScope { dataStore.edit { it.remove(preferencesKey<String>(key)) } }

    fun clearDataStore() =
        runLifeCycleScope { dataStore.edit { it.clear() } }

    fun updateDataStore(key: String, value: String) =
        runLifeCycleScope { dataStore.edit { it[preferencesKey<String>(key)] = value } }
}