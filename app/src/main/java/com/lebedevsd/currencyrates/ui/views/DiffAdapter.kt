package com.lebedevsd.currencyrates.ui.views

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter


class DiffAdapter(
    delegates: List<AdapterDelegate<List<ListItem>>>
) : AsyncListDifferDelegationAdapter<ListItem>(ListDiffCallback()) {
    init {
        delegates.forEach { delegate -> delegatesManager.addDelegate(delegate) }
    }

    private class ListDiffCallback<T : ListItem> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.listId == newItem.listId

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.equals(newItem)

        override fun getChangePayload(oldItem: T, newItem: T): Any? =
            newItem.calculatePayload(oldItem)
    }
}

