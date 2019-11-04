package com.lebedevsd.currencyrates.repository

import com.lebedevsd.currencyrates.api.model.CurrencyRatesResponse
import com.lebedevsd.githubviewer.api.CurrencyRatesApi
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Repository of currencyRates
 */
class CurrencyRatesRepository @Inject constructor(
    private val remoteApi: CurrencyRatesApi
) {
    /**
     * @Returns list of all currencies
     */
    fun getCurrencies(baseCurrency: String): Flowable<CurrencyRatesResponse> {
        return remoteApi.getRates(baseCurrency)
            .toFlowable()
    }
}