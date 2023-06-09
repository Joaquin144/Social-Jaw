package com.devcommop.myapplication.ui.components.settings.account

/*
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
                        EditUserProfileUIEvent.EnteredUserName(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.fullName,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        EditUserProfileUIEvent.EnteredFullName(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.gender,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        EditUserProfileUIEvent.EnteredGender(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.relationshipStatus,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        EditUserProfileUIEvent.EnteredRelationshipStatus(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.address,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        EditUserProfileUIEvent.EnteredAddress(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.city,
                onValueChange = { newValue -> viewModel.onEvent(EditUserProfileUIEvent.EnteredCity(newValue)) }
            )
            InputField(
                value = viewModel.country,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        EditUserProfileUIEvent.EnteredCountry(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.interestedIn,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        EditUserProfileUIEvent.EnteredInterestedIn(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.dob,
                onValueChange = { newValue -> viewModel.onEvent(EditUserProfileUIEvent.EnteredDob(newValue)) }
            )
            InputField(
                value = viewModel.phone,
                onValueChange = { newValue -> viewModel.onEvent(EditUserProfileUIEvent.EnteredPhone(newValue)) }
            )
            InputField(
                value = viewModel.bio,
                onValueChange = { newValue -> viewModel.onEvent(EditUserProfileUIEvent.EnteredBio(newValue)) }
            )
            InputField(
                value = viewModel.company,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        EditUserProfileUIEvent.EnteredCompany(
                            newValue
                        )
                    )
                }
            )
            InputField(
                value = viewModel.employmentStatus,
                onValueChange = { newValue ->
                    viewModel.onEvent(
                        EditUserProfileUIEvent.EnteredEmploymentStatus(
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
                        viewModel.onEvent(EditUserProfileUIEvent.SubmitDetails)
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
*/