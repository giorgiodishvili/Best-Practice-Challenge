package com.android.bestpracticechallenge.ui.error

import com.android.bestpracticechallenge.bean.NewsModel
import retrofit2.Response

object MyErrorHandler {


    private const val HTTP_400_BAD_REQUEST = 400
    private const val HTTP_401_UNAUTHORIZED = 401
    private const val HTTP_404_NOT_FOUND = 404
    private const val HTTP_500_INTERNAL_SERVER_ERROR = 500
    private const val HTTP_204_NO_CONTENT = 204

    fun handleResponseCode(response: Response<List<NewsModel>>) {
        when {
            response.code() == HTTP_400_BAD_REQUEST -> handleError(response.errorBody()!!.string())
            response.code() == HTTP_401_UNAUTHORIZED -> handleError(response.errorBody()!!.string())
            response.code() == HTTP_404_NOT_FOUND -> handleError(response.errorBody()!!.string())
            response.code() == HTTP_500_INTERNAL_SERVER_ERROR -> handleError(
                response.errorBody()!!.string()
            )
            response.code() == HTTP_204_NO_CONTENT -> handleError(response.errorBody()!!.string())
            else -> {
                handleError("UNKNOWN ERROR")
            }
        }

    }

    fun handleError(string: String) {

    }
}