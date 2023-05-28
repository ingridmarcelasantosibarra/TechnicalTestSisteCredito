package com.ingridsantos.technicaltestsistecredito.domain.usecases

import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.ingridsantos.technicaltestsistecredito.domain.repositories.local.LocalUsersRepository
import javax.inject.Inject

class LocalUsersUC @Inject constructor(
    private val localUsersRepository: LocalUsersRepository
) {

    fun getUsers() = localUsersRepository.getUsers()

    suspend fun insertAll(userDomains: List<UserDomain>): List<Long> {
        localUsersRepository.deleteAll()
        return localUsersRepository.insertAll(userDomains)
    }

    suspend fun deleteAll() = localUsersRepository.deleteAll()
}
