package com.lebedevsd.currencyrates.interactor

import com.lebedevsd.currencyrates.api.model.CurrencyRatesResponse
import com.lebedevsd.currencyrates.repository.CurrencyRatesRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * UseCase that provides Currency rates for specified base currency
 */
class GetCurrencyRatesInteractor
@Inject constructor(private val repository: CurrencyRatesRepository) {

    /**
     * @returns get currencies response
     */
    operator fun invoke(baseCurrency: String = "") = getCurrencies(baseCurrency)

    private fun getCurrencies(baseCurrency: String): Flowable<CurrencyRatesResponse> {
        return repository.getCurrencies(baseCurrency)
    }
}