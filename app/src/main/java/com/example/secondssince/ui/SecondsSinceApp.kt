package com.example.secondssince.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.secondssince.ui.theme.SecondsSinceTheme

@Composable
fun SecondsSinceApp(
    modifier: Modifier = Modifier
) {
    Text("Hello SecondsSinceApp!")
}


@Preview(showBackground = true)
@Composable
fun SecondsSinceAppPreview() {
    SecondsSinceTheme {
        SecondsSinceApp()
    }
}
