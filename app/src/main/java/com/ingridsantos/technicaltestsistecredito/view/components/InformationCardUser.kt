package com.ingridsantos.technicaltestsistecredito.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.ingridsantos.technicaltestsistecredito.view.users.FileInfo

@Composable
fun InformationCardUser(
    userDomain: UserDomain,
    showPosts: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = userDomain.username,
            fontSize = 16.sp,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(start = 8.dp)
        )
        FileInfo(imageVector = Icons.Filled.Call, description = userDomain.phone)
        FileInfo(imageVector = Icons.Filled.Email, description = userDomain.email)
        if (showPosts) {
            Box(modifier = Modifier.align(Alignment.End)) {
                Text(
                    text = "Ver posts",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}
