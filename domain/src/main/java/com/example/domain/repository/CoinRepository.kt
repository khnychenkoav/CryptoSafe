package com.example.domain.repository

import com.example.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getCoinList(): Result<List<Coin>>
    fun getCoinListAsFlow(): Flow<Result<List<Coin>>>
}