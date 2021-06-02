package com.android.bestpracticechallenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.bestpracticechallenge.databinding.NewsFragmentBinding
import com.android.bestpracticechallenge.ui.adapters.NewsRecyclerViewAdapter
import com.android.bestpracticechallenge.ui.tools.Tools
import com.android.bestpracticechallenge.ui.viewmodels.NewsViewModel


class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var binding: NewsFragmentBinding
    private lateinit var adapter: NewsRecyclerViewAdapter
//    private lateinit var value: MutableList<NewsModel>

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
//        adapter.setOnLoadMoreListener(loadMoreListener)

    }

//    private val loadMoreListener = object :
//        OnLoadMoreListener {
//        override fun onLoadMore() {
//            if (value.isNotEmpty()) {
//                if (!value[value.size - 1].isLast) {
//                    binding.newsRecyclerView.post {
//                        val newsModel = NewsModel()
//                        newsModel.isLast = true
//                        value.add(newsModel)
//                        adapter.notifyItemInserted(value.size - 1)
//                        viewModel.init(adapter,value[value.size - 1].id.toString())
//                    }
//                }
//            }
//        }
//    }
    private fun observer() {
        viewModel._downloadLiveData.observe(viewLifecycleOwner, {
                Tools.viewVisibility(binding.progressBar)
        })

        viewModel._fetchedNewsLiveData.observe(viewLifecycleOwner, {
            adapter.setData(it.toMutableList())
        })
    }

}

