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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import com.example.movietvshowapp.R
import com.example.movietvshowapp.navigation.Screen
import com.example.movietvshowapp.screens.search.SearchItem
import com.example.movietvshowapp.ui.theme.AppPrimaryColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.KSuspendFunction1

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountScreen(
    navController: NavHostController,
    accountState: AccountState,
    onEvent: KSuspendFunction1<AccountEvent, Unit>,
    onClickUpdateMovieId: (Int)->Unit
){
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }

    val activeContainerColor = Color.White
    val disabledContainerColor = color

    val activeContentColor = Color.Black
    val disabledContentColor = Color.White

    val favouriteMovieContainerColor: Color
    val favouriteMovieContentColor: Color

    val favouriteTVContainerColor: Color
    val favouriteTVContentColor: Color

    val watchlistMovieContainerColor: Color
    val watchlistMovieContentColor: Color

    val watchlistTVContainerColor: Color
    val watchlistTVContentColor: Color

    LaunchedEffect(key1 = Unit){
        onEvent(AccountEvent.UpdateGetFavouriteMovie)
        onEvent(AccountEvent.UpdateGetFavouriteTV)
        onEvent(AccountEvent.UpdateGetWatchlistMovie)
        onEvent(AccountEvent.UpdateGetWatchlistTV)
    }

    val getFavouriteMovie = accountState.getFavouriteMovie?.collectAsLazyPagingItems()
    val getFavouriteTV = accountState.getFavouriteTV?.collectAsLazyPagingItems()
    val getWatchlistMovie = accountState.getWatchlistMovie?.collectAsLazyPagingItems()
    val getWatchlistTV = accountState.getWatchlistTV?.collectAsLazyPagingItems()


    when(accountState.listType){
        ListType.NONE -> {
            favouriteMovieContainerColor = disabledContainerColor
            favouriteMovieContentColor = disabledContentColor
            favouriteTVContainerColor = disabledContainerColor
            favouriteTVContentColor = disabledContentColor
            watchlistMovieContainerColor = disabledContainerColor
            watchlistMovieContentColor = disabledContentColor
            watchlistTVContainerColor = disabledContainerColor
            watchlistTVContentColor = disabledContentColor
        }
        ListType.FAVOURITE_MOVIE_LIST -> {
            favouriteMovieContainerColor = activeContainerColor
            favouriteMovieContentColor = activeContentColor
            favouriteTVContainerColor = disabledContainerColor
            favouriteTVContentColor = disabledContentColor
            watchlistMovieContainerColor = disabledContainerColor
            watchlistMovieContentColor = disabledContentColor
            watchlistTVContainerColor = disabledContainerColor
            watchlistTVContentColor = disabledContentColor
        }
        ListType.FAVOURITE_TV_LIST -> {
            favouriteMovieContainerColor = disabledContainerColor
            favouriteMovieContentColor = disabledContentColor
            favouriteTVContainerColor = activeContainerColor
            favouriteTVContentColor = activeContentColor
            watchlistMovieContainerColor = disabledContainerColor
            watchlistMovieContentColor = disabledContentColor
            watchlistTVContainerColor = disabledContainerColor
            watchlistTVContentColor = disabledContentColor
        }
        ListType.WATCHLIST_MOVIE_LIST -> {
            favouriteMovieContainerColor = disabledContainerColor
            favouriteMovieContentColor = disabledContentColor
            favouriteTVContainerColor = disabledContainerColor
            favouriteTVContentColor = disabledContentColor
            watchlistMovieContainerColor = activeContainerColor
            watchlistMovieContentColor = activeContentColor
            watchlistTVContainerColor = disabledContainerColor
            watchlistTVContentColor = disabledContentColor
        }
        ListType.WATCHLIST_TV_LIST -> {
            favouriteMovieContainerColor = disabledContainerColor
            favouriteMovieContentColor = disabledContentColor
            favouriteTVContainerColor = disabledContainerColor
            favouriteTVContentColor = disabledContentColor
            watchlistMovieContainerColor = disabledContainerColor
            watchlistMovieContentColor = disabledContentColor
            watchlistTVContainerColor = activeContainerColor
            watchlistTVContentColor = activeContentColor
        }
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
                    if(accountState.accountDetails.avatar.tmdb.avatarPath != null){
                        AsyncImage(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .width(125.dp)
                                .height(125.dp)
                                .clip(CircleShape),
                            model = "https://image.tmdb.org/t/p/w185${accountState.accountDetails.avatar.tmdb.avatarPath}",
                            contentDescription = "PosterPath Image",
                            imageLoader = ImageLoader.Builder(context).crossfade(true).build()
                        )
                    } else{
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
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append(accountState.accountDetails.userName)
                                }
                            },
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,                // dealing with overflow
                            color = Color.White
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                    append(accountState.accountDetails.name)
                                }
                            },
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,                // dealing with overflow
                            color = Color.White
                        )
                    }
                }

                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp))
                {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                if(accountState.listType!= ListType.FAVOURITE_MOVIE_LIST){
                                    scope.launch {
                                        onEvent(AccountEvent.UpdateListType(ListType.FAVOURITE_MOVIE_LIST))
                                        cancel()
                                    }
                                } else{
                                    scope.launch {
                                        onEvent(AccountEvent.UpdateListType(ListType.NONE))
                                        cancel()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = favouriteMovieContainerColor,
                                contentColor = favouriteMovieContentColor
                            )
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                        append("Favourite Movie List")
                                    }
                                },
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis                // dealing with overflow
                            )
                        }
                        Button(
                            onClick = {
                                if(accountState.listType != ListType.FAVOURITE_TV_LIST){
                                    scope.launch {
                                        onEvent(AccountEvent.UpdateListType(ListType.FAVOURITE_TV_LIST))
                                        cancel()
                                    }
                                } else{
                                    scope.launch {
                                        onEvent(AccountEvent.UpdateListType(ListType.NONE))
                                        cancel()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = favouriteTVContainerColor,
                                contentColor = favouriteTVContentColor
                            )
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                        append("Favourite TV List")
                                    }
                                },
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis                // dealing with overflow
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                if(accountState.listType != ListType.WATCHLIST_MOVIE_LIST){
                                    scope.launch {
                                        onEvent(AccountEvent.UpdateListType(ListType.WATCHLIST_MOVIE_LIST))
                                        cancel()
                                    }
                                } else{
                                    scope.launch {
                                        onEvent(AccountEvent.UpdateListType(ListType.NONE))
                                        cancel()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = watchlistMovieContainerColor,
                                contentColor = watchlistMovieContentColor
                            )
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                        append("Watchlist Movie List")
                                    }
                                },
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis                // dealing with overflow
                            )
                        }
                        Button(
                            onClick = {
                                if(accountState.listType != ListType.WATCHLIST_TV_LIST){
                                    scope.launch {
                                        onEvent(AccountEvent.UpdateListType(ListType.WATCHLIST_TV_LIST))
                                        cancel()
                                    }
                                } else{
                                    scope.launch {
                                        onEvent(AccountEvent.UpdateListType(ListType.NONE))
                                        cancel()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = watchlistTVContainerColor,
                                contentColor = watchlistTVContentColor
                            )
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                        append("Watchlist TV List")
                                    }
                                },
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis                // dealing with overflow
                            )
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        when(accountState.listType){

                            ListType.NONE -> {  }
                            ListType.FAVOURITE_MOVIE_LIST -> {
                                if (getFavouriteMovie != null) {
                                    if(getFavouriteMovie.itemCount != 0){
                                        items(
                                            items = getFavouriteMovie,
                                            key = { favouriteMovie ->
                                                favouriteMovie.id
                                            }
                                        ) { favouriteMovie ->
                                            favouriteMovie?.let { SearchItem(navController = navController, mediaType = "Movie", path = it.posterPath, title = it.originalTitle, releaseDateAndKnownForDept = it.releaseDate, onClickUpdateMovieId = {onClickUpdateMovieId(it.id)}) }
                                        }
                                    }
                                }
                            }
                            ListType.FAVOURITE_TV_LIST -> {
                                if (getFavouriteTV != null) {
                                    if(getFavouriteTV.itemCount != 0){
                                        items(
                                            items = getFavouriteTV,
                                            key = { favouriteTV ->
                                                favouriteTV.id
                                            }
                                        ) { favouriteTV ->
                                            favouriteTV?.let { SearchItem(navController = navController, mediaType = "TV", path = it.posterPath, title = it.originalName, releaseDateAndKnownForDept = it.firstAirDate, onClickUpdateMovieId = {onClickUpdateMovieId(it.id)}) }
                                        }
                                    }
                                }
                            }
                            ListType.WATCHLIST_MOVIE_LIST -> {
                                if (getWatchlistMovie != null) {
                                    if(getWatchlistMovie.itemCount != 0){
                                        items(
                                            items = getWatchlistMovie,
                                            key = { watchlistMovie ->
                                                watchlistMovie.id
                                            }
                                        ) { watchlistMovie ->
                                            watchlistMovie?.let { SearchItem(navController = navController, mediaType = "Movie", path = it.posterPath, title = it.originalTitle, releaseDateAndKnownForDept = it.releaseDate, onClickUpdateMovieId = {onClickUpdateMovieId(it.id)}) }
                                        }
                                    }
                                }
                            }
                            ListType.WATCHLIST_TV_LIST -> {
                                if (getWatchlistTV != null) {
                                    if(getWatchlistTV.itemCount != 0){
                                        items(
                                            items = getWatchlistTV,
                                            key = { watchlistTV ->
                                                watchlistTV.id
                                            }
                                        ) { watchlistTV ->
                                            watchlistTV?.let { SearchItem(navController = navController, mediaType = "TV", path = it.posterPath, title = it.originalName, releaseDateAndKnownForDept = it.firstAirDate, onClickUpdateMovieId = {onClickUpdateMovieId(it.id)}) }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }

}

@Preview("Light Mode")
@Preview("Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAccountScreen(){
    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }
    val activeContainerColor = Color.White
    val disabledContainerColor = color

    val activeContentColor = Color.Black
    val disabledContentColor = Color.White

    val favouriteMovieContainerColor: Color
    val favouriteMovieContentColor: Color

    val favouriteTVContainerColor: Color
    val favouriteTVContentColor: Color

    val watchlistMovieContainerColor: Color
    val watchlistMovieContentColor: Color

    val watchlistTVContainerColor: Color
    val watchlistTVContentColor: Color

    favouriteMovieContainerColor = disabledContainerColor
    favouriteMovieContentColor = disabledContentColor
    favouriteTVContainerColor = disabledContainerColor
    favouriteTVContentColor = disabledContentColor
    watchlistMovieContainerColor = disabledContainerColor
    watchlistMovieContentColor = disabledContentColor
    watchlistTVContainerColor = disabledContainerColor
    watchlistTVContentColor = disabledContentColor

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
                                append("Username")
                            }
                        },
//                            color = Color.White,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,                // dealing with overflow
                        color = Color.White
                    )
                    // Release Data
                    Text(
//                    buildAnnotatedString
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                append("Name")
                            }
                        },
