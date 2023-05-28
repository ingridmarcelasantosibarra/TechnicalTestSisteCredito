package com.ingridsantos.technicaltestsistecredito.utils

import com.ingridsantos.technicaltestsistecredito.domain.models.DomainException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal fun <T> Flow<T>.handleViewModelExceptions(onError: (DomainException) -> Unit): Flow<T> =
    flow {
        try {
            collect { value -> emit(value) }
        } catch (e: DomainException) {
            onError(e)
        }
    }
