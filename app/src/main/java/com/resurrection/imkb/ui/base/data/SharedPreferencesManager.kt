package com.resurrection.imkb.ui.base.data

import android.content.Context
import android.os.Parcelable
import java.io.Serializable
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(context: Context) {
    private val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    fun remove(key: String) = editor.remove(key).apply()
    fun clear() = editor.clear().apply()

    fun putString(key: String, value: String) = editor.putString(key, value).apply()
    fun getString(key: String, defaultValue: String) = preferences.getString(key, defaultValue)

    fun putInt(key: String, value: Int) = editor.putInt(key, value).apply()
    fun getInt(key: String, defaultValue: Int) = preferences.getInt(key, defaultValue)

    fun putBoolean(key: String, value: Boolean) = editor.putBoolean(key, value).apply()
    fun getBoolean(key: String, defaultValue: Boolean) = preferences.getBoolean(key, defaultValue)

    fun putFloat(key: String, value: Float) = editor.putFloat(key, value).apply()
    fun getFloat(key: String, defaultValue: Float) = preferences.getFloat(key, defaultValue)

    fun putLong(key: String, value: Long) = editor.putLong(key, value).apply()
    fun getLong(key: String, defaultValue: Long) = preferences.getLong(key, defaultValue)

    fun putStringSet(key: String, value: Set<String>) = editor.putStringSet(key, value).apply()
    fun getStringSet(key: String, defaultValue: Set<String>) = preferences.getStringSet(key, defaultValue)

}