package com.lebedevsd.currencyrates.base.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import timber.log.Timber

abstract class OnLoadMoreScrollListener(
    private val threshold: Int
) : RecyclerView.OnScrollListener() {
    private var loading = false
    private var totalCountBeforeLoad: Int = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val visibleCount = layoutManager.childCount
        val totalCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        Timber.v("visibleCount = %d", visibleCount)
        Timber.v("totalCount = %d", totalCount)
        Timber.v("lastVisibleItemPosition = %d", lastVisibleItemPosition)

        if (!loading && lastVisibleItemPosition == totalCount - 1 - threshold) {
            loading = true
            totalCountBeforeLoad = totalCount
            Timber.v("Loading more...")
            onLoadMore()
        } else if (loading && totalCount > totalCountBeforeLoad) {
            Timber.v("Finished loading")
            loading = false
        }
    }

    protected abstract fun onLoadMore()
}
