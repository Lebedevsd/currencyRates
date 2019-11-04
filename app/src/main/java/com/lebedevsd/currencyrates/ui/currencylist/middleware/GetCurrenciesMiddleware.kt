package com.lebedevsd.currencyrates.ui.currencylist.middleware

import com.lebedevsd.currencyrates.api.model.CurrencyRatesResponse
import com.lebedevsd.currencyrates.base.mvi.MviMiddleware
import com.lebedevsd.currencyrates.interactor.GetCurrencyRatesInteractor
import com.lebedevsd.currencyrates.ui.base.FlagNameImage
import com.lebedevsd.currencyrates.ui.currency.CurrencyPresentationModel
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListActions
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListState
import com.lebedevsd.currencyrates.utils.CurrencyUtils
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject


class GetCurrenciesMiddleware @Inject constructor(
    private val getCurrencyRatesInteractor: GetCurrencyRatesInteractor,
    private val currencyUtils: CurrencyUtils
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
                if (it.isOnline) {
                    getCurrencyRatesInteractor(it.selectedCurrency)
                        .map { response ->
                            Timber.d("State has : ${it.selectedValue}")
                            response.toPresentationModel(it.selectedValue, currencyUtils)
                        }
                        .map<CurrencyListActions> { pm ->
                            CurrencyListActions.DataLoaded(
                                pm
                            )
                        }
                        .onErrorReturn {
                            CurrencyListActions.DataLoadFailed(it)
                        }
                } else Flowable.empty()
            }
    }
}

private fun CurrencyRatesResponse.toPresentationModel(
    selectedValue: Double,
    currencyUtils: CurrencyUtils
): List<CurrencyPresentationModel> {
    val currencies = this.rates.entries.map {
        val currency = currencyUtils.getCurrencySymbol(it.key)
        CurrencyPresentationModel(
            it.key,
            currencyUtils.getCurrencySymbol(it.key).displayName,
            BigDecimal(it.value * selectedValue).setScale(2, RoundingMode.HALF_UP).toDouble(),
            it.value,
            FlagNameImage(currencyUtils.getFlagName(currency))
        )
    }.toMutableList()
    val currency = currencyUtils.getCurrencySymbol(this.base)
    currencies.add(
        0,
        CurrencyPresentationModel(
            this.base,
            currency.displayName,
            selectedValue,
            1.0,
            FlagNameImage(currencyUtils.getFlagName(currency))
        )
    )
    return currencies
}
