package com.devcommop.myapplication.ui.components.mainscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "Search",
    onSearch: (String) -> Unit = {},
    leadingIcon: ImageVector = Icons.Default.Search,
    trailingIcon: ImageVector = Icons.Default.Clear
) {
    var textState by remember { mutableStateOf("") }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = leadingIcon,
            contentDescription = null,
            modifier = Modifier.padding(start = 8.dp, end = 4.dp)
        )

        BasicTextField(
            value = textState,
            onValueChange = { newText ->
                textState = newText
                onSearch(newText)
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            cursorBrush = SolidColor(Color.Black)
        )

        if (textState.isNotEmpty()) {

            IconButton(
                onClick = {
                    textState = TextFieldValue().toString()
                    onSearch("")
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun SearchScreen( modifier: Modifier = Modifier,
                  hint: String = "Search",
                  onSearch: (String) -> Unit = {},
                  leadingIcon: ImageVector = Icons.Default.Search,
                  trailingIcon: ImageVector = Icons.Default.Clear
){
    Surface(
        modifier = Modifier.fillMaxSize()
    ){
        modifier.width(30.dp).padding(horizontal = 16.dp, vertical = 32.dp).safeContentPadding()
        Column()
        {
            SearchBar(modifier , hint , onSearch , leadingIcon , trailingIcon)
            //TODO: show list of results here
        }
    }
}

@Preview(showBackground = true )
@Composable
private fun SearchScreenPreview(){
    SearchScreen()
}