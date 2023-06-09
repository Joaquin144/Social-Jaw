package com.devcommop.myapplication.ui.components.common.text

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EditTextSection(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable () -> Unit = {Icons.Default.Camera},
    fieldLabel: String = "field",
    oldFieldValue : String = "",
    onDone: (newFieldValue : String ) -> Unit = {},
    minLines : Int  = 1,
    keyboardOptions : KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text
    )
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
//        enabled = !makeTextReadOnly,
        readOnly = makeTextReadOnly,
        modifier = modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth(),
//        leadingIcon = { Icons.Default.Camera },
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
                            // TODO: Validate if the field is not left empty
                            if(fieldValue.trimEnd().isNotEmpty()) {
                                Log.d(TAG, "EditTextSection: $fieldLabel => $fieldValue")
                                makeTextReadOnly = true
                                pencilIconState = true
                                onDone(fieldValue.trimEnd())
                            }
                            else{
                                Log.d(TAG, "EditTextSection: $fieldLabel value is empty")
                            }

                        }},
                    contentDescription = null
                )

        },
        label = {
            Text(fieldLabel)
        },
        minLines = minLines,
        keyboardOptions = keyboardOptions
    )
}

@Preview(showBackground = true )
@Composable
fun RowScopedEditFieldButtonPreview() {
    EditTextSection()
}