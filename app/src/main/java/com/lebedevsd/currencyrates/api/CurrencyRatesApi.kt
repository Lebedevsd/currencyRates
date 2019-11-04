package com.lebedevsd.githubviewer.api

import com.lebedevsd.currencyrates.api.CurrencyRatesRestApi
import com.lebedevsd.currencyrates.api.model.CurrencyRatesResponse
import com.squareup.moshi.Moshi
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Provides currency list content from network API
 */
@Singleton
class CurrencyRatesApi @Inject constructor(
    moshi: Moshi,
    okHttpClient: OkHttpClient
) {
    private val service: CurrencyRatesRestApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(CurrencyRatesRestApi.ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        service = retrofit.create(CurrencyRatesRestApi::class.java)
    }

    /**
     * Performs network call to get Rates
     */
    fun getRates(baseCurrency: String): Single<CurrencyRatesResponse> {
        return Single.create { emitter ->
            val params: Map<String, String> = if (baseCurrency.isEmpty()) {
                emptyMap()
            } else {
                mapOf(CurrencyRatesRestApi.BASE_CURRENCY_PARAM_KEY to baseCurrency)
            }
            val response = service.fetchLatestCurrencyRates(params).execute()

            if (response.isSuccessful) {
                response.body()?.let { emitter.onSuccess(it) }
            } else {
                emitter.onError(RuntimeException(response.message()))
            }
        }
    }
}