//                            color = Color.White,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,                // dealing with overflow
                        color = Color.White
                    )
                }
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
//                            if(accountState.listType!= ListType.FAVOURITE_MOVIE_LIST){
//                                scope.launch {
//                                    onEvent(AccountEvent.UpdateListType(ListType.FAVOURITE_MOVIE_LIST))
//                                }
//                            } else{
//                                scope.launch {
//                                    onEvent(AccountEvent.UpdateListType(ListType.NONE))
//                                }
//                            }
                        },
                        shape = RoundedCornerShape(20.dp),
//                        border = BorderStroke(width = 1.dp,color = Color.White),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = favouriteMovieContainerColor,
                            contentColor = favouriteMovieContentColor
                        )
                    ) {
                        Text(
//                    buildAnnotatedString
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                    append("Favourite Movie List")
                                }
                            },
//                            color = Color.White,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis                // dealing with overflow
                        )
                    }
                    Button(
                        onClick = {
//                            if(accountState.listType != ListType.FAVOURITE_TV_LIST){
//                                scope.launch {
//                                    onEvent(AccountEvent.UpdateListType(ListType.FAVOURITE_TV_LIST))
//                                }
//                            } else{
//                                scope.launch {
//                                    onEvent(AccountEvent.UpdateListType(ListType.NONE))
//                                }
//                            }
                        },
                        shape = RoundedCornerShape(20.dp),
//                        border = BorderStroke(width = 1.dp,color = Color.White),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = favouriteTVContainerColor,
                            contentColor = favouriteTVContentColor
                        )
                    ) {
                        Text(
//                    buildAnnotatedString
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                    append("Favourite TV List")
                                }
                            },
