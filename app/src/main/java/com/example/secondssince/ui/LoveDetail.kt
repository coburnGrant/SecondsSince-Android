package com.example.secondssince.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.secondssince.ui.theme.SecondsSinceTheme
import com.example.secondssince.ui.viewModel.LoveViewModel
import com.example.secondssince.ui.viewModel.SecondsSinceUIState

@Composable
fun LoveDetail(
    navController: NavController,
    loveVM: LoveViewModel,
) {
    Scaffold(
        topBar = {
            LoveDetailAppBar(navController)
        }
    ) { innerPadding ->
        val padding = innerPadding
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                // Background image
                if(loveVM.love.loveImageUri == null) {
                    Image(
                        painter = painterResource(loveVM.image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                    )
                } else {
                    AsyncImage(
                        model = loveVM.love.loveImageUri!!,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                    )
                }

                // Overlay gradient
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.8f)
                                )
                            )
                        )
                )

                Column(
                    Modifier
                        .padding(25.dp)
                        .matchParentSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = loveVM.combinedNames,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )

                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }

                    Text(
                        text = loveVM.formattedAnniversary,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.End
                    )
                }
            }

            val uiState by loveVM.secondsSinceUIState.collectAsState()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = "${loveVM.combinedNames} have been have been together for:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center
                )

                SecondsSinceInfo(uiState)
            }
        }
    }
}

@Composable
private fun SecondsSinceInfo(
    uiState: SecondsSinceUIState
) {
    Text(
        text = uiState.totalTimeTogether,
        style = TextStyle(
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            brush = Brush.linearGradient(
                colors = listOf(MaterialTheme.colorScheme.primary, Color.Red)
            )
        ),
        textAlign = TextAlign.Center
    )

    Text(
        text = "In other words...",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val details = uiState.details

        details.forEach { detail ->
            Text(
                text = detail,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    brush = Brush.linearGradient(
                        colors = listOf(MaterialTheme.colorScheme.primary, Color.Red)
                    )
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LoveDetailAppBar(navController: NavController) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Preview
@Composable
fun LoveDetailPreview() {
    SecondsSinceTheme {
        LoveDetail(
            navController = rememberNavController(),
            loveVM = LoveViewModel.testLoveVM()
        )
    }
}