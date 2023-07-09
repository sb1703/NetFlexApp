package com.example.movietvshowapp.screens.account

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.movietvshowapp.navigation.Screen
import com.example.movietvshowapp.ui.theme.AppPrimaryColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.KSuspendFunction1

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    accountState: AccountState,
    onDataLoaded: () -> Unit,
    onEvent: KSuspendFunction1<AccountEvent, Unit>
){
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }

    LaunchedEffect(key1 = Unit){
        delay(2000)
        onDataLoaded()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append("TMDB Login")
                            }
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,                // dealing with overflow
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = accountState.username,
                        onValueChange = {
                            scope.launch {
                                onEvent(AccountEvent.UpdateUsername(it))
                            } },
                        placeholder = {
                            Text(
                                modifier = Modifier
                                    .alpha(0.38f)
                                    .padding(3.dp)
                                    .fillMaxWidth(),
                                text = "Username",
                                color = Color.White
                            )
                        },
                        singleLine = true,
                        leadingIcon = {
                            IconButton(
                                onClick = {  }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Username Icon",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(35.dp)
                                        .fillMaxHeight()
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                scope.launch {
                                    isLoggedIn(navController = navController,onEvent = onEvent,snackbarHostState = snackbarHostState)
                                }
                            }
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            textColor = Color.White,
                            cursorColor = Color.White.copy(alpha = 0.38f)
                        )
                    )
                    TextField(
                        value = accountState.password,
                        onValueChange = {
                            scope.launch {
                                onEvent(AccountEvent.UpdatePassword(it))
                            }
                                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                modifier = Modifier
                                    .alpha(0.38f)
                                    .padding(3.dp)
                                    .fillMaxWidth(),
                                text = "Password",
                                color = Color.White
                            )
                        },
                        leadingIcon = {
                            IconButton(
                                onClick = {  }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Username Icon",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(35.dp)
                                        .fillMaxHeight()
                                )
                            }
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        if(accountState.passwordVisibility){
                                            onEvent(AccountEvent.SetPasswordVisibilityFalse)
                                        } else{
                                            onEvent(AccountEvent.SetPasswordVisibilityTrue)
                                        }
                                    }
                                }
                            ) {
                            val image = if(accountState.passwordVisibility){
                                Icons.Filled.Visibility
                            } else{
                                Icons.Filled.VisibilityOff
                            }
                                Icon(
                                    imageVector = image,
                                    contentDescription = "Password Visibility",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(35.dp)
                                        .fillMaxHeight()
                                )
                            }
                        },
                        visualTransformation = if(accountState.passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                scope.launch {
                                    isLoggedIn(navController = navController,onEvent = onEvent,snackbarHostState = snackbarHostState)
                                }
                            }
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            textColor = Color.White,
                            cursorColor = Color.White.copy(alpha = 0.38f)
                        )
                    )
                    Button(
                        onClick = {
                            scope.launch {
                                isLoggedIn(navController = navController,onEvent = onEvent,snackbarHostState = snackbarHostState)
                            }
                        },
                        modifier = Modifier.width(150.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append("Login")
                                }
                            },
                            color = Color.Black,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,                // dealing with overflow
                        )
                    }
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                                append("OR")
                            }
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,                // dealing with overflow
                    )
                    Button(
                        onClick = {
                            scope.launch {
                                onEvent(AccountEvent.UpdateIsGuestLoggedInTrue)
                                onEvent(AccountEvent.UpdateIsGuestSession)
                                snackbarHostState.showSnackbar(
                                    "Logged In As Guest"
                                )
                                withContext(Dispatchers.Main){
                                    navController.popBackStack()
                                    navController.navigate(Screen.HomeMovie.route)
                                }
                            }
                                  },
                        modifier = Modifier.width(150.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append("Guest Session")
                                }
                            },
                            color = Color.Black,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,                // dealing with overflow
                        )
                    }
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append("Don't Have a Account?")
                            }
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,                // dealing with overflow
                        modifier = Modifier.clickable {
                            val browserIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.themoviedb.org/signup")
                            )
                            ContextCompat.startActivity(context, browserIntent, null)
                        },
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

suspend fun isLoggedIn(
    navController: NavHostController,
    onEvent: KSuspendFunction1<AccountEvent, Unit>,
    snackbarHostState: SnackbarHostState
){
    try {
        onEvent(AccountEvent.UpdateRequestToken)
        onEvent(AccountEvent.UpdateLoginRequestToken)
        onEvent(AccountEvent.UpdateSession)
        onEvent(AccountEvent.UpdateIsLoggedInTrue)
        onEvent(AccountEvent.UpdateAccountDetails)
        snackbarHostState.showSnackbar("Login Successful")
        withContext(Dispatchers.Main){
            navController.popBackStack()
            navController.navigate(Screen.HomeMovie.route)
        }
    } catch (e: Exception){
        snackbarHostState.showSnackbar("Invalid Username or Password")
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewLoginScreen(){

    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
//        snackbarHost =
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(30.dp)
//            verticalArrangement = Arrangement.Center
                ) {
                    Text(
//                    buildAnnotatedString
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append("TMDB Login")
                            }
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,                // dealing with overflow
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = "",
                        onValueChange = {  },
                        placeholder = {
                            Text(
                                modifier = Modifier
                                    .alpha(0.38f)
                                    .padding(3.dp)
                                    .fillMaxWidth(),
                                text = "Username",
                                color = Color.White
                            )
                        },
                        singleLine = true,
                        leadingIcon = {
                            IconButton(
                                onClick = {  }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Username Icon",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(35.dp)
                                        .fillMaxHeight()
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {  }
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            textColor = Color.White,
                            cursorColor = Color.White.copy(alpha = 0.38f)
                        )
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = "",
                        onValueChange = {  },
                        placeholder = {
                            Text(
                                modifier = Modifier
                                    .alpha(0.38f)
                                    .padding(3.dp)
                                    .fillMaxWidth(),
                                text = "Password",
                                color = Color.White
                            )
                        },
                        singleLine = true,
                        leadingIcon = {
                            IconButton(
                                onClick = {  }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Username Icon",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(35.dp)
                                        .fillMaxHeight()
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {  }
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            textColor = Color.White,
                            cursorColor = Color.White.copy(alpha = 0.38f)
                        )
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.width(150.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(
//                    buildAnnotatedString
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append("Login")
                                }
                            },
                            color = Color.Black,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,                // dealing with overflow
                        )
                    }
                    Text(
//                    buildAnnotatedString
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                                append("OR")
                            }
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,                // dealing with overflow
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.width(150.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(
//                    buildAnnotatedString
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append("Guest Session")
                                }
                            },
                            color = Color.Black,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,                // dealing with overflow
                        )
                    }
                    Text(
//                    buildAnnotatedString
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append("Don't Have a Account?")
                            }
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,                // dealing with overflow
                        modifier = Modifier.clickable {

                        },
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}