package com.android.bestpracticechallenge.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.bestpracticechallenge.App
import com.android.bestpracticechallenge.bean.NewsModel
import com.android.bestpracticechallenge.databinding.ItemNewsRecyclerviewLayoutBinding
import com.bumptech.glide.Glide


class NewsRecyclerViewAdapter() :
    PagingDataAdapter<NewsModel, RecyclerView.ViewHolder>(REPO_COMPARATOR) {


    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<NewsModel>() {
            override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean =
                oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WallPostsViewHolder(
            ItemNewsRecyclerviewLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? WallPostsViewHolder)?.onBind()
    }

    inner class WallPostsViewHolder(private val binding: ItemNewsRecyclerviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: NewsModel

        fun onBind() {
            model = getItem(absoluteAdapterPosition)!!
            Glide.with(App.instance.getContext()).load(model.cover)
                .into(binding.newsCoverImageView)
            binding.titleTextView.text = model.titleKA
        }
    }
}
//
//class LoadStateViewHolder(
//    parent: ViewGroup,
//    retry: () -> Unit
//) : RecyclerView.ViewHolder(
//    LayoutInflater.from(parent.context)
//        .inflate(R.layout.item_recyclerview_load_more_layout, parent, false)
//) {
//    private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
//    private val errorMsg: TextView = itemView.findViewById(R.id.tv_error)
//    private val retry: Button = itemView.findViewById<Button>(R.id.retry)
//        .also { it.setOnClickListener { retry.invoke() } }
//
//    fun bind(loadState: LoadState) {
//        if (loadState is LoadState.Error) {
//            errorMsg.text = loadState.error.localizedMessage
//        }
//        progressBar.visibility = toVisibility(loadState is LoadState.Loading)
//        retry.visibility = toVisibility(loadState !is LoadState.Loading)
//        errorMsg.visibility = toVisibility(loadState !is LoadState.Loading)
//    }
//
//    private fun toVisibility(constraint: Boolean): Int = if (constraint) {
//        View.VISIBLE
//    } else {
//        View.GONE
//    }
//}
//
///**
// * Adapter which displays a loading spinner when `state = LoadState.Loading`, and an error
// * message and retry button when `state is LoadState.Error`.
// */
//class MyLoadStateAdapter(
//    private val retry: () -> Unit
//) : LoadStateAdapter<LoadStateViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
//        LoadStateViewHolder(parent, retry)
//
//    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
//        holder.bind(loadState)
//}