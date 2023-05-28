package com.ingridsantos.technicaltestsistecredito.domain.usecases

import com.ingridsantos.technicaltestsistecredito.domain.models.DomainException
import com.ingridsantos.technicaltestsistecredito.domain.models.Post
import com.ingridsantos.technicaltestsistecredito.domain.repositories.PostsUserRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PostsUserUCTest {

    private val postsUserRepository = mockk<PostsUserRepository>()
    private lateinit var postsUC: PostsUserUC

    @Before
    fun setup() {
        postsUC = PostsUserUC(postsUserRepository)
    }

    @Test
    fun whenGetPostsShouldReturnsPosts() {
        runBlocking {
            val post = mockk<Post>()
            val posts = listOf(post)
            every { postsUserRepository.getPosts(1) } returns flowOf(posts)

            postsUC.invoke(1).collect { postsList ->
                assertEquals(postsList, posts)
            }

            verify { postsUserRepository.getPosts(1) }
            confirmVerified(post)
        }
    }

    @Test
    fun whenGetPostsIsCalledReturnException() {
        runBlocking {
            val exception = DomainException()
            val flow = flow<List<Post>> {
                throw exception
            }

            every { postsUserRepository.getPosts(1) } returns flow

            postsUC.invoke(1).catch { error ->
                assert(error is DomainException)
            }.collect()

            verify { postsUserRepository.getPosts(1) }
        }
    }

    @After
    fun tearDown() {
        confirmVerified(postsUserRepository)
    }
}
