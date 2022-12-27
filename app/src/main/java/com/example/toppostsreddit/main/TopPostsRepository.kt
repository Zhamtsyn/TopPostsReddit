package com.example.toppostsreddit.main

import androidx.room.withTransaction
import com.example.toppostsreddit.data.TopPostsApi
import com.example.toppostsreddit.data.database.Post
import com.example.toppostsreddit.data.database.PostsDatabase
import com.example.toppostsreddit.util.networkBoundResource
import javax.inject.Inject

class TopPostsRepository @Inject constructor(private val api: TopPostsApi, private val db:PostsDatabase) {

    private val postDao = db.postDao()

    fun getTopPosts() = networkBoundResource(
        query = { postDao.getAllPosts() },
        fetch = {
            api.getTopPosts().body()?.data?.children?.map{
                Post(null,it.data.author, it.data.created_utc.toString(), it.data.title, it.data.num_comments, it.data.thumbnail, it.data.url)
            }
            },
        saveFetchResult = {
            db.withTransaction {
                postDao.deleteAllPosts()
                if (it != null) {
                    postDao.insertPosts(it)
                }

            }
        }
    )
}