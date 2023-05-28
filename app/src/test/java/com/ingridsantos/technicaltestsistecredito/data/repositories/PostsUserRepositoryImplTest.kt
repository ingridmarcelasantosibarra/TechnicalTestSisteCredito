package com.ingridsantos.technicaltestsistecredito.data.repositories

import com.ingridsantos.technicaltestsistecredito.data.api.PostsUserApi
import com.ingridsantos.technicaltestsistecredito.data.models.PostsDTO
import com.ingridsantos.technicaltestsistecredito.domain.models.Post
import com.ingridsantos.technicaltestsistecredito.domain.models.UnknownError
import com.ingridsantos.technicaltestsistecredito.domain.repositories.DomainExceptionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.collect
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class PostsUserRepositoryImplTest {

    private val postsUserApi = mockk<PostsUserApi>()

    private val domainExceptionRepository = mockk<DomainExceptionRepository>()

    private lateinit var postsUserRepositoryImpl: PostsUserRepositoryImpl

    @Before
    fun setUp() {
        postsUserRepositoryImpl = PostsUserRepositoryImpl(
            domainExceptionRepository = domainExceptionRepository,
            postsUserApi = postsUserApi
        )
    }

    @Test
    fun getPosts() = runBlocking {
        val postsDTO = mockk<PostsDTO>()
        val post = mockk<Post>()
        val listPostsDTO = listOf(postsDTO)
        val listPost = listOf(post)

        coEvery { postsUserApi.getPosts(1) } returns listPostsDTO
        every { postsDTO.toPost() } returns post

        postsUserRepositoryImpl.getPosts(1).collect {
            assertEquals(it, listPost)
        }

        coVerify { postsUserApi.getPosts(1) }
        verify { postsDTO.toPost() }
        confirmVerified(postsDTO, post, postsUserApi)
    }

    @Test
    fun getPostsReturnException() = runBlocking {
        val exception: HttpException = mockk()
        coEvery { postsUserApi.getPosts(1) } throws exception
        every { domainExceptionRepository.manageError(any()) } returns UnknownError()

        postsUserRepositoryImpl.getPosts(1).catch {
            assert(it is UnknownError)
        }.collect()

        coVerify { postsUserApi.getPosts(1) }
        verify { domainExceptionRepository.manageError(any()) }
        confirmVerified(postsUserApi)
    }
}
