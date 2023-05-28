package com.ingridsantos.technicaltestsistecredito.data.repositories

import com.ingridsantos.technicaltestsistecredito.data.api.UsersApi
import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.ingridsantos.technicaltestsistecredito.domain.repositories.DomainExceptionRepository
import com.ingridsantos.technicaltestsistecredito.domain.repositories.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val userApi: UsersApi,
    private val domainExceptionRepository: DomainExceptionRepository
) : UsersRepository {

    override fun getUsers(): Flow<List<UserDomain>> {
        return flow {
            val users = userApi.getUsers().map {
                it.toUser()
            }
            emit(users)
        }.catch {
            throw domainExceptionRepository.manageError(it)
        }
    }
}
