package com.example.movietvshowapp.screens.search

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import com.example.movietvshowapp.R
import com.example.movietvshowapp.navigation.Screen
import com.example.movietvshowapp.screens.search.SearchType.*
import com.example.movietvshowapp.ui.theme.AppPrimaryColor

@OptIn(ExperimentalCoilApi::class)
@ExperimentalMaterial3Api
@ExperimentalPagingApi
@Composable
fun SearchScreen(
    navController: NavHostController,
    onClickUpdateMovieId: (Int) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel()
){

    val state by searchViewModel.state.collectAsState()
    val onEvent = searchViewModel::onEvent

    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }

    val activeContainerColor = Color.White
    val disabledContainerColor = color

    val activeContentColor = Color.Black
    val disabledContentColor = Color.White

    val searchMovieContainerColor: Color
    val searchMovieContentColor: Color

    val searchTVContainerColor: Color
    val searchTVContentColor: Color

    val searchPersonContainerColor: Color
    val searchPersonContentColor: Color

    when(state.searchType){
        SEARCH_MOVIE -> {
            searchMovieContainerColor = activeContainerColor
            searchMovieContentColor = activeContentColor
            searchTVContainerColor = disabledContainerColor
            searchTVContentColor = disabledContentColor
            searchPersonContainerColor = disabledContainerColor
            searchPersonContentColor = disabledContentColor
        }
        SEARCH_TV -> {
            searchMovieContainerColor = disabledContainerColor
            searchMovieContentColor = disabledContentColor
            searchTVContainerColor = activeContainerColor
            searchTVContentColor = activeContentColor
            searchPersonContainerColor = disabledContainerColor
            searchPersonContentColor = disabledContentColor
        }
        SEARCH_PERSON -> {
            searchMovieContainerColor = disabledContainerColor
            searchMovieContentColor = disabledContentColor
            searchTVContainerColor = disabledContainerColor
            searchTVContentColor = disabledContentColor
            searchPersonContainerColor = activeContainerColor
            searchPersonContentColor = activeContentColor
        }
    }

    val getAllSearchMovieImages = state.getSearchMovie?.collectAsLazyPagingItems()
    val getAllSearchTVImages = state.getSearchTV?.collectAsLazyPagingItems()
    val getAllSearchPersonImages = state.getSearchPerson?.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            SearchTopBar(
                state = state,
                onBackStackClicked = {
                    navController.popBackStack()
                    onEvent(SearchEvent.UpdateSearchType(SEARCH_MOVIE))
                                     },
                onTextChange = {
                    onEvent(SearchEvent.UpdateSearchTextState(searchTextState = it))
                    when(state.searchType){
                        SEARCH_MOVIE -> {
                            onEvent(SearchEvent.UpdateGetSearchMovie)
                        }
                        SEARCH_TV -> {
                            onEvent(SearchEvent.UpdateGetSearchTV)
                        }
                        SEARCH_PERSON -> {
                            onEvent(SearchEvent.UpdateGetSearchPerson)
                        }
                    }
                               },
                onSearchClicked = {
                    when(state.searchType){
                        SEARCH_MOVIE -> {
                            onEvent(SearchEvent.UpdateGetSearchMovie)
                        }
                        SEARCH_TV -> {
                            onEvent(SearchEvent.UpdateGetSearchTV)
                        }
                        SEARCH_PERSON -> {
                            onEvent(SearchEvent.UpdateGetSearchPerson)
                        }
                    }
                }
            )
        },
        content = { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(color)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, bottom = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Button(
                        onClick = {
                            onEvent(SearchEvent.UpdateSearchType(SEARCH_MOVIE))
                                  },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = searchMovieContainerColor,
                            contentColor = searchMovieContentColor
                        )
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append("Movie")
                                }
                            },
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis                // dealing with overflow
                        )
                    }
                    Button(
                        onClick = {
                            onEvent(SearchEvent.UpdateSearchType(SEARCH_TV))
                                  },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = searchTVContainerColor,
                            contentColor = searchTVContentColor
                        )
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append("TV")
                                }
                            },
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis                // dealing with overflow
                        )
                    }
                    Button(
                        onClick = {
                            onEvent(SearchEvent.UpdateSearchType(SEARCH_PERSON))
                                  },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = searchPersonContainerColor,
                            contentColor = searchPersonContentColor
                        )
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append("Person")
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

                    when(state.searchType){
                        SEARCH_MOVIE -> {
                            if (getAllSearchMovieImages != null) {
                                if(getAllSearchMovieImages.itemCount != 0){
                                    items(
                                        items = getAllSearchMovieImages,
                                        key = { searchMovie ->
                                            searchMovie.id
                                        }
                                    ) { searchMovie ->
                                        searchMovie?.let { SearchItem(navController = navController, mediaType = "Movie", path = it.posterPath, title = it.originalTitle, releaseDateAndKnownForDept = it.releaseDate, onClickUpdateMovieId = {onClickUpdateMovieId(it.id)}) }
                                    }
                                }
                            }
                        }
                        SEARCH_TV -> {
                            if (getAllSearchTVImages != null) {
                                if(getAllSearchTVImages.itemCount != 0){
                                    items(
                                        items = getAllSearchTVImages,
                                        key = { searchTV ->
                                            searchTV.id
                                        }
                                    ) { searchTV ->
                                        searchTV?.let { SearchItem(navController = navController, mediaType = "TV",path = it.posterPath, title = it.originalName, releaseDateAndKnownForDept = it.firstAirDate, onClickUpdateMovieId = {onClickUpdateMovieId(it.id)}) }
                                    }
                                }
                            }
                        }
                        SEARCH_PERSON -> {
                            if (getAllSearchPersonImages != null) {
                                if(getAllSearchPersonImages.itemCount != 0){
                                    items(
                                        items = getAllSearchPersonImages,
                                        key = { searchPerson ->
                                            searchPerson.id
                                        }
                                    ) { searchPerson ->
                                        searchPerson?.let { SearchItem(navController = navController, mediaType = "Person",path = it.profilePath, title = it.originalName, releaseDateAndKnownForDept = it.knownForDepartment, onClickUpdateMovieId = {onClickUpdateMovieId(it.id)}) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@ExperimentalCoilApi
@Composable
fun SearchItem(
    navController: NavHostController,
    mediaType: String,
    path: String?,
    title: String,
    releaseDateAndKnownForDept: String,
    onClickUpdateMovieId: () -> Unit
){

    val context = LocalContext.current

    Box(
        modifier = Modifier.clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .clickable {
                onClickUpdateMovieId()
                if (mediaType == "Movie") {
                    navController.navigate(Screen.MovieDetails.route)
                } else if (mediaType == "TV") {
                    navController.navigate(Screen.TVDetails.route)
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp)
        ) {
            if(path!=null){
                AsyncImage(
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                        .height(350.dp)
                        .width(225.dp),
                    model = "https://image.tmdb.org/t/p/w500$path",
                    contentDescription = "Search Movie Image",
                    contentScale = ContentScale.Crop,
                    imageLoader = ImageLoader.Builder(context).crossfade(true).build()
                )
            } else{
                Surface(
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                        .height(350.dp)
                        .width(225.dp)
                ){}
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append(mediaType)
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis                // dealing with overflow
                )
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append(title)
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis                // dealing with overflow
                )
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append(releaseDateAndKnownForDept)
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis                // dealing with overflow
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode",uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSearchType(){

    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }

    val activeContainerColor = Color.White
    val disabledContainerColor = color

    val activeButtonContentColor = Color.Black
    val disabledButtonContentColor = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Button(
                onClick = {
//                    onEvent(SearchEvent.UpdateSearchType(SEARCH_MOVIE))
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = activeContainerColor,
                    contentColor = activeButtonContentColor
                )
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append("Movie")
                        }
                    },
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis                // dealing with overflow
                )
            }
            Button(
                onClick = {
//                    onEvent(SearchEvent.UpdateSearchType(SEARCH_TV))
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = disabledContainerColor,
                    contentColor = disabledButtonContentColor
                )
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append("TV")
                        }
                    },
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis                // dealing with overflow
                )
            }
            Button(
                onClick = {
//                    onEvent(SearchEvent.UpdateSearchType(SEARCH_PERSON))
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = disabledContainerColor,
                    contentColor = disabledButtonContentColor
                )
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append("Person")
                        }
                    },
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis                // dealing with overflow
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewItem(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .clickable {
////                browser intent
//                val browserIntent = Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("https://unsplash.com/@${unsplashImage.user.username}?utm_source=DemoApp&utm_medium=referral")
//                )
//                ContextCompat.startActivity(context, browserIntent, null)
//            }
            .height(300.dp)
//            .width(225.dp),
//        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
//            if(searchMovie.posterPath!=null){
                Image(
                    modifier = Modifier
                        .height(300.dp)
                        .width(225.dp),
                    painter = painterResource(id = R.drawable._00),
                    contentDescription = "Search Movie Image",
                    contentScale = ContentScale.Crop
                )
//            } else{
//                Surface(
//                    modifier = Modifier.height(300.dp).width(225.dp)
//                ){}
//            }
//            Surface(
//                modifier = Modifier
//                    .height(300.dp)
//                    .width(225.dp)
//            ){}
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
//                    buildAnnotatedString
                    modifier = Modifier.padding(15.dp),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append("Title Movie")
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis                // dealing with overflow
                )
            }
        }
    }
}