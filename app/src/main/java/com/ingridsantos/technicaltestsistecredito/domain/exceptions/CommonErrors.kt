package com.ingridsantos.technicaltestsistecredito.domain.exceptions

import com.google.gson.JsonSyntaxException
import com.ingridsantos.technicaltestsistecredito.domain.models.DomainException
import com.ingridsantos.technicaltestsistecredito.domain.models.InternalErrorException
import com.ingridsantos.technicaltestsistecredito.domain.models.NoConnectivityDomainException
import com.ingridsantos.technicaltestsistecredito.domain.models.NoConnectivityException
import com.ingridsantos.technicaltestsistecredito.domain.models.ParseException
import com.ingridsantos.technicaltestsistecredito.domain.models.TimeOutException
import com.ingridsantos.technicaltestsistecredito.domain.models.UnknownError
import com.squareup.moshi.JsonDataException
import java.net.ConnectException
import java.net.SocketTimeoutException

open class CommonErrors {

    fun manageException(throwable: Throwable): DomainException {
        return manageJavaErrors(throwable)
    }

    fun manageJavaErrors(throwable: Throwable): DomainException {
        return when (throwable) {
            is SocketTimeoutException -> TimeOutException
            is ConnectException -> InternalErrorException(throwable.message ?: String())
            else -> manageParsingExceptions(throwable)
        }
    }

    fun manageParsingExceptions(throwable: Throwable): DomainException {
        return when (throwable) {
            is JsonDataException -> ParseException
            is JsonSyntaxException -> ParseException
            else -> manageOtherException(throwable)
        }
    }

    fun manageOtherException(throwable: Throwable): DomainException {
        return when (throwable) {
            is NoConnectivityException -> NoConnectivityDomainException
            else -> UnknownError(throwable.message.toString())
        }
    }
}
