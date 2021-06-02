package com.android.bestpracticechallenge.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.bestpracticechallenge.bean.NewsModel
import com.android.bestpracticechallenge.ui.api.RetrofitService
import com.android.bestpracticechallenge.ui.constants.ApiMethod
import com.android.bestpracticechallenge.ui.error.MyErrorHandler
import com.android.bestpracticechallenge.ui.tools.Tools
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel : ViewModel() {

    private var fetchedNews = MutableLiveData<List<NewsModel>>().apply {
        mutableListOf<NewsModel>()
    }

    val _fetchedNewsLiveData: LiveData<List<NewsModel>> = fetchedNews

    private var downloadingLiveData = MutableLiveData<Boolean>()

    val _downloadLiveData: LiveData<Boolean> = downloadingLiveData


    fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            populateList()
        }
    }

    private suspend fun populateList() {

        downloadingLiveData.postValue(true)
        lateinit var result: Response<List<NewsModel>>

        if (Tools.isInternetAvailable()) {
            result = RetrofitService.retrofit().getRequest(ApiMethod.news)
            if (result.isSuccessful) {
                fetchedNews.postValue(result.body() as MutableList<NewsModel>)
            }else{
                MyErrorHandler.handleResponseCode(result)
            }
        }else {
            MyErrorHandler.handleError("No Internet")
        }
        downloadingLiveData.postValue(false)

    }
}