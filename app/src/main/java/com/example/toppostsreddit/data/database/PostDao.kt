package com.example.toppostsreddit.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.toppostsreddit.data.models.Children
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    fun getAllPosts(): Flow<List<Post>>
    
    @Query("DELETE FROM posts")
    fun deleteAllPosts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>)
}