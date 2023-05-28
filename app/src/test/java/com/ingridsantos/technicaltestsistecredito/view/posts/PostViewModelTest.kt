package com.ingridsantos.technicaltestsistecredito.view.posts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ingridsantos.technicaltestsistecredito.core.CoroutinesTestRule
import com.ingridsantos.technicaltestsistecredito.domain.models.DomainException
import com.ingridsantos.technicaltestsistecredito.domain.models.Post
import com.ingridsantos.technicaltestsistecredito.domain.usecases.PostsUserUC
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostViewModelTest {

    private val postsUserUC = mockk<PostsUserUC>()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()

    private lateinit var postViewModel: PostViewModel

    @Before
    fun setUp() {
        postViewModel = PostViewModel(
            postsUC = postsUserUC
        )
    }

    @Test
    fun getPosts() = runBlocking {
        val posts = mockk<Post>()
        val listPosts = listOf(posts)
        val success = flow { emit(listPosts) }

        coEvery { postsUserUC.invoke(id = 1) } returns success

        postViewModel.getPosts(1)

        coVerify { postsUserUC.invoke(id = 1) }
        confirmVerified(posts)
    }

    @Test
    fun getPostsReturnError() {
        runBlocking {
            val exception = DomainException()
            val flow = flow<List<Post>> {
                throw exception
            }

            every { postsUserUC.invoke(id = 1) } returns flow

            postViewModel.getPosts(1)

            verify { postsUserUC.invoke(id = 1) }
            spyk(flow).catch { }
        }
    }

    @After
    fun tearDown() {
        confirmVerified(postsUserUC)
    }
}
