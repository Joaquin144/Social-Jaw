package com.devcommop.myapplication.ui.components.editprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devcommop.myapplication.R
import com.devcommop.myapplication.ui.components.common.text.EditTextSection

@Composable
fun EditUserProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 2.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

//contains propic ,cover and username and bio
        ProfileHeaderSection()

        Spacer(modifier = Modifier.height(8.dp))

        NameSection()

        Spacer(modifier = Modifier.height(8.dp))

        DateOfBirthSection()

        Spacer(modifier = Modifier.height(8.dp))

        GenderSection()

        Spacer(modifier = Modifier.height(8.dp))

        BioSection()

        Spacer(modifier = Modifier.height(8.dp))

        AddressCard()

        Spacer(modifier = Modifier.height(8.dp))

        EmploymentStatusCard()
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileHeaderSection() {
    // TODO: when user change cover, show dialog to post it as well
    Box(modifier = Modifier ){
        Box(
            contentAlignment = Alignment.BottomEnd,
        ) {
            Image(
                painter = painterResource(R.drawable.dummy_cover_image),
                contentDescription = "profile_picture",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .padding(horizontal = 2.dp)
                    .size(160.dp)
                    .safeContentPadding()
                    .clip(shape = RectangleShape),
                contentScale = ContentScale.Crop
            )
            IconButton(onClick = { /*TODO: Handle profile picture edit */ }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Profile Picture",
                    modifier = Modifier.size(28.dp)
                )
            }
            //val width = LocalConfiguration.current.screenWidthDp - 180 - 10   -4
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center
            ) {

            }

        }


        Row(
            modifier = Modifier.padding(top = 81.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.padding(0.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.dummy_profile_picture),
                    contentDescription = "profile_picture",
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .size(180.dp)
                        .safeContentPadding()
                        .clip(shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
                IconButton(onClick = { /*TODO: Handle profile picture edit */ }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Profile Picture",
                        modifier = Modifier.size(28.dp)
                    )
                }
//
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
    Text(
        text = "Anime Girl",
        style = MaterialTheme.typography.headlineMedium
    )
}

@Preview(showBackground = true)
@Composable
fun NameSection() {
    EditTextSection(
        modifier = Modifier,
        leadingIcon = { Icons.Default.Details },
        fieldLabel = "Name",
        oldFieldValue = "Anime Girl",
        onDone = {}
    )
}


@Preview(showBackground = true)
@Composable
fun AddressCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            EditTextSection(
                Modifier,
                @Composable { Icons.Default.AddLocation },
                "Address",
                "123 Null Street",
                onDone = {})

            Spacer(modifier = Modifier.height(8.dp))

            EditTextSection(
                Modifier,
                @Composable { Icons.Default.LocationCity },
                "City",
                "New York",
                onDone = {})

            Spacer(modifier = Modifier.height(8.dp))

            EditTextSection(
                Modifier,
                @Composable { Icons.Default.Flag },
                "Country",
                "United States",
                onDone = {})


        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmploymentStatusCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            EditTextSection(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = @Composable { Icons.Default.Work },
                fieldLabel = "Employment Status",
                oldFieldValue = "Full Time",
                onDone = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
            EditTextSection(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = @Composable { },
                fieldLabel = "Company Name",
                oldFieldValue = "ABC Corp",
                onDone = {}
            )

            Spacer(modifier = Modifier.height(8.dp))
            EditTextSection(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = @Composable { },
                fieldLabel = "Duration of Work",
                oldFieldValue = "2 years",
                onDone = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenderSection() {
    val genderOptions = listOf(
        "Male",
        "Female",
        "Transgender",
        "Gender Neutral",
        "Non-Binary",
        "Prefer Not To Say",
//        "Agender",
//        "Pangender",
//        "Genderqueer",
//        "Two-Spirit",
//        "Third Gender"
    )
    var selectedGender by remember { mutableStateOf("Prefer Not To Say") }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Gender",
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropdownMenu(
            expanded = false, // Change to true to show the dropdown initially
            onDismissRequest = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            genderOptions.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(text = gender) },
                    onClick = { selectedGender = gender })
            }
        }
        OutlinedTextField(
            value = selectedGender,
            onValueChange = { selectedGender = it },
            label = { Text(text = "Select Gender") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BioSection() {
    EditTextSection(
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = @Composable { Icons.Default.Details },
        fieldLabel = "Bio",
        oldFieldValue = "I am a software engineer at ABC Corp",
        onDone = {}
    )
}
