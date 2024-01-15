package com.example.fundamentalapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundamentalapp.response.ItemsItem
import com.example.fundamentalapp.response.UserResponse
import com.example.fundamentalapp.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _dataUser = MutableLiveData<List<ItemsItem>>()
    val dataUser: LiveData<List<ItemsItem>> = _dataUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        dataUser("A")
    }

    fun dataUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _dataUser.value = response.body()?.items
                    }
                } else {
                    Log.d(TAG, "onFailure ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure ${t.message} response data")
            }

        })

    }


    companion object {
        const val TAG = "MAIN VIEW MODEL"
    }
}