package com.resurrection.imkb.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.resurrection.imkb.data.model.ServiceStatus
import com.resurrection.imkb.data.model.imkb.DetailResponse
import com.resurrection.imkb.data.model.imkb.Graphic

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

    @TypeConverter
    fun fromDetailResponseStatusLangGson(value: List<Graphic>):String{
        val gson = Gson()
        val type = object : TypeToken<List<Graphic>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toDetailResponseLangList(value:String):List<Graphic>{
        val gson = Gson()
        val type = object : TypeToken<List<Graphic>>() {}.type
        return gson.fromJson(value, type)
    }


}