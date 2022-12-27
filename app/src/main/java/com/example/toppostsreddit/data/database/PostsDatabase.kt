package com.example.toppostsreddit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Post::class], version = 3)
abstract class PostsDatabase:RoomDatabase() {
    abstract fun postDao():PostDao
}