package com.ingridsantos.technicaltestsistecredito.data.api

import com.ingridsantos.technicaltestsistecredito.data.models.UserDTO
import retrofit2.http.GET

interface UsersApi {

    @GET("/users")
    suspend fun getUsers(): List<UserDTO>
}
