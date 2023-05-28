package com.ingridsantos.technicaltestsistecredito.data.models

import com.ingridsantos.technicaltestsistecredito.domain.models.Post
import com.squareup.moshi.Json

data class PostsDTO(
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "body")
    val body: String
) {
    fun toPost(): Post {
        return Post(
            userId = userId,
            id = id,
            title = title,
            body = body
        )
    }
}
