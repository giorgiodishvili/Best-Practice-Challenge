package com.android.bestpracticechallenge.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.android.bestpracticechallenge.bean.NewsModel
import kotlinx.coroutines.flow.Flow

class NewsRepo {

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

    fun getNews(): LiveData<PagingData<NewsModel>> {
        Log.d("PixaBayRepository", "New page")
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { NewsPagingSource() }
        ).liveData
    }

}