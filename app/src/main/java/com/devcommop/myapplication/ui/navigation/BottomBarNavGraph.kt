package com.devcommop.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devcommop.myapplication.ui.components.CreatePostScreen
import com.devcommop.myapplication.ui.components.HomeScreen
import com.devcommop.myapplication.ui.components.SettingsScreen
import com.devcommop.myapplication.ui.components.ShortsScreen
import com.devcommop.myapplication.ui.components.UserProfileScreen
import com.devcommop.myapplication.ui.screens.BottomBarScreen

@Composable
fun BottomBarNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = BottomBarScreen.HomeScreen.route
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
            UserProfileScreen()
        }
        composable(BottomBarScreen.SettingsScreen.route) {
            SettingsScreen()
        }
    }

}