package com.ingridsantos.technicaltestsistecredito.domain.repositories.local

import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import kotlinx.coroutines.flow.Flow

interface LocalUsersRepository {

    suspend fun insertAll(userDomains: List<UserDomain>): List<Long>

    suspend fun deleteAll(): Int

    fun getUsers(): Flow<List<UserDomain>>
}
