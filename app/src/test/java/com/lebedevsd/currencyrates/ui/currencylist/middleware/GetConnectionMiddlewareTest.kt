package com.lebedevsd.currencyrates.ui.currencylist.middleware

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListActions
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListState
import com.lebedevsd.githubviewer.extension.InstantExecutorExtension
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.subscribers.TestSubscriber
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class GetConnectionMiddlewareTest {

    private var connectivityState: BehaviorProcessor<Connectivity> = BehaviorProcessor.create()
    val middleware = GetConnectionMiddleware(connectivityState)

    @Test
    fun `should send IsOnline(true) action when is connected and resumed`() {
        val subscriber = TestSubscriber.create<CurrencyListActions>()
        val actions =
            BehaviorProcessor.createDefault<CurrencyListActions>(CurrencyListActions.ScreenResumed)
        val state =
            BehaviorProcessor.createDefault<CurrencyListState>(CurrencyListState.InitialState)

        middleware(actions, state).subscribe(subscriber)
        connectivityState.onNext(Connectivity.available(true).build())

        subscriber.assertNoErrors()
        subscriber.assertValue {
            it is CurrencyListActions.IsOnline && it.isOnline
        }
    }

    @Test
    fun `should send IsOnline(false) action when is disconnected and resumed`() {
        val subscriber = TestSubscriber.create<CurrencyListActions>()
        val actions =
            BehaviorProcessor.createDefault<CurrencyListActions>(CurrencyListActions.ScreenResumed)
        val state =
            BehaviorProcessor.createDefault<CurrencyListState>(CurrencyListState.InitialState)

        middleware(actions, state).subscribe(subscriber)
        connectivityState.onNext(Connectivity.available(false).build())


        subscriber.assertNoErrors()
        subscriber.assertValue {
            it is CurrencyListActions.IsOnline && !it.isOnline
        }
    }
}