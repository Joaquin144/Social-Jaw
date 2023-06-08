package com.devcommop.myapplication.ui.components.settings.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcommop.myapplication.data.local.RuntimeQueries
import com.devcommop.myapplication.data.repository.Repository
import com.devcommop.myapplication.ui.components.createpost.CreatePostViewModel
import com.devcommop.myapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountUIViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val event = _eventFlow.asSharedFlow()

    //todo: Ensure it works as expected
    private var user = RuntimeQueries.currentUser?.copy()//aim: create a copy by value not by reference

    var userName by mutableStateOf("")
    var fullName by mutableStateOf("")
    var gender by mutableStateOf("")
    var relationshipStatus by mutableStateOf("")
    var address by mutableStateOf("")
    var city by mutableStateOf("")
    var country by mutableStateOf("")
    var interestedIn by mutableStateOf("")
    var dob by mutableStateOf("")
    var phone by mutableStateOf("")
    var bio by mutableStateOf("")
    var company by mutableStateOf("")
    var employmentStatus by mutableStateOf("")
    var isDeactivated by mutableStateOf(false)

    init {
        if (user == null) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowSnackbar(message = "Error: user is null"))
            }
        } else {
            user?.let {
                userName = user!!.userName
                fullName = user!!.fullName.toString()
                gender = user!!.gender.toString()
                relationshipStatus = user!!.relationshipStatus.toString()
                address = user!!.address.toString()
                city = user!!.city.toString()
                country = user!!.country.toString()
                interestedIn = user!!.interestedIn.toString()
                dob = user!!.dob.toString()
                phone = user!!.phone.toString()
                bio = user!!.bio.toString()
                company = user!!.company.toString()
                employmentStatus = user!!.employmentStatus.toString()
                isDeactivated = user!!.isDeactivated ?: false
            }

        }
    }

    fun onEvent(event: AccountUIEvent) {
        when (event) {
            is AccountUIEvent.EnteredAddress -> { address = event.value }
            is AccountUIEvent.EnteredBio -> { bio = event.value }
            is AccountUIEvent.EnteredCity -> { city = event.value }
            is AccountUIEvent.EnteredCompany -> { company = event.value }
            is AccountUIEvent.EnteredCountry -> { country = event.value }
            is AccountUIEvent.EnteredDob -> { dob = event.value }
            is AccountUIEvent.EnteredEmploymentStatus -> { employmentStatus = event.value }
            is AccountUIEvent.EnteredFullName -> { fullName = event.value }
            is AccountUIEvent.EnteredGender -> { gender = event.value }
            is AccountUIEvent.EnteredInterestedIn -> { interestedIn = event.value }
            //is AccountUIEvent.enteredIsDeactivated -> { isDeactivated = event.value }
            is AccountUIEvent.EnteredPhone -> { phone = event.value }
            is AccountUIEvent.EnteredRelationshipStatus -> { relationshipStatus = event.value }
            is AccountUIEvent.EnteredUserName -> { userName = event.value }
            is AccountUIEvent.SubmitDetails -> {
                user = user?.copy(
                    address = address,
                    bio = bio,
                    city = city,
                    country = country,
                    company = company,
                    dob = dob,
                    employmentStatus = employmentStatus,
                    fullName = fullName,
                    gender = gender,
                    interestedIn = interestedIn,
                    phone = phone,
                    relationshipStatus = relationshipStatus,
                    //userName = userName   //todo: Make sure userName is unique and only then change it
                )
                viewModelScope.launch {
                    if(user != null){
                        when(val editStatus = repository.editUser(user!!)) {
                            is Resource.Success -> {
                                _eventFlow.emit(AccountUIViewModel.UiEvent.DetailsUpdatedSuccessfully)
                            }

                            is Resource.Error -> {
                                _eventFlow.emit(
                                    AccountUIViewModel.UiEvent.ShowSnackbar(
                                        message = "Error in creating Post: " + editStatus.message
                                    )
                                )
                            }

                            is Resource.Loading -> {}
                        }

                    }
                }
            }
        }
    }

    sealed class UiEvent() {
        data class ShowSnackbar(val message: String) : UiEvent()
        object DetailsUpdatedSuccessfully : UiEvent()
    }
}