package com.lebedevsd.currencyrates.ui.views

data class CurrencyPresentationModel(
    val title: String,
    val value: Double,
    val exchangeRate: Double
) : ListItem {

    override val listId: String
        get() = this.title
}