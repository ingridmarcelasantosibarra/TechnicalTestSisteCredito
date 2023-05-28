package com.ingridsantos.technicaltestsistecredito.data.repositories.local

import com.ingridsantos.technicaltestsistecredito.data.local.dao.UserDao
import com.ingridsantos.technicaltestsistecredito.data.local.entities.LocalUser
import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.ingridsantos.technicaltestsistecredito.domain.repositories.local.LocalUsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUsersRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : LocalUsersRepository {

    override suspend fun insertAll(userDomains: List<UserDomain>): List<Long> {
        return userDao.insertAll(
            userDomains.map {
                LocalUser(it)
            }
        )
    }

    override suspend fun deleteAll() = userDao.deleteAll()

    override fun getUsers(): Flow<List<UserDomain>> {
        return userDao.getUsers().map { listLocalUser ->
            listLocalUser.map {
                it.toDomainUser()
            }
        }
    }
}
