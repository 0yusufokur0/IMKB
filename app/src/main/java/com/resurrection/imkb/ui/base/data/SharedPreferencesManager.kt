package com.resurrection.imkb.ui.base.data

import android.content.SharedPreferences
import com.resurrection.imkb.ui.base.util.modelToString
import com.resurrection.imkb.ui.base.util.stringToModel
import java.lang.reflect.Type
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(private val preferences: SharedPreferences) {

    private val editor = preferences.edit()

    fun <T>get(key: String,typeOfT: Type?) = stringToModel<T>(preferences.getString(key, null)!!)
    fun insert(key: String,value: Any) = editor.putString(key, modelToString(value)).apply()
    fun remove(key: String) = editor.remove(key).apply()
    fun clear() = editor.clear().apply()
    fun update(key: String,value: String) =editor.putString(key, value).apply()
}