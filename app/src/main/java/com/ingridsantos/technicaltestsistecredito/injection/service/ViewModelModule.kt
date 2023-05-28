package com.ingridsantos.technicaltestsistecredito.injection.service

import com.ingridsantos.technicaltestsistecredito.domain.usecases.LocalUsersUC
import com.ingridsantos.technicaltestsistecredito.domain.usecases.PostsUserUC
import com.ingridsantos.technicaltestsistecredito.domain.usecases.UsersUC
import com.ingridsantos.technicaltestsistecredito.view.posts.PostViewModel
import com.ingridsantos.technicaltestsistecredito.view.users.UsersViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun usersViewModel(
        usersUC: UsersUC,
        localUsersUC: LocalUsersUC
    ) = UsersViewModel(userUC = usersUC, localUsersUC = localUsersUC)

    @Provides
    fun postsViewModel(
        postsUserUC: PostsUserUC
    ) = PostViewModel(postsUC = postsUserUC)
}
