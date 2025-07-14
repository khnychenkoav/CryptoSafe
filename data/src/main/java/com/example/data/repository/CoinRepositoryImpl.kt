package com.example.data.repository

import com.example.data.remote.api.CoinGeckoApiService
import com.example.data.remote.mapper.toDomain
import com.example.domain.model.Coin
import com.example.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val apiService: CoinGeckoApiService
) : CoinRepository {
    override suspend fun getCoinList() : Result<List<Coin>> {
        return try {
            val coinDtos = apiService.getCoinMarkets()
            val coins = coinDtos.map { it.toDomain() }
            Result.success(coins)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCoinListAsFlow(): Flow<Result<List<Coin>>> = flow {
        emit(getCoinList())
    }
}