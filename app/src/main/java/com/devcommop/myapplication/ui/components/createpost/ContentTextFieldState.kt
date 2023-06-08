package com.devcommop.myapplication.ui.components.createpost

import android.net.Uri

data class ContentTextFieldState(
    val text: String = "",
    val imageUri : Uri? = null ,
    val hint: String = "",
    val isHintVisible: Boolean = true


)