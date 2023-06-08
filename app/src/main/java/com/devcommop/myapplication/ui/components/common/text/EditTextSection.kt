package com.devcommop.myapplication.ui.components.common.text

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EditTextSection(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable () -> Unit = {Icons.Default.Camera},
    fieldLabel: String = "field",
    oldFieldValue : String = "",
    onDone: () -> Unit = {}
) {
    val TAG = "##@@EditTextSection"
    var fieldValue by remember { mutableStateOf(oldFieldValue) }
    // on pressing edit button, give write access
    var makeTextReadOnly by remember {
        mutableStateOf(true)
    }
    // when pressed editButton , State is true
    var pencilIconState by remember { mutableStateOf(true) }
    OutlinedTextField(
        value = fieldValue,
        onValueChange = { newValue ->
            if(!makeTextReadOnly) {
                fieldValue = newValue
            }
        },
        readOnly = makeTextReadOnly,
        modifier = modifier.fillMaxWidth(),
        leadingIcon = { Icons.Default.Camera },
        trailingIcon = {
                Icon(
                    imageVector =  ( if(pencilIconState) (Icons.Default.Edit) else (Icons.Default.Done)),
                    modifier = Modifier.clickable{
                        if (pencilIconState) {
                            // give write access and show done button instead
                            makeTextReadOnly = false
                            pencilIconState = false
                        } else {
                            // give read only access and show edit button instead
                            // TODO: Save changes to database
                            onDone()
                            Log.d(TAG, "EditTextSection: $fieldLabel => $fieldValue")
                            makeTextReadOnly = true
                            pencilIconState = true

                        }},
                    contentDescription = null
                )

        },
        label = {
            Text(fieldLabel)
        }
    )
}

@Preview(showBackground = true )
@Composable
fun RowScopedEditFieldButtonPreview() {
    EditTextSection()
}