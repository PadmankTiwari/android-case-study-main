package com.target.targetcasestudy.utils

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    object ItemListScreen : Screen("List")

    object ItemDetailScreen : Screen(
        route = "deals/{id}",
        navArguments = listOf(navArgument("id") {
            type = NavType.StringType
        })
    ) {
        fun createRoute(id: String) = "deals/${id}"
    }
}