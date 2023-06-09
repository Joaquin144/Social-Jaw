package com.devcommop.myapplication.ui.components.editprofile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcommop.myapplication.data.local.RuntimeQueries
import com.devcommop.myapplication.data.repository.Repository
import com.devcommop.myapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EditUserProfileViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val event = _eventFlow.asSharedFlow()
    private val TAG = "##@@EDIT_USER_PROFILE_VIEW_MODEL"
    //todo: Ensure it works as expected
    private var _user = RuntimeQueries.currentUser?.copy()//aim: create a copy by value not by reference
    val user get() = _user

    private var _userId =
        mutableStateOf(_user?.uid ?: "")
    private var __userName by mutableStateOf("")
    val userName get() = __userName

    private var _fullName by mutableStateOf("")
    val fullName get() = _fullName

    private var _profilePictureUrl by mutableStateOf(null as String?)
    val profilePictureUrl get() = _profilePictureUrl

    private var _coverPictureUrl by mutableStateOf(null as String?)
    val coverPictureUrl get() = _coverPictureUrl
    private var _gender by mutableStateOf("")
    val gender get() = _gender
    private var _relationshipStatus by mutableStateOf("")
    val relationshipStatus get() = _relationshipStatus
    private var _address by mutableStateOf("")
    val address get() = _address
    private var _city by mutableStateOf("")
    val city get() = _city
    private var _country by mutableStateOf("")
    val country get() = _country
    private var _interestedIn by mutableStateOf("")
    val interestedIn get() = _interestedIn
    private var _dob by mutableStateOf("")
    val dob get() = _dob
    private var _phone by mutableStateOf("")
    val phone get() = _phone
    private var _bio by mutableStateOf("")
    val bio get() = _bio
    private var _company by mutableStateOf("")
    val company get() = _company
    private var _employmentStatus by mutableStateOf("")
    val employmentStatus get() = _employmentStatus
    private var _isDeactivated by mutableStateOf(false)
    val isDeactivated get() = _isDeactivated
    private var _yearsOfExperience by mutableStateOf(null as Int?)
    val yearsOfExperience get() = _yearsOfExperience
    init {
        if (_user == null) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowSnackbar(message = "Error: _user is null"))
            }
        } else {
            _user?.let {
                __userName = _user!!.userName
                _fullName = _user!!.fullName.toString()
                _gender = _user!!.gender.toString()
                _relationshipStatus = _user!!.relationshipStatus.toString()
                _address = _user!!.address.toString()
                _city = _user!!.city.toString()
                _country = _user!!.country.toString()
                _interestedIn = _user!!.interestedIn.toString()
                _dob = _user!!.dob.toString()
                _phone = _user!!.phone.toString()
                _bio = _user!!.bio.toString()
                _company = _user!!.company.toString()
                _employmentStatus = _user!!.employmentStatus.toString()
                _isDeactivated = _user!!.isDeactivated ?: false
            }

        }
    }
    fun isValidUser() : Boolean {
        return _user != null
    }

    fun onEvent(event: EditUserProfileEvent) {
        when (event) {
            is EditUserProfileEvent.EnteredAddress -> { _address = event.value }
            is EditUserProfileEvent.EnteredCoverPictureUrl -> {
                //TODO: upload picture to firebase storage
                _coverPictureUrl = event.value
            }
            is EditUserProfileEvent.EnteredProfilePictureUrl -> { _profilePictureUrl = event.value
            }
            is EditUserProfileEvent.EnteredYearsOfExperience -> { _yearsOfExperience = event.value }
            is EditUserProfileEvent.EnteredBio -> { _bio = event.value }
            is EditUserProfileEvent.EnteredCity -> { _city = event.value }
            is EditUserProfileEvent.EnteredCompany -> { _company = event.value }
            is EditUserProfileEvent.EnteredCountry -> { _country = event.value }
            is EditUserProfileEvent.EnteredDob -> { _dob = event.value }
            is EditUserProfileEvent.EnteredEmploymentStatus -> { _employmentStatus = event.value }
            is EditUserProfileEvent.EnteredFullName -> { _fullName = event.value }
            is EditUserProfileEvent.EnteredGender -> { _gender = event.value }
            is EditUserProfileEvent.EnteredInterestedIn -> { _interestedIn = event.value }
            //is AccountUIEvent.enteredIsDeactivated -> { _isDeactivated = event.value }
            is EditUserProfileEvent.EnteredPhone -> { _phone = event.value }
            is EditUserProfileEvent.EnteredRelationshipStatus -> { _relationshipStatus = event.value }
            is EditUserProfileEvent.EnteredUserName -> { __userName = event.value }
            is EditUserProfileEvent.SubmitDetails -> {
                Log.d(TAG , "SubmitDetails Event Started")
                Log.d(TAG , "all properties are:/n $_user $_profilePictureUrl $_coverPictureUrl $_fullName $_bio $_gender $_dob $_address $_city $_country $_interestedIn $_employmentStatus $_company $_yearsOfExperience" )
                Log.d(TAG , "before stage User Details Are : $_user")
                _user = _user?.copy(
                    profilePictureUrl = _profilePictureUrl,
                    coverPictureUrl = _coverPictureUrl,
                    fullName = _fullName,

                    bio = _bio,
                    gender = _gender,
                    dob = _dob,

                    address = _address,
                    city = _city,
                    country = _country,
                    phone = _phone,

                    relationshipStatus = _relationshipStatus,
                    interestedIn = _interestedIn,

                    employmentStatus = _employmentStatus,
                    company = _company,
                    yearsOfExperience = _yearsOfExperience,
                    //_userName = _userName   //todo: Make sure _userName is unique and only then change it
                )
                Log.d(TAG , "after stage New User Details Are : $_user")
                viewModelScope.launch {
                    if(_user != null){
                        when(val editStatus = repository.editUser(_user!!)) {
                            is Resource.Success -> {
                                Log.d(TAG , "EditUserDetails Success: $editStatus")
                                _eventFlow.emit(UiEvent.DetailsUpdatedSuccessfully)
                            }

                            is Resource.Error -> {
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        message = "Error in creating Post: " + editStatus.message
                                    )
                                )
                                Log.d(TAG , "EditUserDetails Error: $editStatus")

                            }

                            is Resource.Loading -> {}
                        }

                    }
                    else{
                        Log.d(TAG , "EditUserDetails Error: _user is null")
                    }
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackbar(val message: String) : UiEvent()
        object DetailsUpdatedSuccessfully : UiEvent()
    }
}