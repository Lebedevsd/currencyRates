package com.lebedevsd.currencyrates.ui.currencylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lebedevsd.currencyrates.R
import com.lebedevsd.currencyrates.base.connectivity.ConnectivityLiveData
import com.lebedevsd.currencyrates.base.ui.BaseFragment
import com.lebedevsd.currencyrates.ui.base.DiffAdapter
import com.lebedevsd.currencyrates.ui.base.ImagesDisplayeDelegates
import com.lebedevsd.currencyrates.ui.currency.currencyAdapterDelegate
import kotlinx.android.synthetic.main.fragment_currencies.*
import javax.inject.Inject


class CurrencyListFragment :
    BaseFragment<CurrencyListState, CurrencyListActions, CurrencyListViewModel>() {
    override val viewModelClass: Class<CurrencyListViewModel> = CurrencyListViewModel::class.java

    @Inject
    lateinit var imageDisplayerDelegate: ImagesDisplayeDelegates

    @Inject
    lateinit var connectivityLiveData: ConnectivityLiveData

    private val currencyListAdapter by lazy {
        DiffAdapter(
            listOf(
                currencyAdapterDelegate(
                    selectCurrencyListener = {
                        viewModel.userAction(
                            CurrencyListActions.SelectCurrency(it.title)
                        )
                    },
                    inputListener = {
                        viewModel.userAction(
                            CurrencyListActions.ValueInput(it)
                        )
                    },
                    imageDisplayer = imageDisplayerDelegate
                )
            )
        )
    }
    private val currencyLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_currencies, container, false)
        view.findViewById<RecyclerView>(R.id.recyclerview).apply {
            layoutManager = currencyLayoutManager
            adapter = currencyListAdapter
        }

        currencyListAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                recyclerview.scrollToPosition(0)
            }
        })

        connectivityLiveData.observe(this, Observer { state ->
            viewModel.userAction(CurrencyListActions.IsOnline(state))
        })
        bind(viewModel)
        return view
    }

    override fun render(state: CurrencyListState) {
        progress.isVisible = state.isLoading
        currencyListAdapter.items = state.currencies
        state.error?.consume { showError(it) }
        state.offlineEvent?.consume { showSnack(it) }
    }

    override fun onPause() {
        super.onPause()
        viewModel.userAction(CurrencyListActions.ScreenPaused)
    }

    override fun onResume() {
        super.onResume()
        viewModel.userAction(CurrencyListActions.ScreenResumed)
    }

}