package com.hvasoftware.wikifilm.base

import android.app.Application
import com.hvasoftware.wikifilm.data.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    val movieRepository by lazy { MovieRepository() }

//    private val database by lazy { AppRoomDatabase.getDatabase(this, applicationScope) }
//    val authorRepository by lazy { RAuthorRepository(database.authorDao()) }
//    val quoteRepository by lazy { RQuoteRepository(database.quoteDao()) }
//    val categoryRepository by lazy { RCategoryRepository(database.categoryDao()) }
//    val searchRepository by lazy { RecentSearchRepository(database.searchDao()) }

    override fun onCreate() {
        super.onCreate()
        mSelf = this
    }


    companion object {
        private var mSelf: MyApplication? = null
        fun self(): MyApplication? {
            return mSelf
        }
    }

}