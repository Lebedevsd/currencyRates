package com.lebedevsd.currencyrates.base.ui

import androidx.lifecycle.Observer
import com.lebedevsd.currencyrates.base.mvi.Action
import com.lebedevsd.currencyrates.base.mvi.MviView
import com.lebedevsd.currencyrates.base.mvi.MviViewModel
import com.lebedevsd.currencyrates.base.mvi.State
import dagger.android.support.DaggerFragment

abstract class BaseFragment<S : State> : DaggerFragment(), MviView<S> {

    override fun <A : Action> bind(viewModel: MviViewModel<A, S>) {
        viewModel.state.observe(this,
            Observer { state -> render(state) }
        )
    }

}