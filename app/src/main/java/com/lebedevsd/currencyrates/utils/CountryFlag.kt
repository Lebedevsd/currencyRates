package com.lebedevsd.currencyrates.utils

import android.annotation.SuppressLint
import android.text.TextUtils

object CountryFlag {

    @SuppressLint("DefaultLocale")
    fun flag(iso: String): String {
        if (TextUtils.isEmpty(iso)) {
            return "_unknown"
        }

        return "flag_" + (if (iso.length > 3) {
            iso.substring(0, 3)
        } else iso).toLowerCase()
    }
}
