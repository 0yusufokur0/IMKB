package com.resurrection.imkb.ui.base.data

import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.resurrection.imkb.ui.base.general.ThrowableError

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {
    lateinit var lifecycleOwner: LifecycleOwner

    fun insertDataStore(key: String, value: Any) =
        runLifeCycleScope { dataStore.edit { it[preferencesKey<String>(key)] = Gson().toJson(value) } }

    fun <T> getDataStore(key: String, typeOfT: Type?,function: (T) -> Unit) =
        runLifeCycleScope { function((Gson().fromJson(dataStore.data.first()[preferencesKey<String>(key)], typeOfT))) }

    fun removeDataStore(key: String) =
        runLifeCycleScope { dataStore.edit { it.remove(preferencesKey(key)) } }

    fun clearDataStore() = runLifeCycleScope { dataStore.edit { it.clear() } }


    private fun runLifeCycleScope(block: suspend CoroutineScope.() -> Unit): Job? =
        try {
            val job = lifecycleOwner.lifecycleScope.launch { block() }
            job
        } catch (e: Exception) {
            ThrowableError(e.toString())
            null
        }
}

