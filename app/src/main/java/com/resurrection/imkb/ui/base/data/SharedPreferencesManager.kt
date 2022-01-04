package com.resurrection.imkb.ui.base.data

import android.content.Context
import javax.inject.Inject

/**
 * Created by Yusuf Okur on 01.04.2022
 **/

class SharedPreferencesManager @Inject constructor(context: Context) : StorageManager<Unit> {
    val manager = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    private val editor = manager.edit()

    fun remove(key: String) = editor.remove(key).apply()
    fun size() = manager.all.size
    fun isEmpty() = manager.all.isEmpty()
    fun keySet() = manager.all.keys
    fun contains(key: String) = manager.all.containsKey(key)
    fun get(key: String) = manager.all[key]


    override fun clearAll() = editor.clear().apply()

    override fun putString(key: String, value: String) = editor.putString(key, value).apply()
    override fun getString(key: String, defValue: String) = manager.getString(key, defValue)

    override fun putInt(key: String, value: Int) = editor.putInt(key, value).apply()
    override fun getInt(key: String, defValue: Int) = manager.getInt(key, defValue)

    override fun putBoolean(key: String, value: Boolean) = editor.putBoolean(key, value).apply()
    override fun getBoolean(key: String, defValue: Boolean) = manager.getBoolean(key, defValue)

    override fun putFloat(key: String, value: Float) = editor.putFloat(key, value).apply()
    override fun getFloat(key: String, defValue: Float) = manager.getFloat(key, defValue)

    override fun putLong(key: String, value: Long) = editor.putLong(key, value).apply()
    override fun getLong(key: String, defValue: Long) = manager.getLong(key, defValue)

    override fun putDouble(key: String, value: Double) = editor.putString(key, value.toString()).apply()
    override fun getDouble(key: String, defValue: Double) = manager.getString(key, defValue.toString())?.toDouble()


}