package com.example.movietvshowapp.screens.search

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movietvshowapp.ui.theme.AppPrimaryColor

@ExperimentalMaterial3Api
@Composable
fun SearchTopBar(
    state: SearchState,
    onBackStackClicked: () -> Unit,
    onTextChange: (String) -> Unit,
    onSearchClicked: () -> Unit
){
    TopAppBar(
        title = {

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    modifier = Modifier.fillMaxSize(),
                    value = state.searchTextState,
                    onValueChange = { onTextChange(it) },
                    placeholder = {
                        Text(
                            modifier = Modifier.alpha(0.38f).padding(3.dp),
                            text = "Search Here...",
                            color = Color.White
                        )
                    },
                    singleLine = true,
                    leadingIcon = {
                        IconButton(
                            onClick = {
                                onBackStackClicked()
                                onTextChange("")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back Stack Icon",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(35.dp)
                                    .fillMaxHeight()
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = { onSearchClicked() }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        textColor = Color.White,
                        cursorColor = Color.White.copy(alpha = 0.38f)
                    )
                )
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
            IconButton(
                onClick = {
                    if(state.searchTextState.isNotEmpty()){
                        onTextChange("")
                    } else{
                        onBackStackClicked()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = Color.White.copy(alpha = 0.38f),
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSearchTopBar(){
    SearchTopBar(
        state = SearchState(),
        onBackStackClicked = { /*TODO*/ },
        onSearchClicked = {},
        onTextChange = {}
    )
}