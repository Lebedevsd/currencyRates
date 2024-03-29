package com.lebedevsd.currencyrates.ui.currencylist

import com.lebedevsd.currencyrates.base.mvi.*
import com.lebedevsd.currencyrates.ui.currency.CurrencyPresentationModel
import com.lebedevsd.currencyrates.ui.currency.calculateNewValue
import javax.inject.Inject

data class CurrencyListState(
    val currencies: List<CurrencyPresentationModel> = emptyList(),
    val selectedCurrency: String,
    val selectedValue: Double,
    val isLoading: Boolean = true,
    val isOnline: Boolean = true,
    val error: ViewStateErrorEvent? = null,
    val offlineEvent: ViewStateOfflineEvent? = null
) : State {
    companion object {
        val InitialState = CurrencyListState(
            selectedCurrency = "",
            selectedValue = 1.0
        )
    }
}

sealed class CurrencyListActions : Action {
    object LoadInitialData : CurrencyListActions()
    object LoadData : CurrencyListActions()
    object ScreenPaused : CurrencyListActions()
    object ScreenResumed : CurrencyListActions()
    data class ValueInput(val input: String) : CurrencyListActions()
    data class DataLoaded(val currenciesPresentationModels: List<CurrencyPresentationModel>) :
        CurrencyListActions()
    class DataLoadFailed(val error: Throwable) : CurrencyListActions()
    data class SelectCurrency(val currency: String) : CurrencyListActions()
    data class RecalculateValues(val input: Double) : CurrencyListActions()
    data class IsOnline(val isOnline: Boolean) : CurrencyListActions()
}

class CurrencyListReducer @Inject constructor() :
    MviReducer<CurrencyListState, CurrencyListActions> {
    override fun invoke(old: CurrencyListState, action: CurrencyListActions): CurrencyListState {
        return when (action) {
            is CurrencyListActions.DataLoadFailed -> old.copy(error = ViewStateErrorEvent(action.error))
            is CurrencyListActions.DataLoaded -> old.copy(
                currencies = action.currenciesPresentationModels,
                isLoading = false
            )
            is CurrencyListActions.SelectCurrency -> {
                val (sortedList, movedItem) = moveCurrencyUp(old, action)
                old.copy(
                    selectedValue = movedItem.value,
                    selectedCurrency = action.currency,
                    currencies = sortedList
                )
            }
            is CurrencyListActions.RecalculateValues -> {
                old.copy(
                    selectedValue = action.input,
                    currencies = old.currencies.map {
                        it.copy(
                            value = it.calculateNewValue(action.input)
                        )
                    }
                )
            }
            is CurrencyListActions.LoadInitialData -> old
            is CurrencyListActions.LoadData -> old
            is CurrencyListActions.ScreenPaused -> old
            is CurrencyListActions.ScreenResumed -> old
            is CurrencyListActions.ValueInput -> old
            is CurrencyListActions.IsOnline -> reduceOnline(action, old)
        }
    }

    private fun reduceOnline(
        action: CurrencyListActions.IsOnline,
        old: CurrencyListState
    ): CurrencyListState {
        return when {
            action.isOnline -> old.copy(isOnline = action.isOnline, offlineEvent = null)
            old.offlineEvent == null -> old.copy(
                isOnline = action.isOnline,
                offlineEvent = ViewStateOfflineEvent("No Network")
            )
            else -> old
        }
    }

    private fun moveCurrencyUp(
        old: CurrencyListState,
        action: CurrencyListActions.SelectCurrency
    ): Pair<List<CurrencyPresentationModel>, CurrencyPresentationModel> {
        val sortedList = old.currencies.toMutableList()
        val index = sortedList.indexOfFirst { it.title == action.currency }
        val item = sortedList.removeAt(index)
        sortedList.add(0, item)
        return Pair(sortedList, item)
    }
}
