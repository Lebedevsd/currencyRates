package com.lebedevsd.currencyrates.ui.currencylist

import com.lebedevsd.currencyrates.base.mvi.Action
import com.lebedevsd.currencyrates.base.mvi.MviReducer
import com.lebedevsd.currencyrates.base.mvi.State
import javax.inject.Inject

data class CurrencyListState(val text: String) : State

sealed class CurrencyListActions : Action

class CurrencyListReducer @Inject constructor():
    MviReducer<CurrencyListState, CurrencyListActions> {
    override fun invoke(old: CurrencyListState, action: CurrencyListActions): CurrencyListState {
        return when (action) {
            else -> old
        }
    }
}
