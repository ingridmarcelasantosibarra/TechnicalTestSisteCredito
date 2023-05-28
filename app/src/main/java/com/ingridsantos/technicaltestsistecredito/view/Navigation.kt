package com.ingridsantos.technicaltestsistecredito.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ingridsantos.technicaltestsistecredito.domain.models.UserDomain
import com.ingridsantos.technicaltestsistecredito.utils.fromJson
import com.ingridsantos.technicaltestsistecredito.utils.toJson
import com.ingridsantos.technicaltestsistecredito.view.posts.PostsUI
import com.ingridsantos.technicaltestsistecredito.view.users.UsersUI

enum class NavPath(
    val route: String
) {
    UsersUI(route = "usersList"),
    PostsUI(route = "postsList")
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavPath.UsersUI.route) {
        composable(NavPath.UsersUI.route) {
            UsersUI(
                onClick = { user ->
                    val userJson = user.toJson()
                    navController.navigate(route = "${NavPath.PostsUI.route}/$userJson")
                }
            )
        }

        composable(
            "${NavPath.PostsUI.route}/{user}",
            arguments = listOf(
                navArgument("user") {
                    type = NavType.StringType
                }
            )
        ) {
            it.arguments?.getString("user")?.let { userJson ->
                val user = userJson.fromJson(UserDomain::class.java)
                PostsUI(user) {
                    navController.popBackStack()
                }
            }
        }
    }
}
