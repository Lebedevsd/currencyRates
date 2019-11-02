package com.lebedevsd.currencyrates.di.modules

import com.lebedevsd.currencyrates.CurrencyRatesApplication
import com.lebedevsd.currencyrates.di.ViewModelAssistedFactoriesModule
import com.lebedevsd.currencyrates.di.modules.activities.MainActivityModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AndroidModule::class,
    ViewModelAssistedFactoriesModule::class,
    MainActivityModule::class
])
interface ApplicationComponent : AndroidInjector<CurrencyRatesApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<CurrencyRatesApplication>()
}
