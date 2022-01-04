package com.resurrection.imkb.ui.base.data

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import javax.inject.Inject

/**
* Created by Yusuf Okur on 01.04.2022
**/

class DataHolderManager @Inject constructor(val manager: Bundle) : StorageManager<Unit> {

    fun remove(key: String) = manager.remove(key)
    fun containsKey(key: String) = manager.containsKey(key)
    fun size() = manager.size()
    fun isEmpty() = manager.isEmpty
    fun keySet() = manager.keySet()
    fun get(key: String) = manager.get(key)

    fun putBundle(key: String, value: Bundle) = manager.putBundle(key, value)
    fun getBundle(key: String) = manager.getBundle(key)

    override fun clearAll() = manager.clear()

    override fun putInt(key: String, value: Int) = manager.putInt(key, value)
    override fun getInt(key: String, defValue: Int) = manager.getInt(key, defValue)

    override fun putString(key: String, value: String) = manager.putString(key, value)
    override fun getString(key: String, defValue: String) = manager.getString(key, defValue)

    override fun putBoolean(key: String, value: Boolean) = manager.putBoolean(key, value)
    override fun getBoolean(key: String, defValue: Boolean) = manager.getBoolean(key, defValue)

    override fun putFloat(key: String, value: Float) = manager.putFloat(key, value)
    override fun getFloat(key: String, defValue: Float) = manager.getFloat(key, defValue)

    override fun putDouble(key: String, value: Double) = manager.putDouble(key, value)
    override fun getDouble(key: String, defValue: Double) = manager.getDouble(key, defValue)

    override fun putLong(key: String, value: Long) = manager.putLong(key, value)
    override fun getLong(key: String, defValue: Long) = manager.getLong(key, defValue)

    fun putSerializable(key: String, value: Serializable) = manager.putSerializable(key, value)
    fun <R:Serializable>getSerializable(key: String) = manager.getSerializable(key) as R

    fun putParcelable(key: String, value: Parcelable) = manager.putParcelable(key, value)
    fun <R : Parcelable> getParcelable(key: String) = manager.getParcelable<R>(key)

    //region Array List
    fun putIntegerArrayList(key: String, value: ArrayList<Int>) = manager.putIntegerArrayList(key, value)
    fun getIntegerArrayList(key: String) = manager.getIntegerArrayList(key)

    fun putStringArrayList(key: String, value: ArrayList<String>) = manager.putStringArrayList(key, value)
    fun getStringArrayList(key: String) = manager.getStringArrayList(key)

    fun putParcelableArrayList(key: String, value: ArrayList<out Parcelable?>) = manager.putParcelableArrayList(key, value)
    fun <T : Parcelable> getParcelableArrayList(key: String) = manager.getParcelableArrayList<T>(key)
    // endregion

    //region Array
    fun putIntArray(key: String, value: IntArray) = manager.putIntArray(key, value)
    fun getIntArray(key: String) = manager.getIntArray(key)

    fun getShort(key: String, defValue: Short) = manager.getShort(key, defValue)
    fun putShort(key: String, value: Short) = manager.putShort(key, value)

    fun getByteArray(key: String) = manager.getByteArray(key)
    fun putByteArray(key: String, value: ByteArray) = manager.putByteArray(key, value)

    fun getCharArray(key: String) = manager.getCharArray(key)
    fun putCharArray(key: String, value: CharArray) = manager.putCharArray(key, value)

    fun getDoubleArray(key: String) = manager.getDoubleArray(key)
    fun putDoubleArray(key: String, value: DoubleArray) = manager.putDoubleArray(key, value)

    fun getShortArray(key: String) = manager.getShortArray(key)
    fun putShortArray(key: String, value: ShortArray) = manager.putShortArray(key, value)

    fun getCharSequenceArray(key: String) = manager.getCharSequenceArray(key)
    fun putCharSequenceArray(key: String, value: Array<CharSequence?>) = manager.putCharSequenceArray(key, value)

    fun getStringArray(key: String) = manager.getStringArray(key)
    fun putStringArray(key: String, value: Array<String>) = manager.putStringArray(key, value)

    fun getParcelableArray(key: String) = manager.getParcelableArray(key)
    fun putParcelableArray(key: String, value: Array<Parcelable?>) = manager.putParcelableArray(key, value)

    fun getBooleanArray(key: String) = manager.getBooleanArray(key)
    fun putBooleanArray(key: String, value: BooleanArray) = manager.putBooleanArray(key, value)

    fun getFloatArray(key: String) = manager.getFloatArray(key)
    fun putFloatArray(key: String, value: FloatArray) = manager.putFloatArray(key, value)

    fun getLongArray(key: String) = manager.getLongArray(key)
    fun putLongArray(key: String, value: LongArray) = manager.putLongArray(key, value)


    //endregion

}
