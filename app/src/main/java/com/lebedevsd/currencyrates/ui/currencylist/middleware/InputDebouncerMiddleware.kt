package com.lebedevsd.currencyrates.ui.currencylist.middleware

import com.lebedevsd.currencyrates.base.mvi.MviMiddleware
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListActions
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListState
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class InputDebouncerMiddleware @Inject constructor() : MviMiddleware<CurrencyListActions, CurrencyListState> {

    override fun invoke(
        actions: Flowable<CurrencyListActions>,
        state: Flowable<CurrencyListState>
    ): Flowable<CurrencyListActions> {
        return actions
            .observeOn(Schedulers.io())
            .ofType(CurrencyListActions.ValueInput::class.java)
            .debounce(200, TimeUnit.MILLISECONDS)
            .map {
                val inputToDouble = try {
                    it.input.toDouble()
                } catch (e: NumberFormatException) {
                    0.0
                }
                CurrencyListActions.RecalculateValues(inputToDouble) as CurrencyListActions
            }
    }
}
