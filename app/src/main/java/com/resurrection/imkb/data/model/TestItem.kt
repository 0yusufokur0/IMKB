package com.resurrection.imkb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "item")
data class TestItem(
    @PrimaryKey
    @SerializedName("id")
    val id: String

)