package com.lebedevsd.currencyrates.utils

import timber.log.Timber
import java.util.*
import javax.inject.Inject

class CurrencyUtils @Inject constructor() {

    var currencyLocaleMap: SortedMap<Currency, Locale>

    init {
        currencyLocaleMap = TreeMap(Comparator<Currency> { p0, p1 ->
            p0?.currencyCode?.compareTo(
                p1?.currencyCode ?: ""
            ) ?: 0
        })
        for (availableLocale in Locale.getAvailableLocales()) {
            try {
                val currency = Currency.getInstance(availableLocale)
                if (!currencyLocaleMap.containsKey(currency)) {
                    currencyLocaleMap[currency] = availableLocale
                }
            } catch (e: Exception) {
            }
        }
        Timber.d("${currencyLocaleMap}")
    }

    fun getFlagName(currency: Currency): String {
        return if (currency.currencyCode == "EUR") {
            "flag_eu"
        } else {
            CountryFlag.flag(
                (currencyLocaleMap[currency]
                    ?: Locale(currency.currencyCode)).country.toLowerCase()
            )
        }
    }

    fun getCurrencySymbol(currencyCode: String): Currency {
        return Currency.getInstance(currencyCode)
    }
}