//                            color = Color.White,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis                // dealing with overflow
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
//                            if(accountState.listType != ListType.WATCHLIST_MOVIE_LIST){
//                                scope.launch {
//                                    onEvent(AccountEvent.UpdateListType(ListType.WATCHLIST_MOVIE_LIST))
//                                }
//                            } else{
//                                scope.launch {
//                                    onEvent(AccountEvent.UpdateListType(ListType.NONE))
//                                }
//                            }
                        },
                        shape = RoundedCornerShape(20.dp),
//                        border = BorderStroke(width = 1.dp,color = Color.White),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = watchlistMovieContainerColor,
                            contentColor = watchlistMovieContentColor
                        )
                    ) {
                        Text(
//                    buildAnnotatedString
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                    append("Watchlist Movie List")
                                }
                            },
//                            color = Color.White,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis                // dealing with overflow
                        )
                    }
                    Button(
                        onClick = {
//                            if(accountState.listType != ListType.WATCHLIST_TV_LIST){
//                                scope.launch {
//                                    onEvent(AccountEvent.UpdateListType(ListType.WATCHLIST_TV_LIST))
//                                }
//                            } else{
//                                scope.launch {
//                                    onEvent(AccountEvent.UpdateListType(ListType.NONE))
//                                }
//                            }
                        },
                        shape = RoundedCornerShape(20.dp),
//                        border = BorderStroke(width = 1.dp,color = Color.White),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = watchlistTVContainerColor,
                            contentColor = watchlistTVContentColor
                        )
                    ) {
                        Text(
//                    buildAnnotatedString
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                    append("Watchlist TV List")
                                }
                            },
//                            color = Color.White,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis                // dealing with overflow
                        )
                    }
                }
            }
        }
    }
}