package com.lebedevsd.currencyrates.di.modules

import android.app.Application
import com.lebedevsd.currencyrates.CurrencyRatesApplication
import com.lebedevsd.currencyrates.di.ViewModelAssistedFactoriesModule
import com.lebedevsd.currencyrates.di.modules.activities.MainActivityModule
import dagger.BindsInstance
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

    override fun inject(app: CurrencyRatesApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
//    fun  provideConnectivityLiveData(): ConnectivityLiveData
//
//    @Component.Builder
//    abstract class Builder : AndroidInjector.Builder<CurrencyRatesApplication>()
}
