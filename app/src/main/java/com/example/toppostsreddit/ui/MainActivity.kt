package com.example.toppostsreddit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toppostsreddit.main.TopPostsAdapter
import com.example.toppostsreddit.main.TopPostsViewModel
import com.example.toppostsreddit.databinding.ActivityMainBinding
import com.example.toppostsreddit.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var topPostsAdapter: TopPostsAdapter
    private val viewModel: TopPostsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getTopPosts()
        initRecyclerView()

        viewModel.vm.observe(this, { response->
            when(response){
                is Resource.Success->{
                    response.data.let{posts->
                        if (posts!=null){
                            topPostsAdapter.differ.submitList(posts.data.children)
                        }
                    }
                }
                is Resource.Error ->{
                    response.message?.let{
                        Log.e("TAG", "An error occurred: $it")
                    }
                }
                is Resource.Loading->{

                }
            }
        })


    }

    private fun initRecyclerView() {
        binding.rvPosts.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        topPostsAdapter = TopPostsAdapter()
        binding.rvPosts.adapter = topPostsAdapter

    }
}