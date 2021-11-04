package com.resurrection.imkb.data.model

import com.google.gson.annotations.SerializedName

data class ServiceStatus(
    @SerializedName("error")
    val error: ServiceError,
    @SerializedName("isSuccess")
    val isSuccess: Boolean
)