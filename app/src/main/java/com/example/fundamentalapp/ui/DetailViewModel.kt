package com.example.fundamentalapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundamentalapp.response.DetailResponse
import com.example.fundamentalapp.response.ItemsItem
import com.example.fundamentalapp.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _detailUser = MutableLiveData<DetailResponse>()
    val detailUser: LiveData<DetailResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowUser = MutableLiveData<List<ItemsItem>>()
    val listFollowUser: LiveData<List<ItemsItem>> = _listFollowUser

    fun detailUser(userName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(userName)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _detailUser.postValue(response.body())
                    } else {
                        Log.d(TAG, "onFailure ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure ${t.message}")
            }
        })
    }

    fun getFollowers(userName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollower(userName)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _listFollowUser.postValue(response.body())
                        Log.d(TAG, "data is ${response.body()}")
                    }
                } else {
                    Log.d(TAG, "onFailure ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure ${t.message}")
            }
        })
    }

    fun getFollowing(userName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(userName)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _listFollowUser.postValue(response.body())
                        Log.d(TAG, "data is ${response.body()}")
                    }
                } else {
                    Log.d(TAG, "onFailure ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure ${t.message}")
            }
        })
    }

    companion object {
        const val TAG = "Detail View Model"
    }
}