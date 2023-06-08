package com.devcommop.myapplication.ui.components.editprofile

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun DateOfBirthSection(currentValue: String, onDateSelected: (String) -> Unit) {
    val selectedDate = remember { mutableStateOf(currentValue) }
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp , horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .padding(4.dp),
                text = "Date of Birth: ${selectedDate.value}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.EditCalendar,
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        showDatePickerDialog(context) { year, month, dayOfMonth ->
                            val selectedDateFormatted =
                                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                    .format(Date(year - 1900, month, dayOfMonth))
                            selectedDate.value = selectedDateFormatted
                        }
                    },
                contentDescription = null
            )
            Spacer(Modifier.padding(horizontal = 4.dp))
            Icon(
                imageVector = Icons.Default.Save,
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        if (selectedDate.value.isNotEmpty()) {
                            onDateSelected(selectedDate.value)
                        }
                    },
                contentDescription = null
            )
            Spacer(Modifier.padding(horizontal = 4.dp))

        }
    }
}

fun showDatePickerDialog(
    context: Context,
    onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit
) {
    val currentDate = Calendar.getInstance()
    val year = currentDate.get(Calendar.YEAR)
    val month = currentDate.get(Calendar.MONTH)
    val day = currentDate.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelected(year, month, dayOfMonth)
        },
        year,
        month,
        day
    ).show()
}

@Preview(showBackground = true)
@Composable
fun DOBSectionPreview() {
    DateOfBirthSection(" ", {})
}