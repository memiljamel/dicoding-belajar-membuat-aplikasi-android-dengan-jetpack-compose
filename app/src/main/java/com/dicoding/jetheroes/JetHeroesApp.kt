package com.dicoding.jetheroes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.jetheroes.ui.navigation.Screen
import com.dicoding.jetheroes.ui.screen.about.AboutScreen
import com.dicoding.jetheroes.ui.screen.detail.DetailScreen
import com.dicoding.jetheroes.ui.screen.list.ListScreen
import com.dicoding.jetheroes.ui.theme.JetHeroesTheme

@Composable
fun JetHeroesApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.List.route,
        modifier = modifier
    ) {
        composable(route = Screen.List.route) {
            ListScreen(
                onNavigateToAbout = {
                    navController.navigate(Screen.About.route)
                },
                onNavigateToDetail = { heroId ->
                    navController.navigate(Screen.Detail.createRoute(heroId))
                }
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("heroId") {
                    type = NavType.StringType
                }
            )
        ) {
            DetailScreen(
                heroId = it.arguments?.getString("heroId") ?: "",
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = Screen.About.route) {
            AboutScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    JetHeroesTheme {
        JetHeroesApp()
    }
}
