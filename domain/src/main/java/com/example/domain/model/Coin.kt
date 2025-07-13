package com.example.domain.model

data class Coin (
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val currentPrice: Double,
    val priceChangePercentage24h: Double,
)