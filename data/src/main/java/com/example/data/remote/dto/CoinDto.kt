package com.example.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinDto (
    @field:Json(name = "id") val id: String,
    @field:Json(name = "symbol") val symbol: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "image") val image: String,
    @field:Json(name = "current_price") val currentPrice: Number?,
    @field:Json(name = "price_change_percentage_24h") val priceChangePercentage24h: Number?,
    )