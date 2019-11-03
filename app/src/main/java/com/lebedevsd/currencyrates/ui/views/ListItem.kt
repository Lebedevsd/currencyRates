package com.lebedevsd.currencyrates.ui.views

interface ListItem {
    val listId: String

    fun calculatePayload(oldItem: ListItem): Any? = null
}
