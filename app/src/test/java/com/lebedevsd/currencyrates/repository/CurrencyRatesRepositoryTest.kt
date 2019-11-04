package com.lebedevsd.currencyrates.repository

import com.lebedevsd.currencyrates.api.model.CurrencyRatesResponse
import com.lebedevsd.currencyrates.creators.dummyCurrencyRatesResponse
import com.lebedevsd.githubviewer.api.CurrencyRatesApi
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class CurrencyRatesRepositoryTest {
    val remoteApi: CurrencyRatesApi = mockk()
    val repository = CurrencyRatesRepository(remoteApi)

    @BeforeEach
    fun init() {
        clearMocks(remoteApi)
    }

    @Nested
    inner class GetContributors {

        @Test
        fun `should return items`() {
            val testObserver = TestSubscriber.create<CurrencyRatesResponse>()
            val result = dummyCurrencyRatesResponse()
            every { remoteApi.getRates(any()) } returns Single.just(result)

            repository.getCurrencies("").subscribe(testObserver)

            verify { remoteApi.getRates(any()) }
            testObserver.assertNoErrors()
            testObserver.assertValue { it == result }
        }

        @Test
        fun `should return error if error in api`() {
            val testObserver = TestSubscriber.create<CurrencyRatesResponse>()
            val error = RuntimeException("test")
            every { remoteApi.getRates(any()) } returns Single.error(error)

            repository.getCurrencies("").subscribe(testObserver)

            verify { remoteApi.getRates(any()) }
            testObserver.assertError(error)
            testObserver.assertNoValues()
        }

    }
}