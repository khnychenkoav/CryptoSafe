package com.example.data.remote.api

import com.example.data.remote.dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApiService {

    @GET("coins/markets")
    suspend fun getCoinMarkets(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false,
    ): List<CoinDto>


}