package com.lebedevsd.currencyrates.base.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.lebedevsd.currencyrates.R
import com.lebedevsd.currencyrates.base.mvi.Action
import com.lebedevsd.currencyrates.base.mvi.MviView
import com.lebedevsd.currencyrates.base.mvi.MviViewModel
import com.lebedevsd.currencyrates.base.mvi.State
import com.lebedevsd.currencyrates.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<S : State, A: Action, T : BaseViewModel<A, S> > : DaggerFragment(), MviView<S> {

    protected abstract val viewModelClass: Class<T>
    private var errorSnackBar: Snackbar? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected val viewModel: T by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }

    protected fun showError(error: Throwable) {
        errorSnackBar = Snackbar.make(view!!, R.string.something_went_wrong, Snackbar.LENGTH_LONG).apply {
            show()
        }
    }

    protected fun showSnack(msg: String) {
        errorSnackBar = Snackbar.make(view!!, msg, Snackbar.LENGTH_LONG).apply {
            show()
        }
    }

    protected fun dismissErrorIfShown() {
        errorSnackBar?.dismiss()
    }

    override fun <A : Action> bind(viewModel: MviViewModel<A, S>) {
        viewModel.state.observe(this,
            Observer { state -> render(state) }
        )
    }
}