package com.resurrection.imkb.ui.base.data

/**
 * Created by Yusuf Okur on 01.04.2022
 **/

interface StorageManager <T> {
   fun putInt(key: String, value: Int):T?
   fun getInt(key: String,defValue: Int): Int?
   fun putString(key: String, value: String):T?
   fun getString(key: String,defValue: String): String?
   fun putBoolean(key: String, value: Boolean):T?
   fun getBoolean(key: String,defValue: Boolean): Boolean?
   fun putFloat(key: String, value: Float):T?
   fun getFloat(key: String,defValue: Float): Float?
   fun putLong(key: String, value: Long):T?
   fun getLong(key: String,defValue: Long): Long?
   fun putDouble(key: String, value: Double):T?
   fun getDouble(key: String,defValue: Double): Double?
   fun clearAll():T?
}
