package com.example.toppostsreddit.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toppostsreddit.data.models.TopPosts
import com.example.toppostsreddit.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TopPostsViewModel @Inject constructor(private val topPostsRepository: TopPostsRepository):ViewModel() {

    val vm: MutableLiveData<Resource<TopPosts>> = MutableLiveData()

    fun getTopPosts(){
        viewModelScope.launch {
            vm.postValue(Resource.Loading())
            val response = topPostsRepository.getTopPosts()
            vm.postValue(handleTopPostsResponse(response))
        }
    }

    private fun handleTopPostsResponse(response: Response<TopPosts>): Resource<TopPosts>{
        if (response.isSuccessful){
            response.body()?.let{result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}