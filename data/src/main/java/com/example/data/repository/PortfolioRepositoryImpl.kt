package com.example.data.repository

import android.util.Base64
import com.example.data.local.dao.PortfolioDao
import com.example.data.local.mapper.toEntity
import com.example.domain.model.Coin
import com.example.domain.model.PortfolioAsset
import com.example.domain.repository.CoinRepository
import com.example.domain.repository.PortfolioRepository
import com.example.domain.security.CryptoManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.nio.ByteBuffer
import javax.inject.Inject

class PortfolioRepositoryImpl @Inject constructor(
    private val portfolioDao: PortfolioDao,
    private val cryptoManager: CryptoManager,
    private val coinRepository: CoinRepository
) : PortfolioRepository {

    override suspend fun addAsset(asset: PortfolioAsset) {
        val entity = asset.toEntity(cryptoManager)
        portfolioDao.insertAsset(entity)
    }

    override suspend fun removeAsset(assetId: String) {
        portfolioDao.deleteAsset(assetId)
    }

    override fun getPortfolio(): Flow<List<PortfolioAsset>> {
        return portfolioDao.getPortfolio().map { entities ->
            val remoteCoinsMap = coinRepository.getCoinList()
                .getOrNull()?.associateBy { it.id }.orEmpty()

            entities.mapNotNull { entity ->
                val amount = try {
                    val encryptedBytes = Base64.decode(entity.encryptedAmount, Base64.DEFAULT)
                    val decryptedBytes = cryptoManager.decrypt(encryptedBytes)
                    ByteBuffer.wrap(decryptedBytes).double
                } catch (e: Exception) {
                    e.printStackTrace()
                    return@mapNotNull null
                }

                val remoteCoin = remoteCoinsMap[entity.id]

                PortfolioAsset(
                    coin = Coin(
                        id = entity.id,
                        name = remoteCoin?.name ?: entity.name,
                        symbol = remoteCoin?.symbol ?: entity.symbol,
                        imageUrl = remoteCoin?.imageUrl ?: entity.imageUrl,
                        currentPrice = remoteCoin?.currentPrice ?: 0.0,
                        priceChangePercentage24h = remoteCoin?.priceChangePercentage24h ?: 0.0
                    ),
                    amount = amount
                )
            }
        }
    }
}