package com.lebedevsd.currencyrates.creators

import com.lebedevsd.currencyrates.api.model.CurrencyRatesResponse

fun dummyCurrencyRatesResponse(
    base: String = "",
    date: String = "",
    rates: Map<String, Double> = emptyMap()
) =
    CurrencyRatesResponse(base, date, rates)