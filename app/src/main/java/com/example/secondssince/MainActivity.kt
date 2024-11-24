package com.example.secondssince

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.example.secondssince.ui.SecondsSinceApp
import com.example.secondssince.ui.theme.SecondsSinceTheme
import com.example.secondssince.ui.viewModel.CreateNewLoveViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val createNewLoveViewModel = CreateNewLoveViewModel()

        // Register the content picker
        val getContent = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            Log.i("imageSelection", "selected uri $uri")
            createNewLoveViewModel.updateSelectedImageUri(uri)
        }

        createNewLoveViewModel.setGetContent(getContent)

        // Pass the function as part of the state for composable usage
        setContent {
            SecondsSinceTheme {
                SecondsSinceApp(createNewLoveViewModel)
            }
        }
    }
}