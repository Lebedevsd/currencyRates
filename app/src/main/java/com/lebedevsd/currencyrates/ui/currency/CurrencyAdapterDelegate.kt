package com.lebedevsd.currencyrates.ui.currency

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.lebedevsd.currencyrates.R
import com.lebedevsd.currencyrates.ui.base.ImageDisplayer
import com.lebedevsd.currencyrates.ui.base.ListItem

fun currencyAdapterDelegate(
    imageDisplayer: ImageDisplayer,
    selectCurrencyListener: (CurrencyPresentationModel) -> Unit,
    inputListener: (String) -> Unit
) =
    adapterDelegate<CurrencyPresentationModel, ListItem>(R.layout.viewholder_item_currency) {

        val flag_image: ImageView = findViewById(R.id.currency_logo)
        val title: TextView = findViewById(R.id.title)
        val description: TextView = findViewById(R.id.description)
        val amount: EditText = findViewById(R.id.amount)
        val view: View = findViewById(R.id.currency_item)

        view.setOnClickListener {
            if (adapterPosition != 0) {
                selectCurrencyListener(item)
                amount.requestFocus()
                amount.setSelection(amount.text.length)
            }
        }

        bind {
            if (it.isEmpty()) {
                if (title.text != item.title) {
                    imageDisplayer.displayTo(item.flagImage, flag_image)
                    title.text = item.title
                    description.text = item.description
                }

                if (item.value.toString() != amount.text.toString()) {
                    amount.setText(item.value.toString())
                    if (adapterPosition == 0) {
                        amount.setSelection(amount.text.length)
                        amount.requestFocus()
                        val imm =
                            amount.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                        imm!!.showSoftInput(amount, InputMethodManager.SHOW_IMPLICIT)
                    }
                }

                amount.setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus && v == amount && adapterPosition != 0) {
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
            } else {
                if (adapterPosition != 0) {
                    amount.setText(item.value.toString())
                }
            }
        }
    }