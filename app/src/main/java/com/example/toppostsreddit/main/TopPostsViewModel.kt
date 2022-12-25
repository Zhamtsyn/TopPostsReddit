package com.example.toppostsreddit.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.toppostsreddit.paging.TopPostsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopPostsViewModel @Inject constructor(private val topPostsRepository: TopPostsRepository) :
    ViewModel() {

    val listData = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
            prefetchDistance = 1,
            initialLoadSize = 10
        )
    ) {
        TopPostsPagingSource(topPostsRepository)
    }.flow.cachedIn(viewModelScope)

}