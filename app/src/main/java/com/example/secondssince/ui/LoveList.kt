package com.example.secondssince.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.secondssince.ui.theme.SecondsSinceTheme
import com.example.secondssince.ui.viewModel.LoveListViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    InternalCoroutinesApi::class
)

@Composable
fun LoveList(
    navController: NavController,
    loveListViewModel: LoveListViewModel
) {
    val screenHeight: Int = LocalConfiguration.current.screenHeightDp
    val loves by loveListViewModel.loves.collectAsState()

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

            val isRefreshing by remember { mutableStateOf(false) }
            val state = PullToRefreshState()

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .pullToRefresh(
                        isRefreshing = isRefreshing,
                        state = state,
                        onRefresh = {
                            loveListViewModel.refreshLoves()
                        }
                    )
            ) {
                items(
                    items = loves,
                    key = { loveVM ->
                        loveVM.love.id
                    }
                ) { loveVM ->
                    SwipeBox(
                        onDelete = {
                            loveListViewModel.deleteLove(loveVM.love)
                        },
                    ) {
                        LoveListRow(
                            loveVM = loveVM,
                            modifier = Modifier
                                .animateContentSize(),
                            onClick = {
                                loveListViewModel.selectedLove = loveVM
                                navController.navigate(SecondsSinceScreen.LoveDetail.name)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeBox(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    content: @Composable () -> Unit
) {
    val swipeState = rememberSwipeToDismissBoxState()

    var icon: ImageVector? = null
    lateinit var alignment: Alignment
    val color: Color
    val padding = 20.dp

    when (swipeState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> {
            icon = Icons.Outlined.Delete
            alignment = Alignment.CenterEnd
            color = MaterialTheme.colorScheme.errorContainer
        }

        SwipeToDismissBoxValue.StartToEnd -> {
            icon = null
            alignment = Alignment.CenterStart
            color = Color.Transparent
        }

        SwipeToDismissBoxValue.Settled -> {
            icon = Icons.Outlined.Delete
            alignment = Alignment.CenterEnd
            color = Color.Transparent
        }
    }

    SwipeToDismissBox(
        modifier = modifier.animateContentSize(),
        state = swipeState,
        backgroundContent = {
            Box(
                contentAlignment = alignment,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = padding)
                    .padding(end = padding)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(
                            topStart = 0.dp, topEnd = 20.dp, bottomStart = 0.dp, bottomEnd = 20.dp
                        )
                    )
            ) {
                icon?.let {
                    Icon(
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .padding(end = 10.dp),
                        imageVector = it, contentDescription = null
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(padding)
        ) {
            content()
        }
    }

    when (swipeState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            onDelete()
        }
        SwipeToDismissBoxValue.StartToEnd -> {
            LaunchedEffect(swipeState) {
                swipeState.snapTo(SwipeToDismissBoxValue.Settled)
            }
        }
        SwipeToDismissBoxValue.Settled -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoveListAppBar(
    navController: NavController
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(SecondsSinceScreen.Help.name)
            }) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Information",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
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

@Preview
@Composable
fun LoveListPreview() {
    SecondsSinceTheme {
//        LoveList(
//            navController = rememberNavController(),
//            loveListViewModel = LoveListViewModel(
//                loves = listOf(LoveViewModel.testLoveVM())
//            )
//        )
    }
}