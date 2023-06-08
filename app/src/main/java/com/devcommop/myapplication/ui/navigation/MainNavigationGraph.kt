package com.devcommop.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.devcommop.myapplication.ui.components.createpost.CreatePostScreen
import com.devcommop.myapplication.ui.components.editprofile.EditUserProfileScreen
import com.devcommop.myapplication.ui.components.mainscreen.HomeScreen
import com.devcommop.myapplication.ui.components.mainscreen.MessageScreen
import com.devcommop.myapplication.ui.components.mainscreen.SearchScreen
import com.devcommop.myapplication.ui.components.mainscreen.ShortsScreen
import com.devcommop.myapplication.ui.components.mainscreen.UserProfileScreen
import com.devcommop.myapplication.ui.components.settings.SettingsScreen
import com.devcommop.myapplication.ui.screens.BottomBarScreen
import com.devcommop.myapplication.ui.screens.CommonInMainScreen
import com.devcommop.myapplication.ui.screens.TopAppBarScreen

@Composable
fun MainScreenNavGraph(modifier : Modifier, navHostController: NavHostController, onNavigateToEditUserProfileScreen:()->Unit, onSignOut:()->  Unit) {
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
            UserProfileScreen(
               onNavigateToEditUserProfileScreen
                ,onSignOut = onSignOut
            )
        }
        navigation(
            route = "setting",
            startDestination = "settings_screen"
        ) {
            composable(route = "settings_screen") {
                SettingsScreen(
                    onNavigateToEditUserProfileScreen ,
                    onSignOut = onSignOut)
            }
        }
        composable(TopAppBarScreen.messageScreen.route) {
            MessageScreen()
        }
        composable(TopAppBarScreen.SearchScreen.route) {
            SearchScreen()
        }
        composable(CommonInMainScreen.EditUserProfileScreen.route) {
            EditUserProfileScreen()
        }
    }

}