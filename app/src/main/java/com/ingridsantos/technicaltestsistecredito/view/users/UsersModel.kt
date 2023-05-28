package com.ingridsantos.technicaltestsistecredito.view.users

import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain

data class UsersModel(
    val result: List<UserDomain> = listOf(),
    val saveUsers: List<UserDomain> = listOf(),
    val isError: String = "",
    val isLoading: Boolean = false
)
