package com.ingridsantos.technicaltestsistecredito.view.posts

import com.ingridsantos.technicaltestsistecredito.domain.models.Post

data class PostsModel(
    val result: List<Post> = listOf(),
    val isError: String = "",
    val isLoading: Boolean = false
)
