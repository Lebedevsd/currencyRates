package com.lebedevsd.currencyrates.di.modules

import android.app.Application
import android.content.Context
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import javax.inject.Singleton
import okhttp3.OkHttpClient

@Module
class AndroidModule {

    @Provides
    @Singleton
    internal fun providesContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun providesConnectivityState(application: Application): Flowable<Connectivity> {
        return ReactiveNetwork.observeNetworkConnectivity(application.applicationContext)
            .toFlowable(BackpressureStrategy.LATEST)
    }

    @Provides
    @Singleton
    internal fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    internal fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
}
