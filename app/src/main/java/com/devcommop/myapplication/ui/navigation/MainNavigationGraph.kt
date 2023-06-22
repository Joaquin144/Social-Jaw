package com.devcommop.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.devcommop.myapplication.ui.components.createpost.CreatePostScreen
import com.devcommop.myapplication.ui.components.editprofile.EditUserProfileScreen
import com.devcommop.myapplication.ui.components.homescreen.HomeScreen
import com.devcommop.myapplication.ui.components.searchscreen.SearchScreen
import com.devcommop.myapplication.ui.components.shorts.ShortsScreen
import com.devcommop.myapplication.ui.components.mainscreen.UserProfileScreen
import com.devcommop.myapplication.ui.components.public_profile.PublicUserProfileScreen
import com.devcommop.myapplication.ui.components.settings.SettingsScreen
import com.devcommop.myapplication.ui.screens.BottomBarScreen
import com.devcommop.myapplication.ui.screens.CommonInMainScreen
import com.devcommop.myapplication.ui.screens.OtherScreens
import com.devcommop.myapplication.ui.screens.TopAppBarScreen

@Composable
fun MainScreenNavGraph(
    modifier: Modifier,
    navHostController: NavHostController,
    onNavigateToEditUserProfileScreen: () -> Unit,
    onSignOut: () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = BottomBarScreen.HomeScreen.route,
        modifier = modifier
    ) {
        composable(BottomBarScreen.HomeScreen.route) {
            HomeScreen(navController = navHostController)
        }
        composable(BottomBarScreen.ShortsScreen.route) {
            ShortsScreen()
        }
        composable(BottomBarScreen.CreatePostScreen.route) {
            CreatePostScreen()
        }
        composable(BottomBarScreen.UserProfileScreen.route) {
            UserProfileScreen(
                onNavigateToEditUserProfileScreen, onSignOut = onSignOut
            )
        }
        composable(
            OtherScreens.PublicUserProfileScreen.route + "/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            PublicUserProfileScreen(
                navController = navHostController,
                backStackEntry.arguments?.getString("userId") ?: "null"
            )
        }
        navigation(
            route = "setting",
            startDestination = "settings_screen"
        ) {
            composable(route = "settings_screen") {
                SettingsScreen(
                    onNavigateToEditUserProfileScreen,
                    onSignOut = onSignOut
                )
            }
        }
//        composable(TopAppBarScreen.messageScreen.route) {
//            MessageScreen()
//        }
        composable(TopAppBarScreen.SearchScreen.route) {
            SearchScreen(navController = navHostController)
        }
        composable(CommonInMainScreen.EditUserProfileScreen.route) {
            EditUserProfileScreen()
        }
    }

}