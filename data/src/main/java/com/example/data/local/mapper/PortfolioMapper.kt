package com.example.data.local.mapper

import android.util.Base64
import com.example.data.local.model.PortfolioAssetEntity
import com.example.domain.model.Coin
import com.example.domain.model.PortfolioAsset
import com.example.domain.security.CryptoManager
import java.nio.ByteBuffer

fun PortfolioAsset.toEntity(cryptoManager: CryptoManager): PortfolioAssetEntity {
    val amountBytes = ByteBuffer.allocate(Double.SIZE_BYTES).putDouble(this.amount).array()
    val encryptedAmountBytes = cryptoManager.encrypt(amountBytes)
    val encryptedAmountString = Base64.encodeToString(encryptedAmountBytes, Base64.DEFAULT)

    return PortfolioAssetEntity(
        id = this.coin.id,
        name = this.coin.name,
        symbol = this.coin.symbol,
        imageUrl = this.coin.imageUrl,
        encryptedAmount = encryptedAmountString
    )
}

fun PortfolioAssetEntity.toDomainModel(decryptedAmount: Double, coinDetails: Coin): PortfolioAsset {
    return PortfolioAsset(
        coin = coinDetails,
        amount = decryptedAmount
    )
}