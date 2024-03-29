package com.android.bestpracticechallenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.bestpracticechallenge.databinding.NewsFragmentBinding
import com.android.bestpracticechallenge.ui.LoaderStateAdapter
import com.android.bestpracticechallenge.ui.adapters.NewsRecyclerViewAdapter
import com.android.bestpracticechallenge.ui.viewmodels.NewsViewModel
import kotlinx.coroutines.launch


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
        fetchNews()
    }

    private fun fetchNews() {
        viewModel.fetchNews().observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        })
    }

    private fun initRecyclerView() {
        binding.newsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = NewsRecyclerViewAdapter()
        binding.newsRecyclerView.adapter = adapter.withLoadStateFooter(LoaderStateAdapter())
    }

}

