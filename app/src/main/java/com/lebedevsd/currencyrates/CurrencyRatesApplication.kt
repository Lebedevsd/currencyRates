package com.lebedevsd.currencyrates

import com.lebedevsd.currencyrates.di.modules.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class CurrencyRatesApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().create(this)
    }
}
