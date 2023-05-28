package com.ingridsantos.technicaltestsistecredito.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain

@Entity(tableName = "user")
data class LocalUser(
    @PrimaryKey
    val id: Int,
    val username: String,
    val email: String,
    val phone: String
) {
    constructor(userDomain: UserDomain) : this (
        id = userDomain.id,
        username = userDomain.username,
        phone = userDomain.phone,
        email = userDomain.email
    )

    fun toDomainUser(): UserDomain {
        return UserDomain(
            id = this.id,
            username = this.username,
            email = this.email,
            phone = this.phone
        )
    }
}
