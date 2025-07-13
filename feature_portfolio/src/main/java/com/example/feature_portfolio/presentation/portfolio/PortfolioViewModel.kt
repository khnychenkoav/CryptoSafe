package com.example.feature_portfolio.presentation.portfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Coin
import com.example.domain.model.PortfolioAsset
import com.example.domain.usecase.AddAssetToPortfolioUseCase
import com.example.domain.usecase.GetCoinListUseCase
import com.example.domain.usecase.GetPortfolioUseCase
import com.example.domain.usecase.RemoveAssetFromPortfolioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getCoinListUseCase: GetCoinListUseCase,
    private val getPortfolioUseCase: GetPortfolioUseCase,
    private val addAssetToPortfolioUseCase: AddAssetToPortfolioUseCase,
    private val removeAssetFromPortfolioUseCase: RemoveAssetFromPortfolioUseCase
    ) : ViewModel() {

        private val _state = MutableStateFlow(PortfolioState())
        val state: StateFlow<PortfolioState> = _state.asStateFlow()

        init {
            loadAvailableCoins()
            observePortfolio()
        }

    fun onEvent(event: PortfolioEvent) {
        when (event) {
            is PortfolioEvent.AddAsset -> addAsset(event.coin, event.amount)
            is PortfolioEvent.RemoveAsset -> removeAsset(event.assetId)
            PortfolioEvent.Refresh -> loadAvailableCoins()
        }
    }


    private fun loadAvailableCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getCoinListUseCase()
                .onSuccess { coins ->
                    _state.update { it.copy(isLoading = false, availableCoins = coins) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    private fun observePortfolio() {
        getPortfolioUseCase()
            .onEach { portfolio ->
                val totalValue = portfolio.sumOf { it.coin.currentPrice * it.amount }
                _state.update {
                    it.copy(portfolio = portfolio, totalPortfolioValue = totalValue)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun addAsset(coin: Coin, amount: Double) {
        viewModelScope.launch {
            val asset = PortfolioAsset(coin = coin, amount = amount)
            addAssetToPortfolioUseCase(asset)
        }
    }

    private fun removeAsset(assetId: String) {
        viewModelScope.launch {
            removeAssetFromPortfolioUseCase(assetId)
        }
    }
}