package com.resurrection.imkb.data.model.handshake

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.resurrection.imkb.data.model.ServiceStatus
import java.io.Serializable

@Entity(tableName = "handshake_response")
data class HandshakeResponse(
    @PrimaryKey
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