package com.android.bestpracticechallenge.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.bestpracticechallenge.bean.NewsModel
import com.android.bestpracticechallenge.ui.api.RetrofitService
import com.android.bestpracticechallenge.ui.constants.ApiMethod
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class NewsPagingSource : PagingSource<Int, NewsModel>() {
    override fun getRefreshKey(state: PagingState<Int, NewsModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsModel> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = RetrofitService.retrofit().getRequest(ApiMethod.news, position)
            val data = response.body()!!
            LoadResult.Page(
                data = data,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            ApiResult.failure<NewsModel>("Error")
            return LoadResult.Error(exception)
        }
    }
}