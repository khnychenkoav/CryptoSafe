package com.example.data.remote.mapper

import com.example.data.remote.dto.CoinDto
import com.example.domain.model.Coin

fun CoinDto.toDomain(): Coin {
    return Coin(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        imageUrl = this.image,
        currentPrice = this.currentPrice ?: 0.0,
        priceChangePercentage24h = this.priceChangePercentage24h ?: 0.0,
    )
}