package com.example.toppostsreddit.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopPostsViewModel @Inject constructor(private val topPostsRepository: TopPostsRepository) :
    ViewModel() {

    val topPosts = topPostsRepository.getTopPosts().asLiveData()
}