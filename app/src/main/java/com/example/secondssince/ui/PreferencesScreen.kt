package com.example.secondssince.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.secondssince.ui.theme.SecondsSinceTheme
import com.example.secondssince.ui.viewModel.PreferencesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(
    viewModel: PreferencesViewModel,
    back: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Preferences")
                },
                navigationIcon = {
                    IconButton(
                        onClick = back
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Notifications",
                style = MaterialTheme.typography.titleMedium,
            )

            val notifyOnSpecialDay = viewModel.notifyOnSpecialDay.collectAsState(false)

            TogglePreference(
                title = "Special Day",
                description = "Notify me when it is a special day for my loves",
                isOn = notifyOnSpecialDay.value,
                onCheckedChanged = {
                    viewModel.updateNotifyOnSpecialDay(it)
                }
            )

            val selectedTime = viewModel.selectedTime.collectAsState()

            val timePickerState = rememberTimePickerState(
                initialHour = selectedTime.value.first,
                initialMinute = selectedTime.value.second,
                is24Hour = false
            )

            LaunchedEffect(timePickerState) {
                snapshotFlow { Pair(timePickerState.hour, timePickerState.minute) }
                    .collect { (hour, minute) ->
                        viewModel.updateTime(hour, minute)
                    }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text("Notify on Special Days at")

                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        clockDialColor = MaterialTheme.colorScheme.secondary,
                        timeSelectorSelectedContainerColor =  Color.Red.copy(alpha = 0.5F),
                        timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.secondary,
                        periodSelectorSelectedContainerColor = Color.Red.copy(alpha = 0.5F)
                    )
                )
            }

            val notifyOnSpecialMoment = viewModel.notifyOnSpecialMoment.collectAsState(false)

            TogglePreference(
                title = "Special Moment",
                description = "Notify me when it is a special moment for my loves",
                isOn = notifyOnSpecialMoment.value,
                onCheckedChanged = {
                    viewModel.updateNotifyOnSpecialMoment(it)
                }
            )
        }
    }
}

@Composable
fun TogglePreference(
    title: String,
    description: String,
    isOn: Boolean,
    onCheckedChanged: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f) // Take remaining space for text column
        ) {
            Text(title)

            Text(
                text = description,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Switch(
            checked = isOn,
            onCheckedChange = onCheckedChanged,
            modifier = Modifier.padding(5.dp),
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White, // Thumb (toggle) when checked
                checkedTrackColor = Color.Red.copy(alpha = 0.5f), // Track when checked
                uncheckedThumbColor = Color.White, // Thumb when unchecked
                uncheckedTrackColor = MaterialTheme.colorScheme.secondary // Track when unchecked
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreferencesPreview() {
    SecondsSinceTheme {
        PreferencesScreen(null!!) {}
    }
}