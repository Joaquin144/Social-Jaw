package com.devcommop.myapplication.ui.screens

sealed class SettingComponentScreens(val route : String ){
    object SettingsHomeScreen : SettingComponentScreens("setting_home_screen")
    object SettingAccountScreen : SettingComponentScreens("setting_account_screen")
    object SettingNotificationScreen : SettingComponentScreens("setting_notification_screen")
    object SettingPreferencesScreen : SettingComponentScreens("setting_preferences_screen")
    object SettingTermsAndConditionsScreen : SettingComponentScreens("setting_terms_and_conditions_screen")
    object SettingPrivacyPolicyScreen : SettingComponentScreens("setting_privacy_policy_screen")


}
