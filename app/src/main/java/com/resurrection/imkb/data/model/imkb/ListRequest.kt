package com.resurrection.imkb.data.model.imkb

import com.google.gson.annotations.SerializedName

data class ListRequest(
    @SerializedName("period")
    val period: String
)