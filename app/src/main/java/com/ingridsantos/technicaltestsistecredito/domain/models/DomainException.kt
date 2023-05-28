package com.ingridsantos.technicaltestsistecredito.domain.models

import java.io.IOException

open class DomainException(override val message: String = "") : Throwable(message)
data class InternalErrorException(override val message: String) : DomainException()
data class UnknownError(override val message: String = "") : DomainException()
object TimeOutException : DomainException()
object ParseException : DomainException()
object NoConnectivityDomainException : DomainException()
object NoConnectivityException : IOException()
