package com.devcommop.myapplication.ui.components.editprofile

sealed class EditUserProfileEvent {
    data class EnteredUserName(val value: String): EditUserProfileEvent()
    data class EnteredFullName(val value: String): EditUserProfileEvent()
    data class EnteredProfilePictureUrl(val value : String?): EditUserProfileEvent()
    data class EnteredCoverPictureUrl(val value : String?): EditUserProfileEvent()
    data class EnteredGender(val value: String): EditUserProfileEvent()
    data class EnteredRelationshipStatus(val value: String): EditUserProfileEvent()
    data class EnteredAddress(val value: String): EditUserProfileEvent()
    data class EnteredCity(val value: String): EditUserProfileEvent()
    data class EnteredCountry(val value: String): EditUserProfileEvent()
    data class EnteredInterestedIn(val value: String): EditUserProfileEvent()
    data class EnteredDob(val value: String): EditUserProfileEvent()
    data class EnteredPhone(val value: String): EditUserProfileEvent()
    data class EnteredBio(val value: String): EditUserProfileEvent()
    data class EnteredCompany(val value: String): EditUserProfileEvent()
    data class EnteredEmploymentStatus(val value: String): EditUserProfileEvent()
    data class EnteredYearsOfExperience(val value: Int): EditUserProfileEvent()
    //data class enteredIsDeactivated(val value: String): AccountUIEvent()
    object SubmitDetails: EditUserProfileEvent()
}