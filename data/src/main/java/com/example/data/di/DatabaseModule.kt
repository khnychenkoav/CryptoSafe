package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.CryptoSafeDatabase
import com.example.data.local.dao.PortfolioDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCryptoSafeDatabase(
        @ApplicationContext context: Context
    ): CryptoSafeDatabase {
        return Room.databaseBuilder(
            context,
            CryptoSafeDatabase::class.java,
            "cryptosafe_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePortfolioDao(database: CryptoSafeDatabase) : PortfolioDao {
        return database.portfolioDao()
    }
}