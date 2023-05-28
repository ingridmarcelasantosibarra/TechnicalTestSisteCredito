package com.ingridsantos.technicaltestsistecredito.data.repositories.exception

import com.ingridsantos.technicaltestsistecredito.domain.exceptions.CommonErrors
import com.ingridsantos.technicaltestsistecredito.domain.models.DomainException
import com.ingridsantos.technicaltestsistecredito.domain.repositories.DomainExceptionRepository

class ExceptionPostsUserRepositoryImpl : CommonErrors(), DomainExceptionRepository {

    override fun manageError(error: Throwable): DomainException {
        return manageException(error)
    }
}
