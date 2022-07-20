package hoang.nguyenminh.base.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class SpacingDecoration(private val space: Int) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space
        } else {
            outRect.top = 0
        }
    }
}

class GridSpacingDecoration(private val space: Int) : ItemDecoration() {

    private var spanCount = 0
    private var lastItemCount = 0
    private var lastRowStartIndex = 0

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        syncState(parent, state)
        outRect.apply {
            left = space
            right = space
            parent.getChildAdapterPosition(view).let {
                when {
                    isFirstRow(it) -> {
                        top = space
                        bottom = space
                    }
                    isLastRow(it) -> {
                        top = space
                        bottom = space
                    }
                    else -> {
                        top = space
                        bottom = space
                    }
                }
            }
        }
    }

    private fun syncState(parent: RecyclerView, state: RecyclerView.State) {
        if (lastItemCount != state.itemCount) {
            lastItemCount = state.itemCount
            if (lastItemCount == 0) {
                lastRowStartIndex = -1
                spanCount = 0
            } else (parent.layoutManager as? GridLayoutManager)?.let {
                spanCount = it.spanCount
                lastRowStartIndex = if (state.itemCount % spanCount == 0) {
                    state.itemCount - spanCount
                } else {
                    state.itemCount / spanCount * spanCount
                }
            } ?: apply {
                spanCount = 0
                lastRowStartIndex = -1
            }
        }
    }

    private fun isFirstRow(position: Int): Boolean {
        return position < spanCount
    }

    private fun isLastRow(position: Int): Boolean {
        return position >= lastRowStartIndex
    }
}