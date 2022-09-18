package com.hvasoftware.wikifilm.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//
//fun View.setMarginTop(value: Int) = updateLayoutParams<ViewGroup.MarginLayoutParams> {
//    topMargin = value
//}

fun View.setMarginBottom(value: Int) = updateLayoutParams<ViewGroup.MarginLayoutParams> {
    bottomMargin = value
}

fun View.pxFromDp(dp: Float): Int {
    return (dp * resources.displayMetrics.density).toInt()
}

fun RecyclerView.getCurrentPosition(): Int {
    return (this.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
}

fun View.showIf(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

inline fun <T : View> T.showIf(condition: (T) -> Boolean) {
    if (condition(this)) {
        show()
    } else {
        hide()
    }
}

inline fun <T : View> T.hideIf(condition: (T) -> Boolean) {
    if (condition(this)) {
        hide()
    } else {
        show()
    }
}
//
//internal fun getNavOptions(): NavOptions? {
//    return NavOptions.Builder()
//        .setEnterAnim(R.anim.slide_in)
//        .setExitAnim(R.anim.slide_out)
//        .setPopEnterAnim(R.anim.slide_in)
//        .setPopExitAnim(R.anim.slide_out)
//        .build()
//}