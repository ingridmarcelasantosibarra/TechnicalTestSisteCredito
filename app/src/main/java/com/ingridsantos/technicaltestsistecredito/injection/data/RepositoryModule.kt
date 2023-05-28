package com.ingridsantos.technicaltestsistecredito.injection.data

import com.ingridsantos.technicaltestsistecredito.data.api.PostsUserApi
import com.ingridsantos.technicaltestsistecredito.data.api.UsersApi
import com.ingridsantos.technicaltestsistecredito.data.local.TechnicalTestRoomDatabase
import com.ingridsantos.technicaltestsistecredito.data.local.dao.UserDao
import com.ingridsantos.technicaltestsistecredito.data.repositories.PostsUserRepositoryImpl
import com.ingridsantos.technicaltestsistecredito.data.repositories.UsersRepositoryImpl
import com.ingridsantos.technicaltestsistecredito.data.repositories.exception.ExceptionPostsUserRepositoryImpl
import com.ingridsantos.technicaltestsistecredito.data.repositories.exception.ExceptionUsersRepositoryImpl
import com.ingridsantos.technicaltestsistecredito.data.repositories.local.LocalUsersRepositoryImpl
import com.ingridsantos.technicaltestsistecredito.domain.repositories.DomainExceptionRepository
import com.ingridsantos.technicaltestsistecredito.domain.repositories.PostsUserRepository
import com.ingridsantos.technicaltestsistecredito.domain.repositories.UsersRepository
import com.ingridsantos.technicaltestsistecredito.domain.repositories.local.LocalUsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun postsUserRepository(
        postsUserApi: PostsUserApi,
        @Named(EXCEPTION_SERVICE_POSTS_REPOSITORY) domainExceptionRepository: DomainExceptionRepository
    ): PostsUserRepository = PostsUserRepositoryImpl(postsUserApi, domainExceptionRepository)

    @Provides
    @ViewModelScoped
    fun usersRepository(
        userApi: UsersApi,
        @Named(EXCEPTION_SERVICE_USERS_REPOSITORY) domainExceptionRepository: DomainExceptionRepository
    ): UsersRepository = UsersRepositoryImpl(userApi, domainExceptionRepository)

    @Provides
    @ViewModelScoped
    fun localUsersRepository(
        userDao: UserDao
    ): LocalUsersRepository = LocalUsersRepositoryImpl(userDao)

    @Named(EXCEPTION_SERVICE_POSTS_REPOSITORY)
    @Provides
    @ViewModelScoped
    fun exceptionPostsUserRepository(): DomainExceptionRepository =
        ExceptionPostsUserRepositoryImpl()

    @Named(EXCEPTION_SERVICE_USERS_REPOSITORY)
    @Provides
    @ViewModelScoped
    fun exceptionUsersRepository(): DomainExceptionRepository =
        ExceptionUsersRepositoryImpl()

    private const val EXCEPTION_SERVICE_POSTS_REPOSITORY = "exceptionServicePostsRepository"
    private const val EXCEPTION_SERVICE_USERS_REPOSITORY = "exceptionServiceUsersRepository"
}
