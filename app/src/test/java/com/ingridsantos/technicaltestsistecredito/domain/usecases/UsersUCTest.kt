package com.ingridsantos.technicaltestsistecredito.domain.usecases

import com.ingridsantos.technicaltestsistecredito.domain.models.DomainException
import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.ingridsantos.technicaltestsistecredito.domain.repositories.UsersRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class UsersUCTest {
    private val userRepository = mockk<UsersRepository>()

    private lateinit var usersUC: UsersUC

    @Before
    fun setup() {
        usersUC = UsersUC(userRepository)
    }

    @Test
    fun whenGetUsersShouldReturnSuccessUsers() {
        runBlocking {
            val user = mockk<UserDomain>()
            val listUser = listOf(user)
            every { userRepository.getUsers() } returns flowOf(listUser)

            usersUC.invoke().collect { users ->
                assertEquals(users, listUser)
            }

            verify { userRepository.getUsers() }
            confirmVerified(userRepository, user)
        }
    }

    @Test
    fun whenGetUsersIsCalledReturnException() {
        runBlocking {
            val exception = DomainException()
            val flow = flow<List<UserDomain>> {
                throw exception
            }

            every { userRepository.getUsers() } returns flow

            usersUC.invoke().catch { error ->
                assert(error is DomainException)
            }.collect()

            verify { userRepository.getUsers() }
        }
    }

    @Test
    fun whenUsersFilterIsCalledShouldReturnUser() {
        val user = mockk<UserDomain>()
        val users = listOf(user)

        every { user.username } returns "ingrid"

        val result = usersUC.filterUsers("i", users)

        assertEquals(result, users)
        verify(exactly = 1) { user.username }
        confirmVerified(user)
    }

    @Test
    fun whenUsersFilterIsCalledShouldReturnEmpty() {
        val user = mockk<UserDomain>()
        val users = listOf(user)

        every { user.username } returns "ingrid"

        val result = usersUC.filterUsers("m", users)

        assertEquals(result, listOf<UserDomain>())
        verify(exactly = 1) { user.username }
        confirmVerified(user)
    }

    @Test
    fun whenUsersFilterIsCalledShouldReturnEmptyCase2() {
        val users = listOf<UserDomain>()

        val result = usersUC.filterUsers("m", users)

        assertEquals(result, listOf<UserDomain>())
    }

    @After
    fun tearDown() {
        confirmVerified(userRepository)
    }
}
