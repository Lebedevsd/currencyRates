package com.lebedevsd.currencyrates.ui.currencylist

import com.lebedevsd.currencyrates.api.model.CurrencyRatesResponse
import com.lebedevsd.currencyrates.base.mvi.Action
import com.lebedevsd.currencyrates.base.mvi.MviReducer
import com.lebedevsd.currencyrates.base.mvi.State
import javax.inject.Inject

data class CurrencyListState(
    val isLoading: Boolean = false,
    val selectedCurrency: String
) : State

sealed class CurrencyListActions : Action {
    object LoadInitialData : CurrencyListActions()
    data class DataLoaded(val ratesResponse: CurrencyRatesResponse) : CurrencyListActions()
    data class DataLoadFailed(val throwable: Throwable) : CurrencyListActions()
    object LoadData : CurrencyListActions()
}

class CurrencyListReducer @Inject constructor() :
    MviReducer<CurrencyListState, CurrencyListActions> {
    override fun invoke(old: CurrencyListState, action: CurrencyListActions): CurrencyListState {
        return when (action) {
            is CurrencyListActions.LoadInitialData -> old.copy(isLoading = true)
            is CurrencyListActions.DataLoaded -> old.copy(isLoading = false, selectedCurrency = action.ratesResponse.base)
            is CurrencyListActions.DataLoadFailed -> old
            is CurrencyListActions.LoadData -> old
        }
    }
}
