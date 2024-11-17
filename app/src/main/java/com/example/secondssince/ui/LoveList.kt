package com.example.secondssince.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.secondssince.model.Love
import java.util.Date

@Composable
fun LoveList(
    navController: NavController,
    loves: List<Love> = listOf<Love>(Love("Grant", "Dream", Date())),
    innerPadding: PaddingValues,
) {
    val screenHeight: Int = LocalConfiguration.current.screenHeightDp

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
                items = loves,
                key = { love ->
                    love.userName.length
                }
            ) { love ->
                LoveListRow(
                    love = love,
                    onClick = {
                        // TODO: set selected love to this love
                        navController.navigate("Love Detail")
                    }
                )
            }
        }
    }
}