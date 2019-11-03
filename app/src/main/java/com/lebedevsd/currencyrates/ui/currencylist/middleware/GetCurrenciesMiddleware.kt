package com.lebedevsd.currencyrates.ui.currencylist.middleware

import com.lebedevsd.currencyrates.api.model.CurrencyRatesResponse
import com.lebedevsd.currencyrates.base.mvi.MviMiddleware
import com.lebedevsd.currencyrates.interactor.GetCurrencyRatesInteractor
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListActions
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListState
import com.lebedevsd.currencyrates.ui.currency.CurrencyPresentationModel
import com.lebedevsd.currencyrates.utils.CurrencyUtils
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.math.BigDecimal
import java.math.RoundingMode
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
                    .map { response ->
                        Timber.d("State has : ${it.selectedValue}")
                        response.toPresentationModel(it.selectedValue)
                    }
                    .map<CurrencyListActions> { pm ->
                        CurrencyListActions.DataLoaded(
                            pm
                        )
                    }
                    .onErrorReturn {
                        CurrencyListActions.DataLoadFailed(it)
                    }
            }
    }
}

private fun CurrencyRatesResponse.toPresentationModel(
    selectedValue: Double
): List<CurrencyPresentationModel> {
    val currencies = this.rates.entries.map {
        val currency = CurrencyUtils.getCurrencySymbol(it.key)
        CurrencyPresentationModel(
            it.key,
            CurrencyUtils.getCurrencySymbol(it.key).displayName,
            BigDecimal(it.value * selectedValue).setScale(2, RoundingMode.HALF_UP).toDouble(),
            it.value,
            CurrencyUtils.getFlag(currency)
        )
    }.toMutableList()
    val currency = CurrencyUtils.getCurrencySymbol(this.base)
    currencies.add(
        0,
        CurrencyPresentationModel(
            this.base,
            currency.displayName,
            selectedValue,
            1.0,
            CurrencyUtils.getFlag(currency)
        )
    )
    return currencies
}