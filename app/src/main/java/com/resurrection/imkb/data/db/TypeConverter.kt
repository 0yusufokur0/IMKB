package com.resurrection.imkb.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.resurrection.imkb.data.model.ServiceStatus
import com.resurrection.imkb.data.model.imkb.Graphic
import java.lang.reflect.Type

class TypeConverter {
    @TypeConverter
    fun fromServiceStatusLangGson(value: ServiceStatus) = modelToString(value)
    @TypeConverter
    fun toServiceLangList(value: String) = stringToModel<ServiceStatus>(value)
    @TypeConverter
    fun fromDetailResponseStatusLangGson(value: List<Graphic>) = modelToString(value)
    @TypeConverter
    fun toDetailResponseLangList(value: String) = stringToModel<List<Graphic>>(value)
    fun <T> stringToModel(value: String) = Gson().fromJson(value, object : TypeToken<T>() {}.type) as T
    fun <T> modelToString(value: T) = Gson().toJson(value, object : TypeToken<T>() {}.type) as String
}