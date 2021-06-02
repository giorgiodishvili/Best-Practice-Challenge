package com.android.bestpracticechallenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.bestpracticechallenge.bean.NewsModel
import com.android.bestpracticechallenge.databinding.NewsFragmentBinding
import com.android.bestpracticechallenge.ui.adapters.NewsRecyclerViewAdapter
import com.android.bestpracticechallenge.ui.interfaces.OnLoadMoreListener
import com.android.bestpracticechallenge.ui.viewmodels.NewsViewModel


class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var binding: NewsFragmentBinding
    private lateinit var adapter: NewsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewsFragmentBinding.inflate(
            inflater, container, false
        )
        init()
        return binding.root
    }

    private fun init() {
        initRecyclerView()
        viewModel.init()
        observer()
    }

    private fun initRecyclerView() {
        binding.newsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = NewsRecyclerViewAdapter(binding.newsRecyclerView)
        binding.newsRecyclerView.adapter = adapter
        adapter.setOnLoadMoreListener(loadMoreListener)

    }

    private val loadMoreListener = object :
        OnLoadMoreListener {
        override fun onLoadMore(newsModel: NewsModel, nextElementIndex: Int) {
            if (!newsModel.isLast) {
                binding.newsRecyclerView.post {
                    viewModel.init()
                }
            }else {
                binding.newsRecyclerView.findViewHolderForAdapterPosition(nextElementIndex)!!.itemView.visibility=GONE
            }
        }
    }

    private fun observer() {
        viewModel._fetchedNewsLiveData.observe(viewLifecycleOwner, {
            adapter.addData(it.toMutableList())

            if (binding.newsRecyclerView.visibility == INVISIBLE) {
                binding.newsRecyclerView.visibility = VISIBLE
                binding.progressBar.visibility = GONE
            }
        })
    }

}

