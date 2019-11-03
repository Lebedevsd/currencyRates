package com.lebedevsd.currencyrates.ui.currencylist

import com.lebedevsd.currencyrates.base.mvi.Action
import com.lebedevsd.currencyrates.base.mvi.MviReducer
import com.lebedevsd.currencyrates.base.mvi.State
import com.lebedevsd.currencyrates.base.mvi.ViewStateErrorEvent
import com.lebedevsd.currencyrates.ui.views.CurrencyPresentationModel
import javax.inject.Inject

data class CurrencyListState(
    val isLoading: Boolean = false,
    val currencies: List<CurrencyPresentationModel> = emptyList(),
    val selectedCurrency: String,
    val selectedValue: Double,
    val error: ViewStateErrorEvent? = null
) : State {
    companion object {
        val InitialState = CurrencyListState(
            selectedCurrency = "",
            selectedValue = 0.0
        )
    }
}

sealed class CurrencyListActions : Action {
    object LoadInitialData : CurrencyListActions()
    object LoadData : CurrencyListActions()
    data class DataLoaded(val currenciesPresentationModels: List<CurrencyPresentationModel>) :
        CurrencyListActions()

    class DataLoadFailed(val error: Throwable) : CurrencyListActions()
    data class SelectCurrency(val currency: String) : CurrencyListActions()
}

class CurrencyListReducer @Inject constructor() :
    MviReducer<CurrencyListState, CurrencyListActions> {
    override fun invoke(old: CurrencyListState, action: CurrencyListActions): CurrencyListState {
        return when (action) {
            is CurrencyListActions.LoadInitialData -> old.copy(isLoading = true)
            is CurrencyListActions.DataLoadFailed -> old.copy(error = ViewStateErrorEvent(action.error))
            is CurrencyListActions.DataLoaded -> old.copy(
                isLoading = false,
                currencies = action.currenciesPresentationModels
            )
            is CurrencyListActions.SelectCurrency -> {
                val sortedList = old.currencies.toMutableList()
                val index = sortedList.indexOfFirst { it.title == action.currency }
                sortedList.add(0, sortedList.removeAt(index))
                old.copy(
                    selectedCurrency = action.currency,
                    currencies = sortedList
                )
            }
            is CurrencyListActions.LoadData -> old
        }
    }
}
