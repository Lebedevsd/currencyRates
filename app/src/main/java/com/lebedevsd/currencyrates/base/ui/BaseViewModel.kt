package com.lebedevsd.currencyrates.base.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.lebedevsd.currencyrates.base.mvi.*
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class BaseViewModel<A : Action, S : State>(
    private val reducer: MviReducer<S, A>,
    protected val handle: SavedStateHandle
) : ViewModel(), MviViewModel<A, S> {

    override val state: LiveData<S> get() = internalState.toLiveData()

    private val internalState = BehaviorProcessor.create<S>()
    private val disposable: CompositeDisposable = CompositeDisposable()
    private val middlewares: MutableList<MviMiddleware<A, S>> = mutableListOf()
    protected val actions: BehaviorProcessor<A> = BehaviorProcessor.create()

    fun initWithState(state: S) {
        setMiddlewares()
        disposable.add(
            actions
                .observeOn(Schedulers.io())
                .doOnNext { Timber.d("Log Action $it") }
                .withLatestFrom(internalState, reduceState(reducer))
                .doOnError { Timber.e(it) }
                .distinctUntilChanged()
                .subscribe {
                    Timber.d("Log State $it")
                    internalState.onNext(it)
                }
        )

        internalState.onNext(state)
    }

    private fun reduceState(reducer: MviReducer<S, A>): BiFunction<A, S, S> {
        return BiFunction { action, state ->
            reducer(state, action)
        }
    }

    private fun setMiddlewares() {
        middlewares.addAll(createMiddleWares())
        disposable.add(
            Flowable.merge<A>(
                middlewares.map {
                    it(actions, internalState)
                }
            ).subscribe(actions::onNext)
        )
    }

    override fun onCleared() {
        Timber.d("ViewModel is destroyed")
        disposable.clear()
        super.onCleared()
    }

    override fun userAction(action: A) {
        actions.onNext(action)
    }

    protected abstract fun createMiddleWares(): List<MviMiddleware<A, S>>
}