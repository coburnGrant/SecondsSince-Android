package com.example.secondssince.ui

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.secondssince.ui.theme.SecondsSinceTheme
import com.example.secondssince.ui.viewModel.CreateNewLoveUiState
import com.example.secondssince.ui.viewModel.CreateNewLoveViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewLoveScreen(
    viewModel: CreateNewLoveViewModel,
    uiState: CreateNewLoveUiState,
    navController: NavController,
    onCreate: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text("Create New Love")
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Username TextField
            TextField(
                value =uiState.username,
                onValueChange = { viewModel.updateUsername(it) },
                label = { Text("Your name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Red,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary
                )
            )

            // Love Name TextField
            TextField(
                value = uiState.loveName,
                onValueChange = { viewModel.updateLoveName(it) },
                label = { Text("Love name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Red,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary
                )
            )

            // Anniversary Date Picker
            val state = remember { DatePickerState(locale = CalendarLocale.getDefault()) }

            DatePicker(
                state = state,
                headline = {
                    Text(
                        "Your anniversary",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                },
                title = null,
                showModeToggle = false
            )

            // Display selected image
            uiState.selectedImageUri?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
            }

            // Add Image Button

            Button(
                onClick = {
                    uiState.getContent?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Add love image",
                        color = Color.White,
                    )

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Add love image",
                        tint = Color.White
                    )
                }
            }

            // Create New Love Button
            Button(
                onClick = {
                    state.selectedDateMillis?.let {
                        viewModel.updateSelectedDate(Date(it))
                    }

                    onCreate()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Create new love",
                        color = Color.White,
                    )

                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "Add love image",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NewLoveScreenPreview() {
    SecondsSinceTheme {
        NewLoveScreen(
            navController = rememberNavController(),
            viewModel = CreateNewLoveViewModel(CreateNewLoveUiState(),),
            uiState = CreateNewLoveUiState(),
            onCreate = {}
        )
    }
}