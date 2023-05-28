package com.ingridsantos.technicaltestsistecredito.view.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.ingridsantos.technicaltestsistecredito.view.components.InformationCardUser
import com.ingridsantos.technicaltestsistecredito.view.components.ScreenAlertMessage
import com.ingridsantos.technicaltestsistecredito.view.components.SearchBarUI

@Composable
fun UsersUI(
    onClick: (UserDomain) -> Unit,
    viewModel: UsersViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getUsers()
    }

    val state by viewModel.usersState.collectAsState()
    var searchText by rememberSaveable { mutableStateOf("") }

    SearchBarUI(
        searchText = searchText,
        placeholderText = "Search User",
        onSearchTextChanged = {
            searchText = it
            viewModel.onEvent(UsersUIEvent.FilterUser(it, state.saveUsers))
        },
        onClearClick = {
            searchText = ""
            viewModel.onEvent(UsersUIEvent.OnClear)
        }
    ) {
        if (state.isError.isNotEmpty()) {
            val message = state.isError
            ScreenAlertMessage(message = "¡Ah occurred an error! $message", color = Color.Red)
        }

        if (state.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        if (state.result.isNotEmpty()) {
            Users(userDomains = state.result) {
                onClick.invoke(it)
            }
        } else {
            ScreenAlertMessage(message = "No matches found", color = Color.Black)
        }
    }
}

@Composable
fun Users(userDomains: List<UserDomain>, onClick: (UserDomain) -> Unit) {
    LazyColumn {
        items(userDomains) { user ->
            UserColumn(userDomain = user) {
                onClick(user)
            }
        }
    }
}

@Composable
fun UserColumn(userDomain: UserDomain, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { onClick() },
        elevation = 10.dp
    ) {
        InformationCardUser(userDomain = userDomain, showPosts = true)
    }
}

@Composable
fun FileInfo(imageVector: ImageVector, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Icon(
            imageVector = imageVector,
            modifier = Modifier,
            contentDescription = description
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(description, fontSize = 14.sp, style = MaterialTheme.typography.body1)
    }
}
