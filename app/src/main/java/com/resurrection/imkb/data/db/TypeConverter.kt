package com.resurrection.imkb.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.resurrection.imkb.data.model.ServiceStatus

class TypeConverter {

    @TypeConverter
    fun fromServiceStatusLangGson(value: ServiceStatus):String{
        val gson = Gson()
        val type = object : TypeToken<ServiceStatus>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toServiceLangList(value:String):ServiceStatus{
                val gson = Gson()
        val type = object : TypeToken<ServiceStatus>() {}.type
        return gson.fromJson(value, type)

    }
/*    @TypeConverter
    fun toDescriptionLangList(value: String): Description {
        val gson = Gson()
        val type = object : TypeToken<Description>() {}.type
        return gson.fromJson(value, type)
    }*/

}