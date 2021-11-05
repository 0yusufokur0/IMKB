package com.resurrection.imkb.util

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class DataStoreHelper(private val context: Context) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences> =
        applicationContext.createDataStore(context.packageName.toString())

    suspend fun <T> dsSave(key: String, value: T) {
        dataStore.edit { it[preferencesKey<String>(key)] = Gson().toJson(value) }
    }

    suspend fun <T> dsGet(key: String, typeOfT: Type?): T {
        var resultString =  dataStore.data.first()[preferencesKey<String>(key)]
        val gson = Gson()
        return gson.fromJson(resultString, typeOfT)
    }

    suspend fun remove(key: String){
        dataStore.edit { it.remove(preferencesKey<String>(key)) }
    }

}