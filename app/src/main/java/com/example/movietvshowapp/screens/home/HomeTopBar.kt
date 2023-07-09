package com.example.movietvshowapp.screens.home

import android.content.res.Configuration
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movietvshowapp.navigation.Screen
import com.example.movietvshowapp.ui.theme.AppPrimaryColor

@ExperimentalMaterial3Api
@Composable
fun HomeTopBar(
    onSearchClicked: () -> Unit,
    onAccountClicked: () -> Unit,
    navController: NavHostController,
) {
    TopAppBar(
        title = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

//            homeScreen = true   => HomeMovie Screen
//            homeScreen = false   => HomeMovie Screen
            val movieScreen = currentDestination?.hierarchy?.any {
                it.route == Screen.HomeMovie.route
            } == true

            var movieFontWeightValue by remember { mutableIntStateOf(300) }

            var tvFontWeightValue by remember { mutableIntStateOf(300) }

            val animatedMovieFontWeightValue by animateIntAsState(
                targetValue = movieFontWeightValue,
                animationSpec = tween(300)
            )

            val animatedTVFontWeightValue by animateIntAsState(
                targetValue = tvFontWeightValue,
                animationSpec = tween(300)
            )

            val movieFontSizeValue: TextUnit
            val tvFontSizeValue: TextUnit

            if(movieScreen){
                movieFontWeightValue = 900
                tvFontWeightValue = 300
                movieFontSizeValue = MaterialTheme.typography.headlineMedium.fontSize
                tvFontSizeValue = MaterialTheme.typography.titleLarge.fontSize
            } else{
                movieFontWeightValue = 300
                tvFontWeightValue = 900
                tvFontSizeValue = MaterialTheme.typography.headlineMedium.fontSize
                movieFontSizeValue = MaterialTheme.typography.titleLarge.fontSize
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onAccountClicked
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Account Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .size(35.dp)
                            .fillMaxHeight()
                    )
                }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate(Screen.HomeMovie.route){
                                popUpTo(navController.graph.id){
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        })
                    ) {
                        Column(
                            modifier = Modifier.drawBehind {
                                drawLine(color = Color.White,start = Offset.Zero,end = Offset.Infinite,strokeWidth = 10f)
                            }
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight(animatedMovieFontWeightValue))) {
                                        append(Screen.HomeMovie.title)
                                    }
                                },
                                color = Color.White,
                                fontSize = movieFontSizeValue,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis                // dealing with overflow
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Box(
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate(Screen.HomeTV.route){
                                popUpTo(navController.graph.id){
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        })
                    ) {
                        Column(
                            modifier = Modifier.drawBehind {
                                drawLine(color = Color.White,start = Offset.Zero,end = Offset.Infinite)
                            }
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight(animatedTVFontWeightValue))) {
                                        append(Screen.HomeTV.title)
                                    }
                                },
                                color = Color.White,
                                fontSize = tvFontSizeValue,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis                // dealing with overflow
                            )
                        }
                    }
                }

            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = if (isSystemInDarkTheme()) {
                Color.Black
            } else {
                AppPrimaryColor
            }
        ),
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    )
}


@ExperimentalMaterial3Api
@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun HomeMovieTopBarPreview() {
    HomeTopBar(
        onAccountClicked = {},
        onSearchClicked = {},
        navController = rememberNavController()
    )
}