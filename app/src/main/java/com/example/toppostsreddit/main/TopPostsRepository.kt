package com.example.toppostsreddit.main

import com.example.toppostsreddit.data.TopPostsApi
import javax.inject.Inject

class TopPostsRepository @Inject constructor(private val api: TopPostsApi) {
    suspend fun getTopPosts() = api.getTopPosts()
}