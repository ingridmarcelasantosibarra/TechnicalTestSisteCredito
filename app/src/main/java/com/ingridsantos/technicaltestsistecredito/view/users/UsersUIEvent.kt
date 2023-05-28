package com.ingridsantos.technicaltestsistecredito.view.users

import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain

sealed class UsersUIEvent {
    data class FilterUser(val filter: String, val users: List<UserDomain>) : UsersUIEvent()
    object OnClear : UsersUIEvent()
}
