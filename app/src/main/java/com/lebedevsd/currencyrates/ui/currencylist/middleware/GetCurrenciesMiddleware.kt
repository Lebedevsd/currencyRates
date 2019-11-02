package com.lebedevsd.currencyrates.ui.currencylist.middleware

import com.lebedevsd.currencyrates.base.mvi.MviMiddleware
import com.lebedevsd.currencyrates.interactor.GetCurrencyRatesInteractor
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListActions
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListState
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetCurrenciesMiddleware @Inject constructor(
    private val getCurrencyRatesInteractor: GetCurrencyRatesInteractor
) : MviMiddleware<CurrencyListActions, CurrencyListState> {

    override fun invoke(
        actions: Flowable<CurrencyListActions>,
        state: Flowable<CurrencyListState>
    ): Flowable<CurrencyListActions> {
        return actions
            .observeOn(Schedulers.io())
            .ofType(CurrencyListActions.LoadData::class.java)
            .flatMap { state }
            .flatMap {
                getCurrencyRatesInteractor(it.selectedCurrency)
            }
            .map<CurrencyListActions> { response ->
                CurrencyListActions.DataLoaded(
                    response
                )
            }
            .onErrorReturn {
                CurrencyListActions.DataLoadFailed(it)
            }
    }
}