package com.lebedevsd.currencyrates.ui.currencylist.middleware

import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListActions
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListState
import com.lebedevsd.githubviewer.extension.InstantExecutorExtension
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.subscribers.TestSubscriber
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class InputDebouncerMiddlewareTest {

    val middleware = InputDebouncerMiddleware()

    @Test
    fun `should send RecalculateValues action when user input detected`() {
        val subscriber = TestSubscriber.create<CurrencyListActions>()
        val actions =
            BehaviorProcessor.createDefault<CurrencyListActions>(CurrencyListActions.LoadData)
        val state =
            BehaviorProcessor.createDefault<CurrencyListState>(CurrencyListState.InitialState)
        middleware(actions, state).subscribe(subscriber)

        actions.onNext(CurrencyListActions.ValueInput("1"))

        subscriber.assertNoErrors()
        subscriber.assertValue {
            it is CurrencyListActions.RecalculateValues && it.input == 1.0
        }
    }
}