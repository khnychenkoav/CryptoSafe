package com.example.feature_portfolio.presentation.portfolio.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.domain.model.Coin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssetDialog(
    availableCoins: List<Coin>,
    onDismiss: () -> Unit,
    onAddAsset: (coin: Coin, amount: Double) -> Unit
) {
    var selectedCoin by remember { mutableStateOf<Coin?>(null) }
    var amountText by remember { mutableStateOf("") }
    val isAddButtonEnabled = selectedCoin != null && amountText.toDoubleOrNull() ?: 0.0 > 0.0

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Добавить актив", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = false,
                    onExpandedChange = {}
                ) {
                    OutlinedTextField(
                        value = selectedCoin?.name ?: "Выберите монету",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                }

                LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
                    items(availableCoins) { coin ->
                        ListItem(
                            headlineContent = { Text(coin.name) },
                            modifier = Modifier.clickable { selectedCoin = coin }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Количество") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Отмена")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            selectedCoin?.let { coin ->
                                onAddAsset(coin, amountText.toDouble())
                            }
                        },
                        enabled = isAddButtonEnabled
                    ) {
                        Text("Добавить")
                    }
                }
            }
        }
    }
}