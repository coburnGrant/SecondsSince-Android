package com.example.secondssince

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.secondssince.ui.SecondsSinceApp
import com.example.secondssince.ui.theme.SecondsSinceTheme

class MainActivity : ComponentActivity() {
//    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecondsSinceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    val windowSize = calculateWindowSizeClass(this).widthSizeClass

                    SecondsSinceApp(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}