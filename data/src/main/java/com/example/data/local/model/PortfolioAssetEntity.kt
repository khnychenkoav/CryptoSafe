package com.example.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "portfolio_assets")
data class PortfolioAssetEntity (
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val encryptedAmount: String,

)