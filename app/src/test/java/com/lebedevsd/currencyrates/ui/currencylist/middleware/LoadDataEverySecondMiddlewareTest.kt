package com.lebedevsd.currencyrates.ui.currencylist.middleware

import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListActions
import com.lebedevsd.currencyrates.ui.currencylist.CurrencyListState
import com.lebedevsd.githubviewer.extension.InstantExecutorExtension
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subscribers.TestSubscriber
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.TimeUnit

@ExtendWith(InstantExecutorExtension::class)
internal class LoadDataEverySecondMiddlewareTest {

    val middleware = LoadDataEverySecondMiddleware()

    @Before
    fun setUp() = RxJavaPlugins.reset()

    @After
    fun tearDown() = RxJavaPlugins.reset()

    @Test
    fun `should send LoadData action when screen is resumed`() {
        val testScheduler = TestScheduler()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        val subscriber = TestSubscriber.create<CurrencyListActions>()
        val actions =
            BehaviorProcessor.createDefault<CurrencyListActions>(CurrencyListActions.LoadData)
        val state =
            BehaviorProcessor.createDefault<CurrencyListState>(CurrencyListState.InitialState)
        middleware(actions, state).subscribe(subscriber)

        actions.onNext(CurrencyListActions.ScreenResumed)

        subscriber.assertNoErrors()
        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
        subscriber.assertNoValues()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        subscriber.assertValue {
            it is CurrencyListActions.LoadData
        }
    }

    @Test
    fun `should not send LoadData action when screen is not resumed`() {
        val subscriber = TestSubscriber.create<CurrencyListActions>()
        val actions =
            BehaviorProcessor.createDefault<CurrencyListActions>(CurrencyListActions.LoadData)
        val state =
            BehaviorProcessor.createDefault<CurrencyListState>(CurrencyListState.InitialState)
        middleware(actions, state).subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertNoValues()
    }

    @Test
    fun `should stop sending LoadData action when screen is paused`() {
        val testScheduler = TestScheduler()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        val subscriber = TestSubscriber.create<CurrencyListActions>()
        val actions =
            BehaviorProcessor.createDefault<CurrencyListActions>(CurrencyListActions.ScreenResumed)
        val state =
            BehaviorProcessor.createDefault<CurrencyListState>(CurrencyListState.InitialState)
        middleware(actions, state).subscribe(subscriber)

        testScheduler.advanceTimeBy(1100, TimeUnit.MILLISECONDS)
        actions.onNext(CurrencyListActions.ScreenPaused)

        subscriber.assertNoErrors()
        subscriber.assertValue {
            it is CurrencyListActions.LoadData
        }
        testScheduler.advanceTimeBy(100, TimeUnit.SECONDS)
        subscriber.assertValueCount(1)
    }
}