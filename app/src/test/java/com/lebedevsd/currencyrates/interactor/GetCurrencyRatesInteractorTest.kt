package com.lebedevsd.currencyrates.interactor

import com.lebedevsd.currencyrates.api.model.CurrencyRatesResponse
import com.lebedevsd.currencyrates.creators.dummyCurrencyRatesResponse
import com.lebedevsd.currencyrates.repository.CurrencyRatesRepository
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetCurrencyRatesInteractorTest {

    private val repository: CurrencyRatesRepository = mockk()
    private val getRates = GetCurrencyRatesInteractor(repository)

    @BeforeEach
    fun init() {
        clearMocks(repository)
    }

    @Test
    fun `should return empty if repo has no items`() {
        val subscriber = TestSubscriber.create<CurrencyRatesResponse>()
        every { repository.getCurrencies(any()) } returns Flowable.empty()

        getRates("").subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
    }

    @Test
    fun `should return actual result if repo has items`() {
        val subscriber = TestSubscriber.create<CurrencyRatesResponse>()
        val result: CurrencyRatesResponse = dummyCurrencyRatesResponse()
        every { repository.getCurrencies(any()) } returns Flowable.just(result)

        getRates("").subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertValue {
            it == result
        }
    }

    @Test
    fun `should return error if repo has error`() {
        val subscriber = TestSubscriber.create<CurrencyRatesResponse>()
        val error = RuntimeException("test")
        every { repository.getCurrencies(any()) } returns Flowable.error(error)

        getRates("").subscribe(subscriber)

        subscriber.assertError(error)
        subscriber.assertNoValues()
    }
}