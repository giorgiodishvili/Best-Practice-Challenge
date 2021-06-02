package com.android.bestpracticechallenge.ui.interfaces

import com.android.bestpracticechallenge.bean.NewsModel

interface OnLoadMoreListener {
    fun onLoadMore(newsModel: NewsModel, nextElementIndex: Int)
}