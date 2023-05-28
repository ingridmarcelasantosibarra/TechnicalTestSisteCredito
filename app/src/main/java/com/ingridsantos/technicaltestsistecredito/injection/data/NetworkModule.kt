package com.ingridsantos.technicaltestsistecredito.injection.data

import com.ingridsantos.technicaltestsistecredito.data.api.PostsUserApi
import com.ingridsantos.technicaltestsistecredito.data.api.UsersApi
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Named(AUTH_RETROFIT_CLIENT_NAME)
    @Provides
    @Singleton
    fun getInstanceRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(OkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun postsUserApi(@Named(AUTH_RETROFIT_CLIENT_NAME) retrofit: Retrofit): PostsUserApi {
        return retrofit.create(PostsUserApi::class.java)
    }

    @Provides
    @Singleton
    fun usersApi(@Named(AUTH_RETROFIT_CLIENT_NAME) retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }

    private const val AUTH_RETROFIT_CLIENT_NAME = "auth_retrofit"
}
