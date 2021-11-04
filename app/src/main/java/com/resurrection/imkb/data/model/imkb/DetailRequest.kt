package com.resurrection.imkb.data.model.imkb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class DetailRequest(
    @PrimaryKey
    @SerializedName("id")
    val id: String
)