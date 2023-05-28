package com.ingridsantos.technicaltestsistecredito.domain.repositories

import com.ingridsantos.technicaltestsistecredito.domain.models.DomainException

interface DomainExceptionRepository {
    fun manageError(error: Throwable): DomainException
}
