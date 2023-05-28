package com.ingridsantos.technicaltestsistecredito.domain.exceptions

import com.ingridsantos.technicaltestsistecredito.R
import com.ingridsantos.technicaltestsistecredito.domain.models.DomainException
import com.ingridsantos.technicaltestsistecredito.domain.models.InternalErrorException
import com.ingridsantos.technicaltestsistecredito.domain.models.NoConnectivityDomainException
import com.ingridsantos.technicaltestsistecredito.domain.models.ParseException
import com.ingridsantos.technicaltestsistecredito.domain.models.TimeOutException

class ExceptionHandler {

    fun manageException(domainException: DomainException): Int =
        when (domainException) {
            is TimeOutException -> R.string.error_time_out
            is InternalErrorException -> R.string.error_internal_error_exception
            is ParseException -> R.string.error_parsing_error
            is NoConnectivityDomainException -> R.string.error_internet_connection
            else -> R.string.error_some_wrong
        }
}
