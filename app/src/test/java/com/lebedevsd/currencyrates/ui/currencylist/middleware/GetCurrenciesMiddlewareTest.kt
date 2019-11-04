package com.lebedevsd.currencyrates.ui.currencylist.middleware

import com.lebedevsd.currencyrates.creators.dummyCurrencyRatesResponse
import com.lebedevsd.currencyrates.interactor.GetCurrencyRatesInteractor
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListActions
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListState
import com.lebedevsd.currencyrates.utils.CurrencyUtils
import com.lebedevsd.githubviewer.extension.InstantExecutorExtension
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.subscribers.TestSubscriber
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class GetCurrenciesMiddlewareTest {

    val getCurrencyRatesInteractor: GetCurrencyRatesInteractor = mockk()
    val utils: CurrencyUtils = mockk(relaxed = true)
    val middleware = GetCurrenciesMiddleware(getCurrencyRatesInteractor, utils)

    @BeforeEach
    fun init() {
        clearMocks(getCurrencyRatesInteractor, utils)
    }

    @Test
    fun `should send DataLoaded action when data is loaded`() {
        val subscriber = TestSubscriber.create<CurrencyListActions>()
        val actions =
            BehaviorProcessor.createDefault<CurrencyListActions>(CurrencyListActions.LoadData)
        val state =
            BehaviorProcessor.createDefault<CurrencyListState>(CurrencyListState.InitialState)
        every { getCurrencyRatesInteractor(any()) } returns Flowable.just(dummyCurrencyRatesResponse())

        middleware(actions, state).subscribe(subscriber)

        verify {
            getCurrencyRatesInteractor(CurrencyListState.InitialState.selectedCurrency)
        }
        subscriber.assertNoErrors()
        subscriber.assertValue {
            it is CurrencyListActions.DataLoaded
        }
    }

    @Test
    fun `should send DataLoadFailed when data load failed`() {
        val subscriber = TestSubscriber.create<CurrencyListActions>()
        val actions =
            BehaviorProcessor.createDefault<CurrencyListActions>(CurrencyListActions.LoadData)
        val state =
            BehaviorProcessor.createDefault<CurrencyListState>(CurrencyListState.InitialState)
        val error: RuntimeException = java.lang.RuntimeException("expected")
        every { getCurrencyRatesInteractor(any()) } returns Flowable.error(error)

        middleware(actions, state).subscribe(subscriber)

        verify {
            getCurrencyRatesInteractor(CurrencyListState.InitialState.selectedCurrency)
        }
        subscriber.assertNoErrors()
        subscriber.assertValue {
            it is CurrencyListActions.DataLoadFailed
        }
    }
}