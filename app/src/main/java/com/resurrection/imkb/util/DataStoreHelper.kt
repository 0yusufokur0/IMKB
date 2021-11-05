package com.resurrection.imkb.util

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
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

/*    suspend fun <T> dsGet(key: String,typeOfT: Type?): T? {
        var resultString =  dataStore.data.first()[preferencesKey<String>(key)]
        var result = Gson().fromJson(resultString,typeOfT)
        return result

    }*/

    suspend fun <T> dsGet(key: String, typeOfT: Type?): T {
        var resultString =  dataStore.data.first()[preferencesKey<String>(key)]

        val gson = Gson()
        return gson.fromJson(resultString, typeOfT)
    }


}