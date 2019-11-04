package com.lebedevsd.currencyrates.ui.base

interface ListItem {

    val listId: String

    fun calculatePayload(oldItem: ListItem): Any? = null
}
