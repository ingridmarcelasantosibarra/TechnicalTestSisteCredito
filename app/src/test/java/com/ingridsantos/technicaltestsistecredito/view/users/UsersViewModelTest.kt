package com.ingridsantos.technicaltestsistecredito.view.users

import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.ingridsantos.technicaltestsistecredito.domain.usecases.LocalUsersUC
import com.ingridsantos.technicaltestsistecredito.domain.usecases.UsersUC
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UsersViewModelTest {

   /* private val userUC = mockk<UsersUC>()
    private val localUserUC = mockk<LocalUsersUC>()

    private lateinit var usersViewModel: UsersViewModel

    @Before
    fun setUp() {
        usersViewModel = UsersViewModel(
            userUC = userUC,
            localUsersUC = localUserUC
        )
    }

    @Test
    fun getFilterReference() {
        val user = mockk<UserDomain>()
        val userFilter = mockk<UserDomain>()
        val listUser = listOf(user)
        val listUserFilter = listOf(userFilter)

        every { userUC.filterUsers("i", listUser) } returns listUserFilter

        usersViewModel.getFilterReference("i", listUser)

        verify { userUC.filterUsers("i", listUser) }
        confirmVerified(userUC)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getUsersNetwork() = runTest {
        val user = mockk<UserDomain>()
        val userLocal = mockk<UserDomain>()
        val listUser = listOf(user)
        val listLocalUser = listOf(userLocal)
        var state = usersViewModel.usersState.value
        val successResult = flow {
            emit(listUser)
        }
        val successLocal = flow { emit(listLocalUser) }

        val job = launch {
            every { userUC.invoke() } returns successResult
            coEvery { localUserUC.getUsers() } returns successLocal
        }

        usersViewModel.getUsers()

        job.join()

        state = usersViewModel.usersState.value

        Assert.assertEquals(listLocalUser, state)
        verify { userUC.invoke() }
        coVerify { localUserUC.getUsers() }
    }*/
}
