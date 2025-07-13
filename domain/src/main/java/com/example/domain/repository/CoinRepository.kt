package com.example.domain.repository

import com.example.domain.model.Coin

interface CoinRepository {
    suspend fun getCoinList(): Result<List<Coin>>
}