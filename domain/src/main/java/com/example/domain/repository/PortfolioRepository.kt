package com.example.domain.repository

import com.example.domain.model.PortfolioAsset
import kotlinx.coroutines.flow.Flow

interface PortfolioRepository {
    suspend fun addAsset(asset: PortfolioAsset)

    suspend fun removeAsset(assetId: String)

    fun getPortfolio(): Flow<List<PortfolioAsset>>
}
