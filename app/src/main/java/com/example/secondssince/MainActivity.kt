package com.example.secondssince

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.secondssince.ui.SecondsSinceApp
import com.example.secondssince.ui.theme.SecondsSinceTheme

class MainActivity : ComponentActivity() {
//    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecondsSinceTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        SecondsSinceTopAppBar(navController)
                    }
                ) { innerPadding ->
//                    val windowSize = calculateWindowSizeClass(this).widthSizeClass
                    SecondsSinceApp(
                        innerPadding = innerPadding,
                        navController = navController
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondsSinceTopAppBar(
    navController: NavController
) {
    TopAppBar(
        title = {},
        actions = {
            IconButton(onClick = {
                navController.navigate("Settings")
            }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,  // Make the app bar transparent
            titleContentColor = Color.White,     // Set the title color (if needed)
            actionIconContentColor = Color.White // Set the color of action icons
        ),
    )
}