package com.hvasoftware.wikifilm.help

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object KeyboardUtils {

    /**
     * Setup keyboard : if click view is not editText --> Hide keyboard
     */
    fun setupKeyBoard(view: View) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText || !view.isFocusable()) {
            view.setOnTouchListener { _, _ ->
            hideSoftKeyboard(view)
                view.clearFocus()
                false
            }
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupKeyBoard(innerView)
            }
        }
    }



    fun showKeyboard(context: Context) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun closeKeyboard(context: Context) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }


    fun hideSoftKeyboard(activity: Activity) {
        hideSoftKeyboard(activity.window.decorView.rootView)
    }

    fun showSoftKeyboard(view: View?) {
        if (view == null) return
        if (view.requestFocus()) {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

  private  fun hideSoftKeyboard(view: View?) {
        if (view == null) return
            val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
