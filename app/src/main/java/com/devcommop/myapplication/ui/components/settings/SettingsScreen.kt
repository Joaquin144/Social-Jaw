package com.devcommop.myapplication.ui.components.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devcommop.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onSignOut: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        HeaderText()
        ProfileCardUI()
        GeneralSettingsItem(icon = R.drawable.notification_icon_image, mainText = "Notifications", subText = "Customize your notifications")
        GeneralSettingsItem(icon = R.drawable.baseline_account_circle_24, mainText = "Account", subText = "Manage account details")
        GeneralSettingsItem(icon = R.drawable.baseline_work_24, mainText = "App Preferences", subText = "Manage themes, data usage and more")
        GeneralSettingsItem(icon = R.drawable.baseline_work_24, mainText = "Privacy Policy", subText = "")
        GeneralSettingsItem(icon = R.drawable.baseline_work_24, mainText = "Terms and Conditions", subText = "", onClick = {})
        GeneralSettingsItem(icon = R.drawable.baseline_work_24, mainText = "Help Center", subText = "")
        GeneralSettingsItem(icon = R.drawable.baseline_work_24, mainText = "Sign Out", subText = "", onClick = onSignOut)
//        AccountUI()
//        NotificationsUI()
//        EmploymentUI()
//        OtherSettings()
//        HelpCenterUI()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen()
}




