package com.lebedevsd.currencyrates.creators

import com.lebedevsd.currencyrates.api.model.CurrencyRatesResponse

fun dummyCurrencyRatesResponse(
    base: String = "EUR",
    date: String = "",
    rates: Map<String, Double> = mapOf("USD" to 0.75)
) =
    CurrencyRatesResponse(base, date, rates)