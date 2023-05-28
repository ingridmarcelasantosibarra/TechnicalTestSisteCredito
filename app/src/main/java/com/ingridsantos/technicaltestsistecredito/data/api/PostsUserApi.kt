package com.ingridsantos.technicaltestsistecredito.data.api

import com.ingridsantos.technicaltestsistecredito.data.models.PostsDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsUserApi {

    @GET("/posts")
    suspend fun getPosts(
        @Query("userId") userId: Int
    ): List<PostsDTO>
}
