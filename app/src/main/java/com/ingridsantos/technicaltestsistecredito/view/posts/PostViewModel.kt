package com.ingridsantos.technicaltestsistecredito.view.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingridsantos.technicaltestsistecredito.domain.usecases.PostsUserUC
import com.ingridsantos.technicaltestsistecredito.utils.handleViewModelExceptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postsUC: PostsUserUC
) : ViewModel() {

    private val _postsState = MutableStateFlow(PostsModel())
    val postsState = _postsState.asStateFlow()

    fun getPosts(id: Int) {
        viewModelScope.launch {
            postsUC.invoke(id)
                .onStart {
                    _postsState.value = _postsState.value.copy(isLoading = true)
                }
                .onCompletion {
                    _postsState.value = _postsState.value.copy(isLoading = false)
                }
                .handleViewModelExceptions { domainException ->
                    _postsState.value = _postsState.value.copy(isError = domainException.message)
                }
                .collect {
                    if (it.isNotEmpty()) {
                        _postsState.value = _postsState.value.copy(result = it)
                    }
                }
        }
    }
}
