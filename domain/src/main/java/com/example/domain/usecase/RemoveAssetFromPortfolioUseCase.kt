package com.example.domain.usecase

import com.example.domain.repository.PortfolioRepository
import javax.inject.Inject

class RemoveAssetFromPortfolioUseCase @Inject constructor(
    private val portfolioRepository: PortfolioRepository
) {
    suspend operator fun invoke(assetId: String) {
        portfolioRepository.removeAsset(assetId)
    }
}