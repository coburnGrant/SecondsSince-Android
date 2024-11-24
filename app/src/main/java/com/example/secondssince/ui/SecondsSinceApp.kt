package com.example.secondssince.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.secondssince.ui.theme.SecondsSinceTheme
import com.example.secondssince.ui.viewModel.CreateNewLoveUiState
import com.example.secondssince.ui.viewModel.CreateNewLoveViewModel
import com.example.secondssince.ui.viewModel.LoveListViewModel
import com.example.secondssince.ui.viewModel.LoveViewModel

@Composable
fun SecondsSinceApp(
    createNewLoveViewModel: CreateNewLoveViewModel
) {
    val navController = rememberNavController()
    val loveListViewModel = LoveListViewModel(loves = listOf(LoveViewModel.testLoveVM()))

    NavHost(
        navController = navController,
        startDestination = SecondsSinceScreen.LoveList.name
    ) {
        composable(
            route = SecondsSinceScreen.LoveList.name
        ) {
            LoveList(
                navController = navController,
                loveListViewModel = loveListViewModel
            )
        }

        composable(
            route = SecondsSinceScreen.LoveDetail.name
        ) {
            if(loveListViewModel.selectedLove != null) {
                LoveDetail(
                    navController = navController,
                    loveVM = loveListViewModel.selectedLove!!,
                )
            } else {
                navController.popBackStack()
            }
        }

        composable(
            route = SecondsSinceScreen.CreateNewLove.name
        ) {
            val uiState by createNewLoveViewModel.createNewLoveUiState.collectAsState()

            NewLoveScreen(
                viewModel = createNewLoveViewModel,
                uiState = uiState,
                onCreate = {
                    loveListViewModel .addLove(createNewLoveViewModel.getLove())
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = SecondsSinceScreen.Settings.name
        ) {
            PreferencesScreen()
        }

        composable(
            route = SecondsSinceScreen.Help.name
        ) {
            HelpScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondsSinceAppPreview() {
    SecondsSinceTheme {
        SecondsSinceApp(
            createNewLoveViewModel = CreateNewLoveViewModel(CreateNewLoveUiState())
        )
    }
}
