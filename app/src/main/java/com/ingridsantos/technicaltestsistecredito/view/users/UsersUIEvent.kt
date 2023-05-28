package com.ingridsantos.technicaltestsistecredito.view.users

sealed class UsersUIEvent {
    data class FilterUser(val filter: String) : UsersUIEvent()
    //   data class FilterUser(val name: String): UsersUIEvent()
}
