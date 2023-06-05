package com.devcommop.myapplication.ui.components.mainscreen.createpost

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcommop.myapplication.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "##@@CreatePostVM"

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _postContent = mutableStateOf(
        ContentTextFieldState(
            hint = "What's on your mind...."
        )
    )
    val postContent: State<ContentTextFieldState> = _postContent

    private val _eventFlow =
        MutableSharedFlow<UiEvent>()//For some UI events we don't want to show again and again while config changes. eg: Snackbar, Toast, AlertDialog, vibration etc.
    val event = _eventFlow.asSharedFlow()

    init {
        //todo: ask/ check for camera, gallery permissions
    }

    fun onEvent(event: CreatePostEvents) {
        when (event) {
            is CreatePostEvents.ChangeContentFocus -> {
                _postContent.value = postContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && postContent.value.text.isBlank()
                )
            }

            is CreatePostEvents.EnteredContent -> {
                _postContent.value = postContent.value.copy(
                    text = event.value
                )
            }

            is CreatePostEvents.OnBackButtonPressed -> {
                //todo: Show an alert dialog that all user's progress would be discarded. "Do you wish to continue?"
                //todo: [Check] is viewModelScope a bad approach for this ????
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            message = "Your progress will be discarded"
                        )
                    )
                }
            }

            is CreatePostEvents.SubmitPost -> {
                /*  Aim:-----
                    i) Validate the Post is okay or not
                    ii) Add meta-data to post and user both
                    iii) Call repo function to upload post
                    iv) Handle Success or Error results
                    v) inform ui of UiEvent => PostSavedSuccessfully
                 */
                viewModelScope.launch {
                    //repository.addPost()
                }
            }
        }
    }

    private fun validatePost() {
        //if ()
    }

    sealed class UiEvent() {
        data class ShowSnackbar(val message: String) : UiEvent()
        object PostUploadedSuccessfully : UiEvent()
    }
}