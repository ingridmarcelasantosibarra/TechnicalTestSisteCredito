package com.ingridsantos.technicaltestsistecredito.domain.usecases

import com.ingridsantos.technicaltestsistecredito.domain.repositories.PostsUserRepository
import javax.inject.Inject

class PostsUserUC @Inject constructor(
    private val postsUserRepository: PostsUserRepository
) {

    operator fun invoke(id: Int) = postsUserRepository.getPosts(id)
}
