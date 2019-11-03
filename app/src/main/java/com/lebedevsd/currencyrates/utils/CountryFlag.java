package com.lebedevsd.currencyrates.utils;

import android.text.TextUtils;

import java.util.Locale;

public class CountryFlag {

    public static String flag(Locale locale) {
        return CountryFlag.flag(locale.getCountry().toLowerCase());
    }

    public static String flag(String iso) {
        if (TextUtils.isEmpty(iso)) {
            return "_unknown";
        }
        if (iso.length() > 3) {
            iso = iso.substring(0, 3);
        }
        return "flag_" + iso.toLowerCase();
    }
}