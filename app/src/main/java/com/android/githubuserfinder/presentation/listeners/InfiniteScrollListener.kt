package com.android.githubuserfinder.presentation.listeners

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollListener(
    private val onLoadMore: () -> Unit,
    private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    companion object {
        var isLoading = false
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy < 0) return

        if (!isLoading &&
            (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1)
        ) {
            onLoadMore()
            isLoading = true
        } else {
//            isLoading = false
        }

    }

}