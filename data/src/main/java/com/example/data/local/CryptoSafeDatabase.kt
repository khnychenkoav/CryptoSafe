package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.PortfolioDao
import com.example.data.local.model.PortfolioAssetEntity

@Database(
    entities = [PortfolioAssetEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CryptoSafeDatabase : RoomDatabase() {
    abstract fun portfolioDao(): PortfolioDao
}