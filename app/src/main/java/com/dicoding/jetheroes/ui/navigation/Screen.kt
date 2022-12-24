package com.dicoding.jetheroes.ui.navigation

sealed class Screen(val route: String) {
    object List : Screen("list")
    object Detail : Screen("detail/{heroId}") {
        fun createRoute(heroId: String) = "detail/$heroId"
    }

    object About : Screen("about")
}