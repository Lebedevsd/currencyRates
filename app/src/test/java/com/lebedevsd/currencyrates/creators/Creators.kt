package com.lebedevsd.currencyrates.creators

import com.lebedevsd.currencyrates.api.model.CurrencyRatesResponse
import com.lebedevsd.currencyrates.base.mvi.ViewStateErrorEvent
import com.lebedevsd.currencyrates.base.mvi.ViewStateOfflineEvent
import com.lebedevsd.currencyrates.ui.base.FlagNameImage
import com.lebedevsd.currencyrates.ui.base.Image
import com.lebedevsd.currencyrates.ui.currency.CurrencyPresentationModel
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListState

fun dummyCurrencyRatesResponse(
    base: String = "EUR",
    date: String = "",
    rates: Map<String, Double> = mapOf("USD" to 0.75)
) =
    CurrencyRatesResponse(base, date, rates)

fun dummyCurrencyPresentationModel(
    title: String = "EUR",
    description: String = "Euro",
    value: Double = 1.0,
    exchangeRate: Double = 1.0,
    flagImage: Image = FlagNameImage("EUR")
) = CurrencyPresentationModel(
    title = title,
    description = description,
    value = value,
    exchangeRate = exchangeRate,
    flagImage = flagImage
)

fun dummyCurrencyListState(
    currencies: List<CurrencyPresentationModel> = listOf(
        dummyCurrencyPresentationModel(),
        dummyCurrencyPresentationModel(title = "USD")
    ),
    selectedCurrency: String = "EUR",
    selectedValue: Double = 1.0,
    error: ViewStateErrorEvent? = null,
    offlineEvent: ViewStateOfflineEvent? = null,
    isOnline: Boolean = true
) = CurrencyListState(
    currencies = currencies,
    selectedCurrency = selectedCurrency,
    selectedValue = selectedValue,
    error = error,
    offlineEvent = offlineEvent,
    isOnline = isOnline
)