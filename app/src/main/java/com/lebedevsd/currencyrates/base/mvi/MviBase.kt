package com.lebedevsd.currencyrates.base.mvi

import androidx.lifecycle.LiveData
import io.reactivex.Flowable

interface State
interface Action

interface MviView<S : State> {

    fun render(state: S)
    fun <A : Action> bind(viewModel: MviViewModel<A, S>)
}

interface MviReducer<S : State, A : Action> {
    operator fun invoke(old: S, action: A): S
}

interface MviViewModel<A : Action, S : State> {

    val state: LiveData<S>
    fun userAction(action: A)
}

interface MviMiddleware<A : Action, S : State> {

    operator fun invoke(actions: Flowable<A>, state: Flowable<S>): Flowable<out A>
}
