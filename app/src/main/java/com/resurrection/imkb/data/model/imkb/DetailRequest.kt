package com.resurrection.imkb.data.model.imkb

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class DetailRequest(
    @PrimaryKey
    @SerializedName("id")
    val id: String
)