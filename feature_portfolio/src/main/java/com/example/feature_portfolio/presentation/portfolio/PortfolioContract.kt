package com.example.feature_portfolio.presentation.portfolio

import com.example.domain.model.Coin
import com.example.domain.model.PortfolioAsset

data class PortfolioState(
    val isLoading: Boolean = false,
    val portfolio: List<PortfolioAsset> = emptyList(),
    val availableCoins: List<Coin> = emptyList(),
    val error: String? = null,
    val totalPortfolioValue: Double = 0.0
)

sealed interface PortfolioEvent {
    data class AddAsset(val coin: Coin, val amount: Double) : PortfolioEvent
    data class RemoveAsset(val assetId: String) : PortfolioEvent
    object Refresh : PortfolioEvent
}