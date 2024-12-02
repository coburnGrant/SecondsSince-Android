package com.example.secondssince.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.secondssince.ui.theme.SecondsSinceTheme
import com.example.secondssince.ui.viewModel.CreateNewLoveViewModel
import com.example.secondssince.ui.viewModel.LoveListViewModel

@Composable
fun SecondsSinceApp(
    createNewLoveViewModel: CreateNewLoveViewModel,
    loveListViewModel: LoveListViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SecondsSinceScreen.LoveList.name
    ) {
        composable(
            route = SecondsSinceScreen.Help.name,
            enterTransition = { enterTransition(TransitionDirection.LeftToRight) },
            exitTransition = { exitTransition(TransitionDirection.RightToLeft) }
        ) {
            HelpScreen {
                navController.popBackStack()
            }
        }

        composable(
            route = SecondsSinceScreen.LoveList.name
        ) {
            LoveList(
                navController = navController,
                loveListViewModel = loveListViewModel
            )
        }

        composable(
            route = SecondsSinceScreen.LoveDetail.name,
            enterTransition =  { enterTransition() },
            exitTransition = { exitTransition() }
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
            route = SecondsSinceScreen.CreateNewLove.name,
            enterTransition =  { enterTransition() },
            exitTransition = { exitTransition() }
        ) {
            val uiState by createNewLoveViewModel.createNewLoveUiState.collectAsState()

            NewLoveScreen(
                navController = navController,
                viewModel = createNewLoveViewModel,
                uiState = uiState,
                onCreate = {
                    loveListViewModel.addLove(createNewLoveViewModel.getLove())
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = SecondsSinceScreen.Settings.name,
            enterTransition =  { enterTransition() },
            exitTransition = { exitTransition() }
        ) {
            PreferencesScreen {
                navController.popBackStack()
            }
        }
    }
}

enum class TransitionDirection {
    RightToLeft, LeftToRight
}

fun enterTransition(direction: TransitionDirection = TransitionDirection.RightToLeft): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = {
           if (direction == TransitionDirection.RightToLeft) it else -it
        }
    ) + fadeIn(initialAlpha = 0.3f)
}
fun exitTransition(direction: TransitionDirection = TransitionDirection.LeftToRight): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = {
            if (direction == TransitionDirection.LeftToRight) it else -it
        }
    ) + fadeOut()
}

@Preview(showBackground = true)
@Composable
fun SecondsSinceAppPreview() {
    SecondsSinceTheme {
//        SecondsSinceApp(
//            createNewLoveViewModel = CreateNewLoveViewModel(CreateNewLoveUiState())
//        )
    }
}
