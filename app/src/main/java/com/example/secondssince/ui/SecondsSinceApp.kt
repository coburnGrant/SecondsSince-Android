package com.example.secondssince.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.secondssince.model.Love
import com.example.secondssince.ui.theme.SecondsSinceTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SecondsSinceApp(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = "Love List"
    ) {
        composable(
            route = "Love List"
        ) {
            LoveList(
                navController = navController,
                innerPadding = innerPadding
            )
        }

        composable(
            route = "Love Detail"
        ) {
            LoveDetail(
                navController = navController,
                love = Love("Grant", "Dream", Date())
            )
        }

        composable(route = "Settings") {

        }
    }
}

fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
    return formatter.format(date)
}

@Composable
fun LoveDetail(
    navController: NavController,
    love: Love
) {

}


@Preview(showBackground = true)
@Composable
fun SecondsSinceAppPreview() {
    SecondsSinceTheme {
        SecondsSinceApp(
            innerPadding = PaddingValues(0.dp),
            navController = rememberNavController()
        )
    }
}
