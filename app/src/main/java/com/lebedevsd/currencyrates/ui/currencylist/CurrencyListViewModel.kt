package com.lebedevsd.currencyrates.ui.currencylist

import androidx.lifecycle.SavedStateHandle
import com.lebedevsd.currencyrates.base.mvi.MviMiddleware
import com.lebedevsd.currencyrates.base.ui.BaseViewModel
import com.lebedevsd.currencyrates.di.ViewModelAssistedFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class CurrencyListViewModel @AssistedInject constructor(
    @Assisted handle: SavedStateHandle,
    reducer: CurrencyListReducer
) : BaseViewModel<CurrencyListActions, CurrencyListState>(reducer, handle) {

    init {
        initWithState(CurrencyListState("test"))
    }

    override fun createMiddleWares(): List<MviMiddleware<CurrencyListActions, CurrencyListState>> {
        return emptyList()
    }

    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<CurrencyListViewModel>
}
