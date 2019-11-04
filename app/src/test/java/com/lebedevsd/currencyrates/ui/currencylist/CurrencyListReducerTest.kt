package com.lebedevsd.currencyrates.ui.currencylist

import com.lebedevsd.currencyrates.creators.dummyCurrencyListState
import com.lebedevsd.currencyrates.creators.dummyCurrencyPresentationModel
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class CurrencyListReducerTest {

    val reducer = CurrencyListReducer()

//    LoadInitialData
//    LoadData
//    ScreenPaused
//    ScreenResumed
//    ValueInput(val input: String)
//    DataLoaded(val currenciesPresentationModels: List<CurrencyPresentationModel>)
//    DataLoadFailed(val error: Throwable)
//    SelectCurrency(val currency: String)
//    RecalculateValues(val input: Double)

    @Test
    fun `should reduce LoadInitialData`() {
        val old = dummyCurrencyListState()

        val newState = reducer.invoke(old, CurrencyListActions.LoadInitialData)

        Assertions.assertThat(newState).isEqualTo(old)
    }

    @Test
    fun `should reduce ValueInput`() {
        val old = dummyCurrencyListState()

        val newState = reducer.invoke(old, CurrencyListActions.ValueInput(""))

        Assertions.assertThat(newState).isEqualTo(old)
    }

    @Test
    fun `should reduce ScreenResumed`() {
        val old = dummyCurrencyListState()

        val newState = reducer.invoke(old, CurrencyListActions.ScreenResumed)

        Assertions.assertThat(newState).isEqualTo(old)
    }

    @Test
    fun `should reduce ScreenPaused`() {
        val old = dummyCurrencyListState()

        val newState = reducer.invoke(old, CurrencyListActions.ScreenPaused)

        Assertions.assertThat(newState).isEqualTo(old)
    }


    @Test
    fun `should reduce LoadData`() {
        val old = dummyCurrencyListState()

        val newState = reducer.invoke(old, CurrencyListActions.LoadData)

        Assertions.assertThat(newState).isEqualTo(old)
    }

    @Test
    fun `should reduce DataLoadFailed`() {
        val old = dummyCurrencyListState()
        val runtimeException = RuntimeException("test")

        val newState = reducer.invoke(old, CurrencyListActions.DataLoadFailed(runtimeException))

        Assertions.assertThat(newState).matches {
            it.error != null && it.error?.payload?.equals(runtimeException) ?: false
        }
    }

    @Test
    fun `should reduce SelectCurrency`() {
        val old = dummyCurrencyListState()

        val newState = reducer.invoke(old, CurrencyListActions.SelectCurrency("USD"))

        Assertions.assertThat(newState).matches {
            it.selectedCurrency == "USD" && it.currencies[0].title == "USD"
        }
    }

    @Test
    fun `should reduce RecalculateValues`() {
        val old = dummyCurrencyListState(
            currencies = listOf(
                dummyCurrencyPresentationModel(),
                dummyCurrencyPresentationModel(title = "USD", exchangeRate = 2.0)
            )
        )

        val newState = reducer.invoke(old, CurrencyListActions.RecalculateValues(20.0))

        Assertions.assertThat(newState).matches {
            it.selectedValue == 20.0 && it.currencies[0].value == 20.0 && it.currencies[1].value == 40.0
        }
    }

    @Test
    fun `should reduce DataLoaded`() {
        val old = dummyCurrencyListState(currencies = emptyList())

        val newState = reducer.invoke(
            old, CurrencyListActions.DataLoaded(
                listOf(
                    dummyCurrencyPresentationModel()
                )
            )
        )

        Assertions.assertThat(newState).matches {
            it.currencies.size == 1
        }
    }
}