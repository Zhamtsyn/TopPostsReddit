package com.example.toppostsreddit.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String,
    val created_utc:String,
    val title:String,
    val num_comments:Int,
    val thumbnail:String,
    val url:String
)