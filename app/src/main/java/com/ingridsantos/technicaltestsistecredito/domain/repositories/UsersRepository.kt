package com.ingridsantos.technicaltestsistecredito.domain.repositories

import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun getUsers(): Flow<List<UserDomain>>
}
