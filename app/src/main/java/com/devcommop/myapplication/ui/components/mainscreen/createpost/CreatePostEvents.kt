package com.devcommop.myapplication.ui.components.mainscreen.createpost

import android.net.Uri
import androidx.compose.ui.focus.FocusState

//Aim: To use these for handling user actions like submit, backButtonPressed etc.
sealed class CreatePostEvents {
    data class ChangeContentFocus(val focusState: FocusState): CreatePostEvents()//we'll hide hint when content is in focus and show hint if it goes out of focus
    data class EnteredContent(val value: String, val imageUri : Uri? ): CreatePostEvents()//when user has entered content of the post
    object SubmitPost: CreatePostEvents()
    object OnBackButtonPressed: CreatePostEvents()//todo: As user if he really wants to discard changes
}