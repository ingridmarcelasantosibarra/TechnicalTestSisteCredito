package com.ingridsantos.technicaltestsistecredito.data.repositories

import com.ingridsantos.technicaltestsistecredito.data.api.UsersApi
import com.ingridsantos.technicaltestsistecredito.data.models.UserDTO
import com.ingridsantos.technicaltestsistecredito.domain.models.UnknownError
import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.ingridsantos.technicaltestsistecredito.domain.repositories.DomainExceptionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException

class UserRepositoryImplTest {

    private val userApi = mockk<UsersApi>()
    private val domainExceptionRepository = mockk<DomainExceptionRepository>()

    private lateinit var usersRepositoryImpl: UsersRepositoryImpl

    @BeforeEach
    fun setup() {
        usersRepositoryImpl = UsersRepositoryImpl(
            userApi = userApi,
            domainExceptionRepository = domainExceptionRepository
        )
    }

    @Test
    fun getPosts() = runTest {
        val users = mockk<UserDomain>()
        val userDTO = mockk<UserDTO>()
        val listUserDTO = listOf(userDTO)
        val listUsers = listOf(users)

        coEvery { userApi.getUsers() } returns listUserDTO
        every { userDTO.toUser() } returns users

        usersRepositoryImpl.getUsers().collect {
            assertEquals(it, listUsers)
        }

        coVerify { userApi.getUsers() }
        verify { userDTO.toUser() }
        confirmVerified(userDTO, users)
    }

    @Test
    fun getUsersReturnException() = runTest {
        val exception: HttpException = mockk()
        coEvery { userApi.getUsers() } throws exception
        every { domainExceptionRepository.manageError(any()) } returns UnknownError()

        usersRepositoryImpl.getUsers().catch {
            assert(it is UnknownError)
        }.collect()

        coVerify { userApi.getUsers() }
        verify { domainExceptionRepository.manageError(any()) }
        confirmVerified(exception)
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(userApi, domainExceptionRepository)
    }
}
