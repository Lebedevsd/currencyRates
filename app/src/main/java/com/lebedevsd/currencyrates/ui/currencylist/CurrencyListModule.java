package com.lebedevsd.currencyrates.ui.currencylist;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.savedstate.SavedStateRegistryOwner;

import com.lebedevsd.currencyrates.di.ViewModelAssistedFactory;
import com.lebedevsd.currencyrates.di.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class CurrencyListModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyListViewModel.class)
    abstract ViewModelAssistedFactory<? extends ViewModel> bindFactory(CurrencyListViewModel.Factory factory);

    @Binds
    abstract SavedStateRegistryOwner bindSavedStateRegistryOwner(CurrencyListFragment currencyListFragment);

    @Nullable
    @Provides
    static Bundle provideDefaultArgs() {
        return null;
    }
}