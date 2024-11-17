package com.example.secondssince.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.secondssince.R
import com.example.secondssince.model.Love

@Composable
fun LoveListRow(
    love: Love,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Image filling the button
                Image(
                    painter = painterResource(R.drawable.grant_and_dream),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                )

                // Text content at the bottom
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(25.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "love",
                            tint = Color.Red
                        )

                        Text(
                            text = "${love.userName} & ${love.loveName}",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Text(
                        text = formatDate(love.anniversary),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}