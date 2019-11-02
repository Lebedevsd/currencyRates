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
            .switchMap { state.take(1) }
            .switchMap {
                getCurrencyRatesInteractor(it.selectedCurrency)
                    .map<CurrencyListActions> { response ->
                        CurrencyListActions.DataLoaded(
                            response
                        )
                    }
                    .onExceptionResumeNext(Flowable.just(CurrencyListActions.DataLoadFailed()))
            }

    }
}