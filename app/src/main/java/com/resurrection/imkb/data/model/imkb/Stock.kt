package com.resurrection.imkb.data.model.imkb

import com.google.gson.annotations.SerializedName

data class Stock(
    @SerializedName("bid")
    val bid: Double,
    @SerializedName("difference")
    val difference: Double,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isDown")
    val isDown: Boolean,
    @SerializedName("isUp")
    val isUp: Boolean,
    @SerializedName("offer")
    val offer: Double,
    @SerializedName("price")
    val price: Double,
    @SerializedName("symbol")
    var symbol: String,
    @SerializedName("volume")
    val volume: Double
)