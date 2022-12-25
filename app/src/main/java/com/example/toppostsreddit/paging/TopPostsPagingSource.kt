package com.example.toppostsreddit.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.toppostsreddit.data.models.Children
import com.example.toppostsreddit.main.TopPostsRepository

class TopPostsPagingSource(private val topPostsRepository: TopPostsRepository):PagingSource<Int, Children>() {
    override fun getRefreshKey(state: PagingState<Int, Children>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition)?:return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Children> {
        return try{
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize
            val response = topPostsRepository.getTopPosts()

            val data = checkNotNull(response.body()?.data?.children)
            val nextKey = if (data.size<pageSize) null else currentPage+1
            val prevKey = if(currentPage==1) null else currentPage-1
            return LoadResult.Page(
                data, prevKey, nextKey
            )
        } catch(e:Exception){
            LoadResult.Error(e)
        }
    }
}