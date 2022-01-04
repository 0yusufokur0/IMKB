package com.resurrection.imkb.ui.base.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.lifecycle.LifecycleOwner
import com.resurrection.imkb.ui.base.general.tryCatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Created by Yusuf Okur on 01.04.2022
 **/

class DataStoreManager @Inject constructor(private val manager: DataStore<Preferences>):StorageManager<Preferences> {

    lateinit var lifecycleOwner: LifecycleOwner

    override fun putInt(key: String, value: Int) = run{ manager.edit { it[intPreferencesKey(key)] = value } }
    override fun getInt(key: String,defValue: Int) = run(defValue) { manager.data.first()[intPreferencesKey(key)] }

    fun removeInt(key: String) = run { manager.edit { it.remove(intPreferencesKey(key)) } }

    override fun putString(key: String, value: String) = run{ manager.edit { it[stringPreferencesKey(key)] = value } }
    override fun getString(key: String,defValue: String) = run(defValue) { manager.data.first()[stringPreferencesKey(key)] }
    fun removeString(key: String) = run { manager.edit { it.remove(stringPreferencesKey(key)) } }

    override fun putBoolean(key: String, value: Boolean) = run{ manager.edit { it[booleanPreferencesKey(key)] = value } }
    override fun getBoolean(key: String,defValue: Boolean) = run(defValue) { manager.data.first()[booleanPreferencesKey(key)] }
    fun removeBoolean(key: String) = run { manager.edit { it.remove(booleanPreferencesKey(key)) } }

    override fun putFloat(key: String, value: Float) = run { manager.edit { it[floatPreferencesKey(key)] = value } }
    override fun getFloat(key: String,defValue: Float) = run(defValue) { manager.data.first()[floatPreferencesKey(key)] }
    fun removeFloat(key: String) = run { manager.edit { it.remove(floatPreferencesKey(key)) } }

    override fun putLong(key: String, value: Long) = run{ manager.edit { it[longPreferencesKey(key)] = value } }
    override fun getLong(key: String,defValue: Long) = run(defValue) { manager.data.first()[longPreferencesKey(key)] }
    fun removeLong(key: String) = run { manager.edit { it.remove(longPreferencesKey(key)) } }

    override fun putDouble(key: String, value: Double) = run { manager.edit { it[doublePreferencesKey(key)] = value } }
    override fun getDouble(key: String, defValue: Double) = run(defValue) { manager.data.first()[doublePreferencesKey(key)] }
    fun removeDouble(key: String) = run { manager.edit { it.remove(doublePreferencesKey(key)) } }

    override fun clearAll() = run { manager.edit { it.clear() } }

    private fun <T> run(defValue:T?= null,block: suspend CoroutineScope.() -> T?) = lifecycleOwner.tryCatch(defValue) { block() }

}


