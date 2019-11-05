package com.lebedevsd.currencyrates.api

import com.lebedevsd.currencyrates.api.model.CurrencyRatesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Provides CurrencyRates entities from network API
 */
interface CurrencyRatesRestApi {

    /**
     * Performs network call to fetch latest currency update
     */
    @GET("latest")
    fun fetchLatestCurrencyRates(@QueryMap options: Map<String, String>): Call<CurrencyRatesResponse>

    companion object {
        /**
         * EndPoint to receive currency rates
         */
        val ENDPOINT = "https://revolut.duckdns.org/"
        val BASE_CURRENCY_PARAM_KEY = "base"
    }
}
