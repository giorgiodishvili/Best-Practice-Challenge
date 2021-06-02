package com.android.bestpracticechallenge.ui.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.bestpracticechallenge.App
import com.android.bestpracticechallenge.bean.NewsModel
import com.android.bestpracticechallenge.databinding.ItemNewsRecyclerviewLayoutBinding
import com.android.bestpracticechallenge.databinding.ItemRecyclerviewLoadMoreLayoutBinding
import com.android.bestpracticechallenge.ui.interfaces.OnLoadMoreListener
import com.android.bestpracticechallenge.ui.tools.Tools
import com.bumptech.glide.Glide


class NewsRecyclerViewAdapter(
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var news: MutableList<NewsModel> = mutableListOf()


    companion object {
        private const val VIEW_TYPE_WALL_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var isLoading = false
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private val visibleThreshold = 5
    private var screenHeight = 0

    init {
        screenHeight = Tools.getScreenDimenss().y
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    if (onLoadMoreListener != null)
                        onLoadMoreListener!!.onLoadMore()
                    isLoading = true
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_WALL_ITEM -> WallPostsViewHolder(
                ItemNewsRecyclerviewLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> LoadMorePostsViewHolder(
                ItemRecyclerviewLoadMoreLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WallPostsViewHolder -> holder.onBind()
        }
    }

    inner class WallPostsViewHolder(private val binding: ItemNewsRecyclerviewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: NewsModel

        fun onBind() {
            model = news[absoluteAdapterPosition]
            Glide.with(App.instance.getContext()).load(model.cover)
                .into(binding.newsCoverImageView)
            binding.titleTextView.text = model.titleKA
        }
    }

    inner class LoadMorePostsViewHolder(binding: ItemRecyclerviewLoadMoreLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener
    }

    fun setLoaded() {
        isLoading = false
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            news[position].isLast -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_WALL_ITEM
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun setData(list: MutableList<NewsModel>) {
        this.news.clear()
        this.news.addAll(list)
        notifyDataSetChanged()
    }
    fun clearData(){
        this.news.clear()
    }
}