package com.resurrection.imkb.ui.base.data

import android.content.Context
import com.resurrection.imkb.ui.base.util.modelToString
import com.resurrection.imkb.ui.base.util.stringToModel

class SharedPreferencesManager(context: Context) {
    private val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    fun put(key: String, value: Any) = editor.putString(key, modelToString(value)).apply()
    fun <T> get(key: String) = stringToModel<T>(preferences.getString(key, null)!!)
    fun remove(key: String) = editor.remove(key).apply()
    fun clear() = editor.clear().apply()
}