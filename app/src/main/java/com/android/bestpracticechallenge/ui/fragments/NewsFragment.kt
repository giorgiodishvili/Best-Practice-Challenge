package com.android.bestpracticechallenge.ui.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.bestpracticechallenge.bean.NewsModel
import com.android.bestpracticechallenge.databinding.NewsFragmentBinding
import com.android.bestpracticechallenge.ui.adapters.NewsRecyclerViewAdapter
import com.android.bestpracticechallenge.ui.interfaces.OnLoadMoreListener
import com.android.bestpracticechallenge.ui.tools.Tools
import com.android.bestpracticechallenge.ui.viewmodels.NewsViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope


class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var binding: NewsFragmentBinding
    private lateinit var adapter: NewsRecyclerViewAdapter
    private var value: MutableList<NewsModel> = mutableListOf()
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
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
        adapter = NewsRecyclerViewAdapter(binding.newsRecyclerView)
        binding.newsRecyclerView.adapter = adapter
        adapter.setOnLoadMoreListener(loadMoreListener)

    }

    private val loadMoreListener = object :
        OnLoadMoreListener {
        override fun onLoadMore() {
            if (value.isNotEmpty()) {
                if (!value[value.size - 1].isLast) {
                    binding.newsRecyclerView.post {
                        val newsModel = NewsModel()
                        newsModel.isLast = true
                        value.add(newsModel)
                        adapter.addToList(newsModel)
                        viewModel.init()
                    }
                }
            }
        }
    }
    private fun observer() {
        viewModel._downloadLiveData.observe(viewLifecycleOwner, {
            Tools.viewVisibility(binding.progressBar)
        })

        viewModel._fetchedNewsLiveData.observe(viewLifecycleOwner, {
            val lastPosition = value.size
            if (lastPosition > 0) {
                value.removeAt(value.size - 1)
                adapter.notifyItemRemoved(value.size - 1)
            }
            adapter.setLoaded()
            value.addAll(it.toMutableList())
            if (value.isNotEmpty() && value.size != 1) adapter.notifyItemMoved(
                lastPosition,
                value.size - 1
            )
            else adapter.notifyDataSetChanged()

            adapter.setData(value)
        })
    }

}

