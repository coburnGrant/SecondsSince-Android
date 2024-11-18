package com.example.secondssince.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.secondssince.ui.viewModel.LoveListViewModel

@Composable
fun LoveList(
    navController: NavController,
    loveListViewModel: LoveListViewModel
) {
    val screenHeight: Int = LocalConfiguration.current.screenHeightDp

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            LoveListAppBar(navController)
        }
    ) { innerPadding ->
        Box {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeight / 5).dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Red.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        )
                    )
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                items(
                    items = loveListViewModel.loves,
                    key = { loveVM ->
                        loveVM.love.id
                    }
                ) { loveVM ->
                    LoveListRow(
                        loveVM = loveVM,
                        onClick = {
                            // TODO: set selected love to this love
                            navController.navigate(SecondsSinceScreen.LoveDetail.name)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoveListAppBar(
    navController: NavController
) {
    TopAppBar(
        title = {},
        actions = {
            IconButton(onClick = {
                navController.navigate(SecondsSinceScreen.CreateNewLove.name)
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Love",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(onClick = {
                navController.navigate(SecondsSinceScreen.Settings.name)
            }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
    )
}