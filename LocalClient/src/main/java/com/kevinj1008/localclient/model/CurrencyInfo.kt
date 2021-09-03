package com.kevinj1008.localclient.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "currency_list")
data class CurrencyInfo(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id") val id: String,

    @ColumnInfo(name = "name")
    @SerializedName("name") val name: String,

    @ColumnInfo(name = "symbol")
    @SerializedName("symbol") val symbol: String
)