package com.lebedevsd.currencyrates.utils

import android.text.TextUtils

object CountryFlag {

    fun flag(iso: String): String {
        var iso = iso
        if (TextUtils.isEmpty(iso)) {
            return "_unknown"
        }
        if (iso.length > 3) {
            iso = iso.substring(0, 3)
        }
        return "flag_" + iso.toLowerCase()
    }
}
