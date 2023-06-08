package com.devcommop.myapplication.ui.components.settings.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devcommop.myapplication.ui.components.common.buttons.RowScopedHorizontalButtonWithText

@Composable
fun AccountUI(

) {
    val viewModel: AccountUIViewModel = hiltViewModel()
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            InputField(
                value = viewModel.userName,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        AccountUIEvent.EnteredUserName(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.fullName,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        AccountUIEvent.EnteredFullName(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.gender,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        AccountUIEvent.EnteredGender(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.relationshipStatus,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        AccountUIEvent.EnteredRelationshipStatus(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.address,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        AccountUIEvent.EnteredAddress(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.city,
                onValueChange = { newValue -> viewModel.onEvent(AccountUIEvent.EnteredCity(newValue)) }
            )
            InputField(
                value = viewModel.country,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        AccountUIEvent.EnteredCountry(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.interestedIn,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        AccountUIEvent.EnteredInterestedIn(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.dob,
                onValueChange = { newValue -> viewModel.onEvent(AccountUIEvent.EnteredDob(newValue)) }
            )
            InputField(
                value = viewModel.phone,
                onValueChange = { newValue -> viewModel.onEvent(AccountUIEvent.EnteredPhone(newValue)) }
            )
            InputField(
                value = viewModel.bio,
                onValueChange = { newValue -> viewModel.onEvent(AccountUIEvent.EnteredBio(newValue)) }
            )
            InputField(
                value = viewModel.company,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        AccountUIEvent.EnteredCompany(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.employmentStatus,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        AccountUIEvent.EnteredEmploymentStatus(
                            newValue
                        )
                    )
                }
            )
            //todo: Make deactivate switch
//            InputField(
//                value = viewModel.isDeactivated,
//                onValueChange = { newValue -> viewModel.onEvent(AccountUIEvent.enteredIsDeactivated(newValue)) }
//            )

            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RowScopedHorizontalButtonWithText(
                    icon = Icons.Default.Done,
                    onClick = {
                        viewModel.onEvent(AccountUIEvent.SubmitDetails)
                    },
                    text = "Save"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAccountUI() {
    AccountUI()
}

@Composable
fun InputField(value: String, onValueChange: (String) -> Unit) {
    Spacer(modifier = Modifier.height(16.dp))
    BasicTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
    )
}