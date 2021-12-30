package com.resurrection.imkb.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.resurrection.imkb.data.model.ServiceStatus
import com.resurrection.imkb.data.model.imkb.Graphic
import com.resurrection.imkb.ui.base.util.modelToString
import com.resurrection.imkb.ui.base.util.stringToModel
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

}