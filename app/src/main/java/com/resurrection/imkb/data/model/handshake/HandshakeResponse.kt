package com.resurrection.imkb.data.model.handshake

import com.google.gson.annotations.SerializedName
import com.resurrection.imkb.data.model.ServiceStatus
import java.io.Serializable

data class HandshakeResponse(
    @SerializedName("aesIV")
    val aesIV: String,
    @SerializedName("aesKey")
    val aesKey: String,
    @SerializedName("authorization")
    val authorization: String,
    @SerializedName("lifeTime")
    val lifeTime: String,
    @SerializedName("status")
    val status: ServiceStatus
):Serializable