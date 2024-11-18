package com.example.secondssince.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.secondssince.model.Love
import com.example.secondssince.ui.theme.SecondsSinceTheme
import com.example.secondssince.ui.viewModel.LoveListViewModel
import com.example.secondssince.ui.viewModel.LoveViewModel
import java.util.Date

@Composable
fun SecondsSinceApp() {
    val love = Love(
        userName = "Grant",
        loveName = "Dream",
        anniversary = Date(1638662400000)
    )
    val navController = rememberNavController()
    val loveViewModel = LoveViewModel(love)
    val viewModel = LoveListViewModel(loves = listOf(loveViewModel))

    NavHost(
        navController = navController,
        startDestination = SecondsSinceScreen.LoveList.name
    ) {
        composable(
            route = SecondsSinceScreen.LoveList.name
        ) {
            LoveList(
                navController = navController,
                loveListViewModel = viewModel
            )
        }

        composable(
            route = SecondsSinceScreen.LoveDetail.name
        ) {
            LoveDetail(
                navController = navController,
                loveVM = loveViewModel,
            )
        }

        composable(
            route = SecondsSinceScreen.Settings.name
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondsSinceAppPreview() {
    SecondsSinceTheme {
        SecondsSinceApp()
    }
}
