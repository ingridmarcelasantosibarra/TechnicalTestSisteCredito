package com.ingridsantos.technicaltestsistecredito.view.posts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ingridsantos.technicaltestsistecredito.domain.models.Post
import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.ingridsantos.technicaltestsistecredito.view.components.InformationCardUser
import com.ingridsantos.technicaltestsistecredito.view.components.ScreenAlertMessage

@Composable
fun PostsUI(
    user: UserDomain,
    viewModel: PostViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.getPosts(user.id)
    }

    val state by viewModel.postsState.collectAsState()

    if (state.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        Column {
            TopAppBar(
                title = { androidx.compose.material.Text("Publicaciones") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            modifier = Modifier,
                            contentDescription = "volver"
                        )
                    }
                }
            )
            if (state.isError.isNotEmpty()) {
                val message = state.isError
                ScreenAlertMessage(message = "¡Ah occurred an error! $message", color = Color.Red)
            }

            if (state.result.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    InformationCardUser(userDomain = user, showPosts = false)
                    Posts(posts = state.result)
                }
            }
        }
    }
}

@Composable
fun Posts(posts: List<Post>) {
    LazyColumn {
        items(posts) { post ->
            PostInfo(post = post)
        }
    }
}

@Composable
fun PostInfo(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = post.title,
                fontSize = 16.sp,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.body,
                textAlign = TextAlign.Justify,
                fontSize = 14.sp,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = 8.dp).fillMaxWidth()
            )
        }
    }
}
