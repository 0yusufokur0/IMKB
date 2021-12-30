package com.resurrection.imkb.util.data

import android.content.SharedPreferences
import com.google.gson.Gson
import java.lang.reflect.Type
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(private val preferences: SharedPreferences) {

    private val editor = preferences.edit()

    fun <T>get(key: String,typeOfT: Type?) = Gson().fromJson(preferences.getString(key, null), typeOfT) as T
    fun insert(key: String,value: Any) = editor.putString(key, Gson().toJson(value)).apply()
    fun remove(key: String) = editor.remove(key).apply()
    fun clear() = editor.clear().apply()
    fun update(key: String,value: String) =editor.putString(key, value).apply()
}