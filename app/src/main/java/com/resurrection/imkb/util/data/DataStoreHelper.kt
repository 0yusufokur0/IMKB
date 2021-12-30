package com.resurrection.imkb.util.data

import android.app.Activity
import androidx.datastore.preferences.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.resurrection.imkb.util.general.ThrowableError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class DataStoreHelper(private val activity: Activity) {

    private val dataStore = activity.createDataStore(activity.packageName)

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

    private fun runLifeCycleScope(block: suspend CoroutineScope.() -> Unit): Job? =
        try {
            val job = (activity as LifecycleOwner).lifecycleScope.launch { block() }
            job
        } catch (e: Exception) {
            ThrowableError(e.toString())
            null
        }
}