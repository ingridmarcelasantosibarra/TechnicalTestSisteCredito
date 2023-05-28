package com.ingridsantos.technicaltestsistecredito.data.models

import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.squareup.moshi.Json

data class UserDTO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "username")
    val username: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "phone")
    val phone: String
) {
    fun toUser() : UserDomain {
        return UserDomain(
            id = id,
            username = username,
            email = email,
            phone = phone
        )
    }
}
