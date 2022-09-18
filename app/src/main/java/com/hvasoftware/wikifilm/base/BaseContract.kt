package com.hvasoftware.wikifilm.base


interface BaseContract {

    interface View {

        // binding layout for screen
        fun setContentView() : android.view.View

        // handle all click event in view
        fun onViewClick()


    }

    interface Data {

        fun initData()

        fun loadData()

    }

}