package com.lebedevsd.currencyrates.ui.currencylist

import androidx.lifecycle.SavedStateHandle
import com.lebedevsd.currencyrates.base.mvi.MviMiddleware
import com.lebedevsd.currencyrates.base.ui.BaseViewModel
import com.lebedevsd.currencyrates.di.ViewModelAssistedFactory
import com.lebedevsd.currencyrates.ui.currencylist.middleware.GetConnectionMiddleware
import com.lebedevsd.currencyrates.ui.currencylist.middleware.GetCurrenciesMiddleware
import com.lebedevsd.currencyrates.ui.currencylist.middleware.InputDebouncerMiddleware
import com.lebedevsd.currencyrates.ui.currencylist.middleware.LoadDataEverySecondMiddleware
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class CurrencyListViewModel @AssistedInject constructor(
    @Assisted handle: SavedStateHandle,
    reducer: CurrencyListReducer,
    private val getCurrenciesMiddleware: GetCurrenciesMiddleware,
    private val everySecondMiddleware: LoadDataEverySecondMiddleware,
    private val inputDebouncerMiddleware: InputDebouncerMiddleware,
    private val getConnectionMiddleware: GetConnectionMiddleware
) : BaseViewModel<CurrencyListActions, CurrencyListState>(reducer, handle) {

    init {
        initWithState(CurrencyListState.InitialState)
        userAction(CurrencyListActions.LoadInitialData)
    }

    override fun createMiddleWares(): List<MviMiddleware<CurrencyListActions, CurrencyListState>> {
        return listOf(
            getCurrenciesMiddleware,
            everySecondMiddleware,
            inputDebouncerMiddleware,
            getConnectionMiddleware
        )
    }

    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<CurrencyListViewModel>
}
