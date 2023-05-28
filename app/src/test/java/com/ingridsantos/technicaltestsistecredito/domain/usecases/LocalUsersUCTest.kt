package com.ingridsantos.technicaltestsistecredito.domain.usecases

import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.ingridsantos.technicaltestsistecredito.domain.repositories.local.LocalUsersRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class LocalUsersUCTest {

    private val localUsersRepository = mockk<LocalUsersRepository>()

    private lateinit var localUsersUC: LocalUsersUC

    @Before
    fun setup() {
        localUsersUC = LocalUsersUC(localUsersRepository)
    }

    @Test
    fun whenGetUsersShouldReturnUsers() {
        runBlocking {
            val user = mockk<UserDomain>()
            val users = listOf(user)

            every { localUsersRepository.getUsers() } returns flowOf(users)

            localUsersUC.getUsers()

            verify { localUsersRepository.getUsers() }
            confirmVerified(user)
        }
    }

    @Test
    fun whenInsertAllUsersReturnSuccess() {
        runBlocking {
            val usersSuccess = listOf(1L, 2L)
            val user = mockk<UserDomain>()
            val users = listOf(user)

            coEvery { localUsersRepository.deleteAll() } returns 1
            coEvery { localUsersRepository.insertAll(users) } returns usersSuccess

            localUsersUC.insertAll(users)

            coVerify { localUsersRepository.insertAll(users) }
            coVerify { localUsersRepository.deleteAll() }
            confirmVerified(localUsersRepository, user)
        }
    }

    @Test
    fun whenDeleteAllSuccess() {
        val deleteReturn = (0..11).random()
        runBlocking {
            coEvery { localUsersRepository.deleteAll() } returns deleteReturn

            localUsersUC.deleteAll()

            coVerify { localUsersRepository.deleteAll() }
        }
    }

    @After
    fun tearDown() {
        confirmVerified(localUsersRepository)
    }
}
