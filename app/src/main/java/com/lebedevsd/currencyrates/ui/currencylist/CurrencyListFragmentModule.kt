package com.lebedevsd.currencyrates.ui.currencylist

import com.lebedevsd.currencyrates.di.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CurrencyListFragmentModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [CurrencyListModule::class])
    abstract fun contributeCurrencyListFragmentInjector(): CurrencyListFragment
}