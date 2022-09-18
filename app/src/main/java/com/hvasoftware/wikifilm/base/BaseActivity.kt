package com.hvasoftware.wikifilm.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hvasoftware.wikifilm.help.KeyboardUtils

abstract class BaseActivity : AppCompatActivity(), BaseContract.View, BaseContract.Data {

    protected var TAG = javaClass.simpleName



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setContentView())

    }


    fun <T> openActivity(toClass: Class<T>, isHideKeyboard: Boolean) {
        if (isHideKeyboard) {
            KeyboardUtils.closeKeyboard(this)
        }
        val intent = Intent(this, toClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


    fun <T> openOneActivity(toClass: Class<T>) {
        val intent = Intent(this, toClass)
        startActivity(intent)
        finishAffinity()
    }


}