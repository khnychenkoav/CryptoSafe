package com.example.domain.usecase

import com.example.domain.model.Coin
import com.example.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinListUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    suspend operator fun invoke(): Result<List<Coin>> {
        return coinRepository.getCoinList()
    }

}