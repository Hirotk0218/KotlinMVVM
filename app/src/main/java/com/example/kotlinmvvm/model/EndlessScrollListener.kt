package com.example.kotlinmvvm.model

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val visibleThreshold: Int = 10,
    firstPage: Int = FirstPage,
    private val configure: EndlessScrollConfigure = LinearEndlessScrollConfigure()
) : RecyclerView.OnScrollListener() {

    companion object {
        private const val FirstPage: Int = 1
    }

    private var firstVisibleItemCount: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0

    private var scrolling: Boolean = false

    var page: Int = firstPage
        private set

    private var current: Int = 0

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            scrolling = true
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!configure.enableLoadOrientation(dx, dy)) return

        firstVisibleItemCount = layoutManager.findFirstVisibleItemPosition()
        visibleItemCount = recyclerView.childCount
        totalItemCount = layoutManager.itemCount

        if (scrolling) {
            if (totalItemCount > current) {
                scrolling = false
                current = totalItemCount
            }
        }

        if (scrolling && (totalItemCount - visibleItemCount) <= (firstVisibleItemCount + visibleThreshold)) {
            onLoadMore(++page)
            scrolling = false
        }
    }

    abstract fun onLoadMore(page: Int)

    interface EndlessScrollConfigure {
        fun enableLoadOrientation(dx: Int, dy: Int): Boolean
    }

    class LinearEndlessScrollConfigure(
        private val orientation: Int = RecyclerView.VERTICAL
    ) : EndlessScrollConfigure {
        override fun enableLoadOrientation(dx: Int, dy: Int): Boolean {
            return when (orientation) {
                RecyclerView.VERTICAL -> dy > 0
                RecyclerView.HORIZONTAL -> dx > 0
                else -> false
            }
        }
    }
}