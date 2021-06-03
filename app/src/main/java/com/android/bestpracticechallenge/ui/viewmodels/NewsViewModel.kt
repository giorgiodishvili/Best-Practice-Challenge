package com.android.bestpracticechallenge.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.bestpracticechallenge.bean.NewsModel
import com.android.bestpracticechallenge.ui.ApiResult
import com.android.bestpracticechallenge.ui.api.RetrofitService
import com.android.bestpracticechallenge.ui.constants.ApiMethod
import com.android.bestpracticechallenge.ui.error.MyErrorHandler
import com.android.bestpracticechallenge.ui.tools.Tools
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel : ViewModel() {

    private var fetchedNews = MutableLiveData<ApiResult<List<NewsModel>>>().apply {
        mutableListOf<NewsModel>()
    }

    val _fetchedNewsLiveData: LiveData<ApiResult<List<NewsModel>>> = fetchedNews

    fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            populateList()
        }
    }

    private suspend fun populateList() {
        lateinit var result: Response<List<NewsModel>>
        fetchedNews.postValue(ApiResult.loading(true))

        if (Tools.isInternetAvailable()) {
            result = RetrofitService.retrofit().getRequest(ApiMethod.news)
            if (result.isSuccessful) {
                fetchedNews.postValue(ApiResult.loading(false))
                fetchedNews.postValue(ApiResult.success(result.body() as MutableList<NewsModel>))
            } else {
                fetchedNews.postValue(ApiResult.loading(false))
                fetchedNews.postValue(ApiResult.failure(result.message()))
                MyErrorHandler.handleResponseCode(result)
            }
        } else {
            MyErrorHandler.handleError("No Internet")
        }
    }
}