package com.ingridsantos.technicaltestsistecredito.domain.usecases

import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.ingridsantos.technicaltestsistecredito.domain.repositories.UsersRepository
import com.ingridsantos.technicaltestsistecredito.domain.repositories.local.LocalUsersRepository
import javax.inject.Inject

class UsersUC @Inject constructor(
    private val usersRepository: UsersRepository,
    private val localUsersRepository: LocalUsersRepository
) {

    operator fun invoke() = usersRepository.getUsers()

    fun filterUsers(
        filter: CharSequence,
        users: List<UserDomain>?
    ): List<UserDomain> {
        return if (users.isNullOrEmpty().not()) {
            users!!.filter {
                it.username.startsWith(filter, true)
            }
        } else {
            listOf()
        }
    }
}
