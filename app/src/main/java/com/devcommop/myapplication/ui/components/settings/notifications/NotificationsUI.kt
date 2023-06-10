package com.devcommop.myapplication.ui.components.settings.notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun NotificationsUI(
    navController: NavController,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val notificationState = viewModel.notificationState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SettingItem(
            title = "Receive Follow notifications",
            isChecked = notificationState.value.receiveFollowNotifications,
            onCheckedChange = {  }
        )
        SettingItem(
            title = "Receive Like notifications",
            isChecked = notificationState.value.receiveLikeNotifications,
            onCheckedChange = {  }
        )
        SettingItem(
            title = "Receive Like notifications",
            isChecked = notificationState.value.receiveCommentNotifications,
            onCheckedChange = {  }
        )
        SettingItem(
            title = "Receive Promotional notifications",
            isChecked = notificationState.value.receivePromotionalNotifications,
            onCheckedChange = {  }
        )
        SettingItem(
            title = "Receive Product updates notifications",
            isChecked = notificationState.value.receiveProductUpdatesNotifications,
            onCheckedChange = {  }
        )
    }
}

@Composable
fun SettingItem(
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}