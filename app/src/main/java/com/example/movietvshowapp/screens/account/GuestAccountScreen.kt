package com.example.movietvshowapp.screens.account

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import com.example.movietvshowapp.R
import com.example.movietvshowapp.navigation.Screen
import com.example.movietvshowapp.ui.theme.AppPrimaryColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.KSuspendFunction1

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagingApi::class)
@Composable
fun GuestAccountScreen(
    navController: NavHostController,
    accountViewModel: AccountViewModel = hiltViewModel()
){
    val onEvent = accountViewModel::onEvent

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
        ) {
            Box(
                modifier = Modifier.padding(10.dp),
                contentAlignment = Alignment.TopStart
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(color)
                                .padding(5.dp),
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Stack Icon",
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Logged out")
                                withContext(Dispatchers.Main){
                                    navController.navigate(Screen.Login.route){
                                        popUpTo(navController.graph.id){
                                            inclusive = true
                                        }
                                    }
                                }
                                onEvent(AccountEvent.DeleteSession)
                            }
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(color)
                                .padding(5.dp),
                            imageVector = Icons.Filled.Logout,
                            contentDescription = "Logout Icon",
                            tint = Color.White
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .width(125.dp)
                            .height(125.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.pngtreeoval_user_avatar_placeholder_black_6796229),
                        contentDescription = "Placeholder Profile Image",
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append("Guest")
                                }
                            },
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,                // dealing with overflow
                            color = Color.White
                        )
                    }
                }
            }
        }
    }


}

@Preview("Light Mode")
@Preview("Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewGuestAccountScreen(){
    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
    ) {
        Box(
            modifier = Modifier.padding(10.dp),
            contentAlignment = Alignment.TopStart
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
//                    onBackStackClicked()
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(color)
                            .padding(5.dp),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Stack Icon",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = {
//                    onBackStackClicked()
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(color)
                            .padding(5.dp),
                        imageVector = Icons.Filled.Logout,
                        contentDescription = "Back Stack Icon",
                        tint = Color.White
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .width(125.dp)
                        .height(125.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.pngtreeoval_user_avatar_placeholder_black_6796229),
                    contentDescription = "Placeholder Profile Image",
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    // Title
                    Text(
//                    buildAnnotatedString
                        modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append("Guest")
                            }
                        },
//                            color = Color.White,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,                // dealing with overflow
                        color = Color.White
                    )
                }
            }
        }
    }
}