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


class NewsRecyclerViewAdapter() : PagingDataAdapter<NewsModel,RecyclerView.ViewHolder>(REPO_COMPARATOR) {


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