package com.example.domain.usecase

import com.example.domain.model.PortfolioAsset
import com.example.domain.repository.PortfolioRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPortfolioUseCase @Inject constructor(
    private val portfolioRepository: PortfolioRepository
) {
    operator fun invoke(): Flow<List<PortfolioAsset>> {
        return portfolioRepository.getPortfolio()
    }
}
