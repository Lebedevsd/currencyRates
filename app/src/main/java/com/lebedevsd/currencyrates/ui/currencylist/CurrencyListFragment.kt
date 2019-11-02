package com.lebedevsd.currencyrates.ui.currencylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lebedevsd.currencyrates.R
import com.lebedevsd.currencyrates.base.ui.BaseFragment
import timber.log.Timber


class CurrencyListFragment :
    BaseFragment<CurrencyListState, CurrencyListActions, CurrencyListViewModel>() {
    override val viewModelClass: Class<CurrencyListViewModel> = CurrencyListViewModel::class.java

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind(viewModel)
        return inflater.inflate(R.layout.fragment_currencies, container, false)
    }

    override fun render(state: CurrencyListState) {
        Timber.d("$state")
    }
}