package com.example.feature_portfolio.presentation.portfolio

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.feature_portfolio.R
import com.example.feature_portfolio.presentation.portfolio.components.AddAssetDialog
import com.example.feature_portfolio.presentation.portfolio.components.PortfolioAssetItem
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PortfolioScreen(
    viewModel: PortfolioViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    if (showAddDialog) {
        AddAssetDialog(
            availableCoins = state.availableCoins,
            onDismiss = { showAddDialog = false },
            onAddAsset = { coin, amount ->
                viewModel.onEvent(PortfolioEvent.AddAsset(coin, amount))
                showAddDialog = false
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(imageVector = androidx.compose.material.icons.Icons.Default.Add, contentDescription = stringResource(R.string.portfolio_add_asset_button_description))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            val currentError = state.error
            if (currentError != null) {
                Text(
                    text = stringResource(R.string.portfolio_error_message, currentError),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            TotalValueCard(totalValue = state.totalPortfolioValue)

            Spacer(modifier = Modifier.height(16.dp))

            if (state.portfolio.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.portfolio_empty_state_message),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(state.portfolio, key = { it.coin.id }) { asset ->
                        PortfolioAssetItem(
                            asset = asset,
                            onRemoveClick = { viewModel.onEvent(PortfolioEvent.RemoveAsset(asset.coin.id)) },
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun TotalValueCard(totalValue: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.portfolio_total_value_title), style = MaterialTheme.typography.titleMedium)
            AnimatedContent(
                targetState = totalValue,
                transitionSpec = {
                    slideInVertically { height -> height } togetherWith
                            slideOutVertically { height -> -height }
                },
                label = "totalValueAnimation"
            ) { targetValue ->
                Text(
                    text = "$${String.format(Locale.US, "%.2f", targetValue)}",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}
