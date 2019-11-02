package com.lebedevsd.currencyrates.ui.currencylist.middleware

import com.lebedevsd.currencyrates.base.mvi.MviMiddleware
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListActions
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListState
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoadDataEverySecondMiddleware @Inject constructor(
) : MviMiddleware<CurrencyListActions, CurrencyListState> {

    override fun invoke(
        actions: Flowable<CurrencyListActions>,
        state: Flowable<CurrencyListState>
    ): Flowable<CurrencyListActions> {
        return actions
            .observeOn(Schedulers.io())
            .ofType(CurrencyListActions.LoadInitialData::class.java)
            .flatMap {
                Flowable.interval(1, TimeUnit.SECONDS)
                    .map { CurrencyListActions.LoadData }
            }
    }
}