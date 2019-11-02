package com.lebedevsd.currencyrates.base.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lebedevsd.currencyrates.base.mvi.Action
import com.lebedevsd.currencyrates.base.mvi.MviView
import com.lebedevsd.currencyrates.base.mvi.MviViewModel
import com.lebedevsd.currencyrates.base.mvi.State
import com.lebedevsd.currencyrates.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<S : State, A: Action, T : BaseViewModel<A, S> > : DaggerFragment(), MviView<S> {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected val viewModel: T by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }

    protected abstract val viewModelClass: Class<T>

    override fun <A : Action> bind(viewModel: MviViewModel<A, S>) {
        viewModel.state.observe(this,
            Observer { state -> render(state) }
        )
    }
}