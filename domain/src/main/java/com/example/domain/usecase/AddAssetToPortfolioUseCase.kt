package com.example.domain.usecase

import com.example.domain.model.PortfolioAsset
import com.example.domain.repository.PortfolioRepository
import javax.inject.Inject

class AddAssetToPortfolioUseCase @Inject constructor(
    private val portfolioRepository: PortfolioRepository
) {
    suspend operator fun invoke(asset: PortfolioAsset) {
        portfolioRepository.addAsset(asset)
    }
}