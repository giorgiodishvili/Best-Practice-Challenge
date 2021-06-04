package com.android.bestpracticechallenge.ui

data class ApiResult<T>(
    val state: State,
    val data: T? = null,
    val message: String? = null,
    val loading: Boolean = false
) {
    enum class State {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(item: T): ApiResult<T> {
            return ApiResult(State.SUCCESS, item)
        }

        fun <T> failure(message: String): ApiResult<T> {
            return ApiResult(State.ERROR, null, message)
        }

        fun <T> loading(flag: Boolean): ApiResult<T> {
            return ApiResult(State.LOADING, null, null, flag)
        }
    }
}