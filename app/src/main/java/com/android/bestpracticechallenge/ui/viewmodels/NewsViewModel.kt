package com.android.bestpracticechallenge.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.bestpracticechallenge.bean.NewsModel
import com.android.bestpracticechallenge.ui.NewsRepo

class NewsViewModel : ViewModel() {

    private val newsRepo = NewsRepo()

    private var downloadingLiveData = MutableLiveData<Boolean>()

    fun fetchNews(): LiveData<PagingData<NewsModel>> {
        return newsRepo.getNews().cachedIn(viewModelScope)
    }
}