package com.devcommop.myapplication.ui.components.settings.account

sealed class AccountUIEvent {
    data class EnteredUserName(val value: String): AccountUIEvent()
    data class EnteredFullName(val value: String): AccountUIEvent()
    data class EnteredGender(val value: String): AccountUIEvent()
    data class EnteredRelationshipStatus(val value: String): AccountUIEvent()
    data class EnteredAddress(val value: String): AccountUIEvent()
    data class EnteredCity(val value: String): AccountUIEvent()
    data class EnteredCountry(val value: String): AccountUIEvent()
    data class EnteredInterestedIn(val value: String): AccountUIEvent()
    data class EnteredDob(val value: String): AccountUIEvent()
    data class EnteredPhone(val value: String): AccountUIEvent()
    data class EnteredBio(val value: String): AccountUIEvent()
    data class EnteredCompany(val value: String): AccountUIEvent()
    data class EnteredEmploymentStatus(val value: String): AccountUIEvent()
    //data class enteredIsDeactivated(val value: String): AccountUIEvent()
    object SubmitDetails: AccountUIEvent()
}