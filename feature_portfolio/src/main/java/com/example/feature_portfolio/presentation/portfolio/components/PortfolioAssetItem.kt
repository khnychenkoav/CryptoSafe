package com.example.feature_portfolio.presentation.portfolio.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.model.PortfolioAsset


@Composable
fun PortfolioAssetItem(asset: PortfolioAsset, onRemoveClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // TODO: Загрузка картинки с помощью Coil

            Column {
                Text(text = asset.coin.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "${asset.amount} ${asset.coin.symbol.uppercase()}", style = MaterialTheme.typography.bodySmall)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(text = "$${String.format("%.2f", asset.coin.currentPrice * asset.amount)}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "$${String.format("%.2f", asset.coin.currentPrice)}", style = MaterialTheme.typography.bodySmall)
            }

            IconButton(onClick = onRemoveClick) {
                Icon(imageVector = androidx.compose.material.icons.Icons.Default.Delete, contentDescription = "Remove")
            }
        }
    }
}