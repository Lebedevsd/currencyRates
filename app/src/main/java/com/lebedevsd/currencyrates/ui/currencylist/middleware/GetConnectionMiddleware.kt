package com.lebedevsd.currencyrates.ui.currencylist.middleware

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.lebedevsd.currencyrates.base.mvi.MviMiddleware
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListActions
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListState
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class GetConnectionMiddleware @Inject constructor(
    private val connectivityState: Flowable<Connectivity>
) : MviMiddleware<CurrencyListActions, CurrencyListState> {

    override fun invoke(
        actions: Flowable<CurrencyListActions>,
        state: Flowable<CurrencyListState>
    ): Flowable<CurrencyListActions> {
        return actions
            .observeOn(Schedulers.io())
            .ofType(CurrencyListActions.ScreenResumed::class.java)
            .flatMap {
                connectivityState
                    .map { it.available() }
                    .distinctUntilChanged()
                    .map { CurrencyListActions.IsOnline(it) }
                    .takeUntil(
                        actions.observeOn(Schedulers.io())
                            .ofType(CurrencyListActions.ScreenPaused::class.java)
                    )
            }
    }
}