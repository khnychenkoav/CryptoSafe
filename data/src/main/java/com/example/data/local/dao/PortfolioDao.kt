package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.model.PortfolioAssetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PortfolioDao {
    @Query("SELECT * FROM portfolio_assets")
    fun getPortfolio(): Flow<List<PortfolioAssetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(asset: PortfolioAssetEntity)

    @Query("DELETE FROM portfolio_assets WHERE id = :assetId")
    suspend fun deleteAsset(assetId: String)

}