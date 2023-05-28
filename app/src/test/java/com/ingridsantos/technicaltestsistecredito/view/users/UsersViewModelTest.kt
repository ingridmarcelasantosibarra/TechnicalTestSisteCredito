package com.ingridsantos.technicaltestsistecredito.view.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ingridsantos.technicaltestsistecredito.core.CoroutinesTestRule
import com.ingridsantos.technicaltestsistecredito.domain.models.DomainException
import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.ingridsantos.technicaltestsistecredito.domain.usecases.LocalUsersUC
import com.ingridsantos.technicaltestsistecredito.domain.usecases.UsersUC
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

class UsersViewModelTest {

    private val userUC = mockk<UsersUC>()
    private val localUserUC = mockk<LocalUsersUC>()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()

    private lateinit var usersViewModel: UsersViewModel

    @Before
    fun setUp() {
        usersViewModel = UsersViewModel(
            userUC = userUC,
            localUsersUC = localUserUC
        )
    }

    @Test
    fun getUsersOfLocal() = runBlocking {
        val userLocal = mockk<UserDomain>()
        val listLocalUser = listOf(userLocal)
        val successLocal = flow { emit(listLocalUser) }

        coEvery { localUserUC.getUsers() } returns successLocal

        usersViewModel.getUsers()

        coVerify { localUserUC.getUsers() }
        confirmVerified(userLocal)
    }

    @Test
    fun getUsers() = runBlocking {
        val user = mockk<UserDomain>()
        val listLocalUser = listOf<UserDomain>()
        val listUser = listOf(user)
        val successLocal = flow { emit(listLocalUser) }
        val success = flow { emit(listUser) }

        coEvery { localUserUC.getUsers() } returns successLocal
        coEvery { userUC.invoke() } returns success
        coEvery { localUserUC.insertAll(listUser) }

        usersViewModel.getUsers()

        coVerify { localUserUC.getUsers() }
        coVerify { userUC.invoke() }
        coVerify { localUserUC.insertAll(listUser) }
        confirmVerified(user)
    }

    @Test
    fun getUsersReturnError() {
        runBlocking {
            val listLocalUser = listOf<UserDomain>()
            val successLocal = flow { emit(listLocalUser) }
            val exception = DomainException()
            val flow = flow<List<UserDomain>> {
                throw exception
            }

            coEvery { localUserUC.getUsers() } returns successLocal
            every { userUC.invoke() } returns flow

            usersViewModel.getUsers()

            coVerify { localUserUC.getUsers() }
            verify { userUC.invoke() }
            spyk(flow).catch { }
        }
    }

    @After
    fun tearDown() {
        confirmVerified(localUserUC, userUC)
    }
}
