package com.resurrection.imkb.ui.base.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.resurrection.imkb.ui.base.general.ThrowableError
import com.resurrection.imkb.ui.base.general.tryCatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    lateinit var lifecycleOwner: LifecycleOwner

    fun putString(key: String, value: String) = run{ dataStore.edit { it[stringPreferencesKey(key)] = value } }
    fun getString(key: String) = run { dataStore.data.first()[stringPreferencesKey(key)] }
    fun removeString(key: String) = run { dataStore.edit { it.remove(stringPreferencesKey(key)) } }

    fun putInt(key: String, value: Int) = run{ dataStore.edit { it[intPreferencesKey(key)] = value } }
    fun getInt(key: String) = run { dataStore.data.first()[intPreferencesKey(key)] }
    fun removeInt(key: String) = run { dataStore.edit { it.remove(intPreferencesKey(key)) } }

    fun putBoolean(key: String, value: Boolean) = run{ dataStore.edit { it[booleanPreferencesKey(key)] = value } }
    fun getBoolean(key: String) = run { dataStore.data.first()[booleanPreferencesKey(key)] }
    fun removeBoolean(key: String) = run { dataStore.edit { it.remove(booleanPreferencesKey(key)) } }

    fun putFloat(key: String, value: Float) = run { dataStore.edit { it[floatPreferencesKey(key)] = value } }
    fun getFloat(key: String) = run { dataStore.data.first()[floatPreferencesKey(key)] }
    fun removeFloat(key: String) = run { dataStore.edit { it.remove(floatPreferencesKey(key)) } }

    fun putLong(key: String, value: Long) = run{ dataStore.edit { it[longPreferencesKey(key)] = value } }
    fun getLong(key: String) = run { dataStore.data.first()[longPreferencesKey(key)] }
    fun removeLong(key: String) = run { dataStore.edit { it.remove(longPreferencesKey(key)) } }

    fun putSet(key: String, value: Set<String>) = run{ dataStore.edit { it[stringSetPreferencesKey(key)] = value } }
    fun getSet(key: String) = run { dataStore.data.first()[stringSetPreferencesKey(key)] }
    fun removeSet(key: String) = run { dataStore.edit { it.remove(stringSetPreferencesKey(key)) } }

    fun clearAll() = run { dataStore.edit { it.clear() } }

    private fun <T> run(block: suspend CoroutineScope.() -> T?) = lifecycleOwner.tryCatch <T>{ block() }
}


