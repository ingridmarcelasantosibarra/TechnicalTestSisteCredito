package com.ingridsantos.technicaltestsistecredito.view.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.ingridsantos.technicaltestsistecredito.domain.usecases.LocalUsersUC
import com.ingridsantos.technicaltestsistecredito.domain.usecases.UsersUC
import com.ingridsantos.technicaltestsistecredito.utils.handleViewModelExceptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userUC: UsersUC,
    private val localUsersUC: LocalUsersUC
) : ViewModel() {

    private val _usersState = MutableStateFlow(UsersModel())
    val usersState = _usersState.asStateFlow()

    private fun getUsersNetwork() {
        viewModelScope.launch {
            userUC.invoke()
                .onStart {
                    _usersState.value = UsersModel(isLoading = true)
                }
                .onCompletion {
                    _usersState.value = UsersModel(isLoading = false)
                }
                .handleViewModelExceptions { domainException ->
                    _usersState.value = UsersModel(isError = domainException.message)
                }
                .collect {
                    if (it.isNotEmpty()) {
                        _usersState.value = UsersModel(result = it)
                        localUsersUC.insertAll(it)
                    }
                }
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            localUsersUC.getUsers().collect {
                _usersState.value = UsersModel(saveUsers = it)
                if (it.isEmpty()) {
                    getUsersNetwork()
                } else {
                    _usersState.value = UsersModel(isLoading = false, result = it)
                }
            }
        }
    }

    fun getFilterReference(
        filter: String,
        users: List<UserDomain>?
    ) {
        viewModelScope.launch {

            val users = userUC.filterUsers(
                filter = filter,
                users = users
            )

            _usersState.value = _usersState.value.copy(result = users)
        }
    }

    fun onClearClick() {
        viewModelScope.launch {
            localUsersUC.getUsers().collect {
                _usersState.value = UsersModel(result = it)
            }
        }
    }

    fun onEvent(event: UsersUIEvent) {
        when(event) {
           /* is UIEvent.AccountChanged -> {
                _uiState.value = _uiState.value.copy(
                    accountNumber = event.account
                )
            }
           */
            is UsersUIEvent.FilterUser -> {
                getFilterReference(filter = event.filter, listOf())
            }
        }
    }
}
