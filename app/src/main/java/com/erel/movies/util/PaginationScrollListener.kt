package com.erel.movies.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private var linearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
    abstract fun loadMoreItems()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = linearLayoutManager.childCount
        val totalItemCount = linearLayoutManager.itemCount
        val firstVisibleItemPos = linearLayoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage() && visibleItemCount + firstVisibleItemPos >= totalItemCount
            && firstVisibleItemPos >= 0
        ) {
            loadMoreItems()
        }
    }
}