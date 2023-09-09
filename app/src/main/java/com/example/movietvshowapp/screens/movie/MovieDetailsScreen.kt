package com.example.movietvshowapp.screens.movie

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import com.example.movietvshowapp.R
import com.example.movietvshowapp.model.movieDetails.MovieSimilar
import com.example.movietvshowapp.navigation.Screen
import com.example.movietvshowapp.screens.account.AccountState
import com.example.movietvshowapp.screens.account.AccountViewModel
import com.example.movietvshowapp.screens.common.MovieContentItem
import com.example.movietvshowapp.ui.theme.AppPrimaryColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.KSuspendFunction1

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagingApi::class)
@Composable
fun MovieDetailsScreen(
    navController: NavHostController,
    onClickUpdateMovieId: (Int) -> Unit,
    movieViewModel: MovieViewModel = hiltViewModel(),
    accountViewModel: AccountViewModel = hiltViewModel()
){
    val accountState by accountViewModel.state.collectAsState()
    val state by movieViewModel.state.collectAsState()
    val onEvent = movieViewModel::onEvent

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }
    LaunchedEffect(key1 = Unit){
        onEvent(MovieEvent.UpdateGetMovieDetails)
        onEvent(MovieEvent.UpdateGetMovieSimilar)
        onEvent(MovieEvent.SetIsAddFavouriteFalse)
        onEvent(MovieEvent.SetIsAddWatchlistFalse)
        if(!accountState.isGuestLoggedIn){
            onEvent(MovieEvent.UpdateIsAddFavouritesMovie(accountState = accountState))
            onEvent(MovieEvent.UpdateIsAddWatchlistMovie(accountState = accountState))
        }
        onEvent(MovieEvent.UpdateGetMovieReviews)
    }
    val getAllMovieSimilar = state.getMovieSimilar?.collectAsLazyPagingItems()

    val context = LocalContext.current

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
            Column(
                modifier = Modifier
                    .height(520.dp)
                    .fillMaxWidth()
                    .background(Color.Transparent)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ){
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w780${state.getMovieDetails?.backdropPath}",
                        contentDescription = "BackDrop Image",
                        imageLoader = ImageLoader.Builder(context).crossfade(true).build()
                    )
                    Column(modifier = Modifier
                        .padding(5.dp)
                    ) {
                        IconButton(
                            modifier = Modifier.padding(vertical = 10.dp),
                            onClick = {
                                scope.launch {
                                    withContext(Dispatchers.Main){
                                        navController.popBackStack()
                                    }
                                    onEvent(MovieEvent.UpdateBackStackMovieId)
                                    cancel()
                                }
                            }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .padding(vertical = 5.dp),
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back Stack Icon",
                                tint = Color.White
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${state.getMovieDetails?.posterPath}",
                        contentDescription = "PosterPath Image",
                        imageLoader = ImageLoader.Builder(context).crossfade(true).build()
                    )
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 10.dp, start = 15.dp),
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    state.getMovieDetails?.let { append(it.originalTitle) }
                                }
                            },
                            color = Color.White,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis                // dealing with overflow
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 15.dp),
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    state.getMovieDetails?.let { append(it.releaseDate) }
                                }
                            },
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis                // dealing with overflow
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    navController.navigate(Screen.MovieReviews.route)
                                }
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .padding(7.dp),
                                    imageVector = Icons.Filled.Reviews,
                                    contentDescription = "Reviews Icon",
                                    tint = Color.White
                                )
                            }
                            if(accountState.isGuestLoggedIn){
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Login to Add to Favourites")
                                            cancel()
                                        }
                                    }
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                            .background(color)
                                            .padding(7.dp),
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = "Favourites Icon",
                                        tint = Color.White
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Login to Add to Watchlist")
                                            cancel()
                                        }
                                    }
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                            .background(color)
                                            .padding(7.dp),
                                        imageVector = Icons.Filled.BookmarkAdd,
                                        contentDescription = "Watchlist Icon",
                                        tint = Color.White
                                    )
                                }
                            } else{
                                if(state.isAddedToFavourites){
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                onEvent(MovieEvent.AddFavouritesMovie(accountState = accountState))
                                                snackbarHostState.showSnackbar("Removed from Favourites")
                                                cancel()
                                            }
                                        }
                                    ) {
                                        Icon(
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clip(CircleShape)
                                                .background(color)
                                                .padding(7.dp),
                                            imageVector = Icons.Filled.HeartBroken,
                                            contentDescription = "Already Added To Favourites Icon",
                                            tint = Color.White
                                        )
                                    }
                                } else{
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                onEvent(MovieEvent.AddFavouritesMovie(accountState = accountState))
                                                snackbarHostState.showSnackbar("Added to Favourites")
                                                cancel()
                                            }
                                        }
                                    ) {
                                        Icon(
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clip(CircleShape)
                                                .background(color)
                                                .padding(7.dp),
                                            imageVector = Icons.Filled.Favorite,
                                            contentDescription = "Add To Favourites Icon",
                                            tint = Color.White
                                        )
                                    }
                                }
                                if(state.isAddedToWatchlist){
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                onEvent(MovieEvent.AddWatchlistMovie(accountState = accountState))
                                                snackbarHostState.showSnackbar("Removed from Watchlist")
                                                cancel()
                                            }
                                        }
                                    ) {
                                        Icon(
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clip(CircleShape)
                                                .background(color)
                                                .padding(7.dp),
                                            imageVector = Icons.Filled.BookmarkAdded,
                                            contentDescription = "Already Added To Watchlist Icon",
                                            tint = Color.White
                                        )
                                    }
                                } else{
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                onEvent(MovieEvent.AddWatchlistMovie(accountState = accountState))
                                                snackbarHostState.showSnackbar("Added to Watchlist")
                                                cancel()
                                            }
                                        }
                                    ) {
                                        Icon(
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clip(CircleShape)
                                                .background(color)
                                                .padding(7.dp),
                                            imageVector = Icons.Filled.BookmarkAdd,
                                            contentDescription = "Add To Watchlist Icon",
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Text(
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 10.dp)
                    .fillMaxWidth(),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        state.getMovieDetails?.let { append(it.overview) }
                    }
                },
                color = Color.White,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                overflow = TextOverflow.Visible                // dealing with overflow
            )
            Spacer(modifier = Modifier.height(5.dp))
            ListContentSimilarMovie(items = getAllMovieSimilar, navController = navController, onClickUpdateMovieId = {movieId:Int -> onClickUpdateMovieId(movieId)})
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ListContentSimilarMovie(
    navController: NavHostController,
    items: LazyPagingItems<MovieSimilar>?,
    onClickUpdateMovieId: (Int) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                    append("Similar")
                }
            },
            color = Color.White,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,                // dealing with overflow
            modifier = Modifier.padding(start = 13.dp)
        )
        if(items != null){
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(all = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = items,
                    key = { MovieSimilar ->
                        MovieSimilar.id
                    }
                ) { MovieSimilar ->
                    MovieSimilar?.let { MovieContentItem(posterPath = it.posterPath, originalNameTitle = it.originalTitle, navController = navController, onClickUpdateMovieId = {onClickUpdateMovieId(it.id)} ) }
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieDetailsScreenPreview(){
    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .height(520.dp)
                .fillMaxWidth()
                .background(Color.Transparent)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.bogkgrgdhrbyjslpxaxhxvstddv),
                    contentDescription = "BackDrop Image"
                )
                Column(modifier = Modifier
                    .padding(5.dp)
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
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    painter = painterResource(id = R.drawable._00),
                    contentDescription = "PosterPath Image"
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 10.dp, start = 15.dp),
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append("Title")
                            }
                        },
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis                // dealing with overflow
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 15.dp),
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append("2022-12-07")
                            }
                        },
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis                // dealing with overflow
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                    ) {
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .padding(7.dp),
                                imageVector = Icons.Filled.Reviews,
                                contentDescription = "Reviews Icon",
                                tint = Color.White
                            )
                        }
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .padding(7.dp),
                                imageVector = Icons.Filled.HeartBroken,
                                contentDescription = "Add To Favourites Icon",
                                tint = Color.White
                            )
                        }
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .padding(7.dp),
                                imageVector = Icons.Filled.BookmarkAdded,
                                contentDescription = "Add To Watchlist Icon",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
        Text(
            modifier = Modifier
                .padding(vertical = 15.dp, horizontal = 10.dp)
                .height(70.dp)
                .fillMaxWidth(),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                    append("Overview")
                }
            },
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            overflow = TextOverflow.Ellipsis                // dealing with overflow
        )
//        ListContentSimilarMovie()
    }
}