package com.devcommop.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.devcommop.myapplication.ui.components.createpost.CreatePostScreen
import com.devcommop.myapplication.ui.components.mainscreen.HomeScreen
import com.devcommop.myapplication.ui.components.mainscreen.MessageScreen
import com.devcommop.myapplication.ui.components.mainscreen.SearchScreen
import com.devcommop.myapplication.ui.components.mainscreen.ShortsScreen
import com.devcommop.myapplication.ui.components.mainscreen.UserProfileScreen
import com.devcommop.myapplication.ui.components.settings.SettingsScreen
import com.devcommop.myapplication.ui.screens.BottomBarScreen

@Composable
fun BottomBarNavGraph(modifier : Modifier, navHostController: NavHostController, onSignOut:()->  Unit) {
    NavHost(
        navController = navHostController,
        startDestination = BottomBarScreen.HomeScreen.route,
        modifier = modifier
    ) {
        composable(BottomBarScreen.HomeScreen.route) {
            HomeScreen()
        }
        composable(BottomBarScreen.ShortsScreen.route) {
            ShortsScreen()
        }
        composable(BottomBarScreen.CreatePostScreen.route) {
            CreatePostScreen()
        }
        composable(BottomBarScreen.UserProfileScreen.route) {
            UserProfileScreen(onSignOut = onSignOut)
        }
        navigation(
            route = "setting",
            startDestination = "settings_screen"
        ) {
            composable(route = "settings_screen") {
                SettingsScreen(onSignOut = onSignOut)
//                SettingsHomeScreen(onSignOut = onSignOut)
            }
        }
        composable("message_screen") {
            MessageScreen()
        }
        composable("search_screen") {
            SearchScreen()
        }
    }

}