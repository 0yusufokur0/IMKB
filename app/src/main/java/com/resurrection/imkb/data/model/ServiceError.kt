package com.resurrection.imkb.data.model

import com.google.gson.annotations.SerializedName

data class ServiceError(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)