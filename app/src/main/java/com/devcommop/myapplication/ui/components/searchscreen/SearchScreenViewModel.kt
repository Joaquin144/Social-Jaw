package com.devcommop.myapplication.ui.components.searchscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcommop.myapplication.data.model.User
import com.devcommop.myapplication.data.repository.Repository
import com.devcommop.myapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _usersListResult = mutableStateOf(emptyList<User>())
    val usersListResult: State<List<User>> = _usersListResult

    fun onEvent(event: SearchScreenEvents){
        when(event){
            is SearchScreenEvents.OnSearchQuerySubmit -> {
                viewModelScope.launch {
                    when(val searchStatus = repository.searchUsers(query = event.searchQuery)){
                        is Resource.Success -> {
                            _usersListResult.value = searchStatus.data?:emptyList()//todo: check if its bad practice
                        }
                        is Resource.Error -> {
                            //todo: show error to ui
                        }
                        is Resource.Loading -> {}
                    }
                }

            }
        }
    }

}