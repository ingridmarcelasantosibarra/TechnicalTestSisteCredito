package com.ingridsantos.technicaltestsistecredito.domain.repositories

import com.ingridsantos.technicaltestsistecredito.domain.models.Post
import kotlinx.coroutines.flow.Flow

interface PostsUserRepository {

    fun getPosts(
        userId: Int
    ): Flow<List<Post>>
}
