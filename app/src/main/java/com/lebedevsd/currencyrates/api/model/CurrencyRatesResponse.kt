package com.lebedevsd.currencyrates.api.model

data class CurrencyRatesResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)