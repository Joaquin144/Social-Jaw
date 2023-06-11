package com.devcommop.myapplication.ui.components.settings.notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SettingItem(
            title = "Receive Follow notifications",
            isChecked = notificationState.value.receiveFollowNotifications,
            onCheckedChange = { viewModel.onEvent(NotificationVMEvents.FollowNotificationChange(viewModel.notificationState.value.receiveFollowNotifications)) }
        )
        SettingItem(
            title = "Receive Like notifications",
            isChecked = notificationState.value.receiveLikeNotifications,
            onCheckedChange = { viewModel.onEvent(NotificationVMEvents.LikeNotificationChange(viewModel.notificationState.value.receiveLikeNotifications)) }
        )
        SettingItem(
            title = "Receive Comment notifications",
            isChecked = notificationState.value.receiveCommentNotifications,
            onCheckedChange = { viewModel.onEvent(NotificationVMEvents.CommentNotificationChange(viewModel.notificationState.value.receiveCommentNotifications)) }
        )
        SettingItem(
            title = "Receive Security notifications",
            isChecked = notificationState.value.receiveSecurityNotifications,
            onCheckedChange = { viewModel.onEvent(NotificationVMEvents.SecurityNotificationChange(viewModel.notificationState.value.receiveSecurityNotifications)) }
        )
        SettingItem(
            title = "Receive Promotional notifications",
            isChecked = notificationState.value.receivePromotionalNotifications,
            onCheckedChange = { viewModel.onEvent(NotificationVMEvents.PromotionalNotificationChange(viewModel.notificationState.value.receivePromotionalNotifications)) }
        )
        SettingItem(
            title = "Receive Product updates notifications",
            isChecked = notificationState.value.receiveProductUpdatesNotifications,
            onCheckedChange = { viewModel.onEvent(NotificationVMEvents.ProductUpdateNotificationChange(viewModel.notificationState.value.receiveProductUpdatesNotifications)) }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { viewModel.onEvent(NotificationVMEvents.SavePreferences) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Save")
        }
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