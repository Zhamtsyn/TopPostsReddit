package com.example.toppostsreddit.data

import com.example.toppostsreddit.data.models.TopPosts
import retrofit2.Response
import retrofit2.http.GET

interface TopPostsApi {

    @GET
    fun getTopPosts(): Response<TopPosts>
}