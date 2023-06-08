package com.devcommop.myapplication.ui.components.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devcommop.myapplication.R
import com.devcommop.myapplication.data.local.RuntimeQueries
import com.devcommop.myapplication.ui.screens.SettingComponentScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsHomeScreen(
    navController : NavHostController,
    onSignOut :() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        RuntimeQueries.currentUser?.profilePictureUrl?.let { ProfileCardUI(it) }
        RuntimeQueries.currentUser?.userName?.let { HeaderText(it) }
        GeneralSettingsItem(icon = R.drawable.notification_icon_image, mainText = "Notifications", subText = "Customize your notifications"){
            navController.navigate(SettingComponentScreens.SettingNotificationScreen.route)
        }
        GeneralSettingsItem(icon = R.drawable.baseline_account_circle_24, mainText = "Account", subText = "Manage account details"){
            navController.navigate(SettingComponentScreens.SettingAccountScreen.route)
        }
        GeneralSettingsItem(icon = R.drawable.baseline_work_24, mainText = "App Preferences", subText = "Manage themes, data usage and more"){
            navController.navigate(SettingComponentScreens.SettingPreferencesScreen.route)
        }
        GeneralSettingsItem(icon = R.drawable.baseline_work_24, mainText = "Privacy Policy", subText = ""){
            // TODO: show privacy policy with snackbar
        }
        GeneralSettingsItem(icon = R.drawable.baseline_work_24, mainText = "Terms and Conditions", subText = ""){
            navController.navigate(SettingComponentScreens.SettingTermsAndConditionsScreen.route)
        }
        GeneralSettingsItem(icon = R.drawable.baseline_work_24, mainText = "Help Center", subText = ""){
            //TODO: navigate to email support with intent
        }
        GeneralSettingsItem(icon = R.drawable.baseline_work_24, mainText = "Sign Out", subText = "", onClick = onSignOut)
//        AccountUI()
//        NotificationsUI()
//        EmploymentUI()
//        OtherSettings()
//        HelpCenterUI()
    }
}