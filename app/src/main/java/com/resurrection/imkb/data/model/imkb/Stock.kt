package com.resurrection.imkb.data.model.imkb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "stock")
data class Stock(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("bid")
    val bid: Double,
    @SerializedName("difference")
    val difference: Double,
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