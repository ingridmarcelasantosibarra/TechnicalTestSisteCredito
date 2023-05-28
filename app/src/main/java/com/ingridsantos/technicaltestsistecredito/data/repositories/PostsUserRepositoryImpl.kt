package com.ingridsantos.technicaltestsistecredito.data.repositories

import com.ingridsantos.technicaltestsistecredito.data.api.PostsUserApi
import com.ingridsantos.technicaltestsistecredito.domain.models.Post
import com.ingridsantos.technicaltestsistecredito.domain.repositories.DomainExceptionRepository
import com.ingridsantos.technicaltestsistecredito.domain.repositories.PostsUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostsUserRepositoryImpl @Inject constructor(
    private val postsUserApi: PostsUserApi,
    private val domainExceptionRepository: DomainExceptionRepository
) : PostsUserRepository {

    override fun getPosts(userId: Int): Flow<List<Post>> {
        return flow {
            val posts = postsUserApi.getPosts(userId).map {
                it.toPost()
            }
            emit(posts)
        }.catch {
            throw domainExceptionRepository.manageError(it)
        }
    }
}
