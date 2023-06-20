package com.devcommop.myapplication.ui.components.settings.preferences

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devcommop.myapplication.utils.Constants

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OtherSettings() {

    val themeOptions = listOf("System Default", "Light", "Dark")
    val selectedThemeIndex = remember { mutableStateOf(0) }

    val sharedPreferences = LocalContext.current.applicationContext.getSharedPreferences(
        Constants.BASIC_SHARED_PREF_NAME, Context.MODE_PRIVATE
    )
    val editor = sharedPreferences.edit()

    val onThemeSelected: (Int) -> Unit = { index ->
        selectedThemeIndex.value = index

        // Save the selected theme to SharedPreferences
        editor.putInt("selectedThemeIndex", index)
        editor.apply()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Choose a Theme", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Display theme options as radio buttons
        themeOptions.forEachIndexed { index, theme ->
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onThemeSelected(index) }
                    .padding(8.dp)) {
                RadioButton(selected = selectedThemeIndex.value == index,
                    onClick = { onThemeSelected(index) })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = theme, style = MaterialTheme.typography.bodyLarge)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Restart app to apply changes",
            style = MaterialTheme.typography.labelMedium,
            color = Color.Red,
            modifier = Modifier.align(Alignment.End)
        )
    }

    // Retrieve the previously selected theme from SharedPreferences
    LaunchedEffect(Unit) {
        val savedThemeIndex = sharedPreferences.getInt("selectedThemeIndex", 0)
        selectedThemeIndex.value = savedThemeIndex
    }
}