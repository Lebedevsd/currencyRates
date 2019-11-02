package com.lebedevsd.currencyrates.di.modules.activities

import com.lebedevsd.currencyrates.MainActivity
import com.lebedevsd.currencyrates.di.PerActivity
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainViewModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @Module(
        includes = [CurrencyListFragmentModule::class]
    )
    internal abstract class MainViewModule
}