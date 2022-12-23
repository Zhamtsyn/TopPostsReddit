package com.example.toppostsreddit

import com.example.toppostsreddit.data.TopPostsApi
import javax.inject.Inject

class TopPostsRepository @Inject constructor(private val api: TopPostsApi) {
    suspend fun getTopPosts() = api.getTopPosts()
}