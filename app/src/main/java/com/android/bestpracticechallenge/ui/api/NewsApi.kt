package com.android.bestpracticechallenge.ui.api

import com.android.bestpracticechallenge.bean.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NewsApi {

    @GET("{path}")
    suspend fun getRequest(@Path("path") path: String,@Query("page") query: Int): Response<List<NewsModel>>

}