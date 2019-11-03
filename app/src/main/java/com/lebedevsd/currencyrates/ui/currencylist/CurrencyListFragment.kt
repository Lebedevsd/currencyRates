package com.lebedevsd.currencyrates.ui.currencylist

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import android.widget.ImageView
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

    override fun onPause() {
        super.onPause()
        viewModel.userAction(CurrencyListActions.ScreenPaused)
    }

    override fun onResume() {
        super.onResume()
        viewModel.userAction(CurrencyListActions.ScreenResumed)
    }


    private val currencyListAdapter by lazy {
        DiffAdapter(
            listOf(
                currencyAdapterDelegate({
                    viewModel.userAction(
                        CurrencyListActions.SelectCurrency(it.title)
                    )
                }, {
                    viewModel.userAction(
                        CurrencyListActions.ValueInput(it)
                    )
                })
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

    fun currencyAdapterDelegate(
        itemClickedListener: (CurrencyPresentationModel) -> Unit,
        inputListener: (String) -> Unit
    ) =
        adapterDelegate<CurrencyPresentationModel, ListItem>(R.layout.viewholder_item_currency) {

            val logo: ImageView = findViewById(R.id.currency_logo)
            val title: TextView = findViewById(R.id.title)
            val description: TextView = findViewById(R.id.description)
            val amount: EditText = findViewById(R.id.amount)
            val view: View = findViewById(R.id.currency_item)

            view.setOnClickListener {
                if (adapterPosition != 0) {
                    itemClickedListener(item)
                    amount.setSelection(amount.text.length)
                }
            }

            bind {
                title.text = item.title
                description.text = item.description

                logo.setImageResource(
                    item.flagString.toImageResource(context)
                )

                if (item.value.toString() != amount.text.toString()) {
                    amount.setText(item.value.toString())
                    if (adapterPosition == 0) {
                        amount.setSelection(amount.text.length)
                        amount.requestFocus()
                        val imm =
                            amount.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
                        imm!!.showSoftInput(amount, SHOW_IMPLICIT)
                    }
                }

                amount.setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus && v == amount && adapterPosition != 0){
                        view.performClick()
                    }
                }

                amount.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        if (adapterPosition == 0) {
                            inputListener(p0.toString())
                        }
                    }

                })
            }
        }
}

private fun String.toImageResource(context: Context): Int {
    return context.resources.getIdentifier(this, "drawable", context.packageName)
}
