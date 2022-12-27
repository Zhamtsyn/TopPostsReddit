package com.example.toppostsreddit.util

sealed class Resource<T>(
    val data: T? = null,
    val message: Throwable? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: Throwable, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T?=null) : Resource<T>(data)
}