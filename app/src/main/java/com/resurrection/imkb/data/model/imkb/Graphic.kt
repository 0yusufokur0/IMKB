package com.resurrection.imkb.data.model.imkb

import com.google.gson.annotations.SerializedName

data class Graphic(
    @SerializedName("day")
    val day: Int,
    @SerializedName("value")
    val value: Double
)