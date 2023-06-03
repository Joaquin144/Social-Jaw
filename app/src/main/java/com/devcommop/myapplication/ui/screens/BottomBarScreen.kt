package com.devcommop.myapplication.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(val route: String, val title: String, val icon: ImageVector) {
    object HomeScreen : BottomBarScreen(
        route = "home_screen",
        title = "Home",
        icon = Icons.Default.Home
    )

    object ShortsScreen : BottomBarScreen(
        route = "shorts_screen",
        title = "Shorts",
        icon = Icons.Default.ElectricBolt
    )

    object CreatePostScreen : BottomBarScreen(
        route = "create_post_screen",
        title = "Create",
        icon = Icons.Default.Camera
    )

    object UserProfileScreen : BottomBarScreen(
        route = "user_profile_screen",
        title = "Profile",
        icon = Icons.Default.Person
    )

    object SettingsScreen : BottomBarScreen(
        route = "settings_screen",
        title = "Settings",
        icon = Icons.Default.Settings
    )
}
