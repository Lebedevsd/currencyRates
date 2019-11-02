package com.lebedevsd.currencyrates.di.modules

import android.app.Application
import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class AndroidModule {

    @Provides
    @Singleton
    internal fun providesContext(application: Application): Context {
        return application
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
