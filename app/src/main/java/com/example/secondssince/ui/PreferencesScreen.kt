package com.example.secondssince.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PreferencesScreen() {
    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text("Hello World!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreferencesPreview() {
    PreferencesScreen()
}