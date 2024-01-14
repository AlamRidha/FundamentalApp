package com.example.fundamentalapp.retrofit

import com.example.fundamentalapp.response.DetailResponse
import com.example.fundamentalapp.response.ItemsItem
import com.example.fundamentalapp.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

//  create ApiService
interface ApiService {

    @GET("users")
    fun getUser(): Call<ItemsItem>

    @GET("search/users")
    fun searchUser(
        @Query("q") query: String?
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollower(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

}