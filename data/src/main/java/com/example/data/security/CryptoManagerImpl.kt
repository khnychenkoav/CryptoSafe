package com.example.data.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.example.domain.security.CryptoManager
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

class CryptoManagerImpl @Inject constructor() : CryptoManager {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")

    private fun getOrCreateSecretKey(): SecretKey {
        val alias = "portfolio_key"
        if (keyStore.containsAlias(alias)) {
            val secretKeyEntry = keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry
            return secretKeyEntry.secretKey
        }

        val keyGenSpec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .setKeySize(256)
            .setUserAuthenticationRequired(false)
            .build()

        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        keyGenerator.init(keyGenSpec)
        return keyGenerator.generateKey()
    }

    override fun encrypt(data: ByteArray): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateSecretKey())
        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(data)
        return iv + encryptedBytes
    }

    override fun decrypt(encryptedData: ByteArray): ByteArray {
        val iv = encryptedData.copyOfRange(0, cipher.blockSize)
        val encryptedBytes = encryptedData.copyOfRange(cipher.blockSize, encryptedData.size)

        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, getOrCreateSecretKey(), ivSpec)
        return cipher.doFinal(encryptedBytes)
    }
}