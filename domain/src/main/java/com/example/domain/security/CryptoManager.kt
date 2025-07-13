package com.example.domain.security

interface CryptoManager {
    fun encrypt(data: ByteArray): ByteArray
    fun decrypt(encryptedData: ByteArray): ByteArray
}