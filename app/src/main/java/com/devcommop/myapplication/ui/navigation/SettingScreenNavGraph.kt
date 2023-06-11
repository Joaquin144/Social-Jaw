package com.devcommop.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devcommop.myapplication.ui.components.editprofile.EditUserProfileScreen
import com.devcommop.myapplication.ui.components.settings.SettingsHomeScreen
import com.devcommop.myapplication.ui.components.settings.notifications.NotificationsUI
import com.devcommop.myapplication.ui.components.settings.preferences.OtherSettings
import com.devcommop.myapplication.ui.components.settings.tandc.TermsConditionsUI
import com.devcommop.myapplication.ui.screens.CommonInMainScreen
import com.devcommop.myapplication.ui.screens.SettingComponentScreens

@Composable
fun SetupSettingNavigation(navController : NavHostController , onSignOut :()-> Unit ){
    NavHost(navController = navController,
        startDestination = SettingComponentScreens.SettingsHomeScreen.route){
        composable(SettingComponentScreens.SettingsHomeScreen.route){
            SettingsHomeScreen( navController , onSignOut = onSignOut)
        }
        composable(SettingComponentScreens.SettingNotificationScreen.route){
            NotificationsUI(navController = navController)
        }
        composable(route = CommonInMainScreen.EditUserProfileScreen.route){
            EditUserProfileScreen()
        }
        composable(route = SettingComponentScreens.SettingPreferencesScreen.route){
            OtherSettings()
        }
        composable(route = SettingComponentScreens.SettingTermsAndConditionsScreen.route){
            TermsConditionsUI()
        }

    }
}