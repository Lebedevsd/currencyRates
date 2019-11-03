package com.lebedevsd.currencyrates.ui.currencylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.lebedevsd.currencyrates.R
import com.lebedevsd.currencyrates.base.ui.BaseFragment
import com.lebedevsd.currencyrates.ui.views.CurrencyPresentationModel
import com.lebedevsd.currencyrates.ui.views.DiffAdapter
import com.lebedevsd.currencyrates.ui.views.ListItem
import kotlinx.android.synthetic.main.fragment_currencies.*


class CurrencyListFragment :
    BaseFragment<CurrencyListState, CurrencyListActions, CurrencyListViewModel>() {
    override val viewModelClass: Class<CurrencyListViewModel> = CurrencyListViewModel::class.java

    private val currencyListAdapter by lazy {
        DiffAdapter(
            listOf(
                currencyAdapterDelegate {
                    viewModel.userAction(
                        CurrencyListActions.SelectCurrency(it.title)
                    )
                }
//                LoadingDelegate(),

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

        bind(viewModel)
        return view
    }

    override fun render(state: CurrencyListState) {
        currencyListAdapter.items = state.currencies
        state.error?.consume { showError(it) }
    }

    fun currencyAdapterDelegate(itemClickedListener: (CurrencyPresentationModel) -> Unit) =
        adapterDelegate<CurrencyPresentationModel, ListItem>(R.layout.viewholder_item_currency) {

            val name: TextView = findViewById(R.id.repo_name)
            name.setOnClickListener { itemClickedListener(item) }

            bind { diffPayloads ->
                name.text = "${item.title} ${item.value} ${item.exchangeRate}"
            }
        }
}