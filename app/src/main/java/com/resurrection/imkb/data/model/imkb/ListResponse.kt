package com.resurrection.imkb.data.model.imkb

import com.google.gson.annotations.SerializedName
import com.resurrection.imkb.data.model.ServiceStatus

data class ListResponse(
    @SerializedName("status")
    val status: ServiceStatus,
    @SerializedName("stocks")
    val stocks: List<Stock>
)