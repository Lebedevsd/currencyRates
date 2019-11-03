package com.lebedevsd.currencyrates.utils

import java.util.*

class CurrencyUtils {

    companion object {
        fun getCurrencySymbol(currencyCode: String): String {
            val currency = Currency.getInstance(currencyCode)
            return currency.displayName
        }
    }
}