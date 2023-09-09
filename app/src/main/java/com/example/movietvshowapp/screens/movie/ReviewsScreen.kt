package com.example.movietvshowapp.screens.movie

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.movietvshowapp.R
import com.example.movietvshowapp.model.movieDetails.MovieReviews
import com.example.movietvshowapp.model.tvDetails.TVReviews
import com.example.movietvshowapp.ui.theme.AppPrimaryColor

@OptIn(ExperimentalPagingApi::class)
@Composable
fun MovieReviewsScreen(
    navController: NavHostController,
    movieViewModel: MovieViewModel = hiltViewModel()
){
    val state by movieViewModel.state.collectAsState()
    val getAllMovieReviews = state.getMovieReviews?.collectAsLazyPagingItems()
    ListMovieReviews(navController = navController, items = getAllMovieReviews)
}

@OptIn(ExperimentalPagingApi::class)
@Composable
fun TVReviewsScreen(
    navController: NavHostController,
    movieViewModel: MovieViewModel = hiltViewModel()
){
    val state by movieViewModel.state.collectAsState()
    val getAllTVReviews = state.getTVReviews?.collectAsLazyPagingItems()
    ListTVReviews(navController = navController, items = getAllTVReviews)
}

@Composable
fun ListMovieReviews(
    navController: NavHostController,
    items: LazyPagingItems<MovieReviews>?
){
    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier.padding(vertical = 10.dp),
                onClick = {
                    navController.popBackStack()
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
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                        append("Reviews")
                    }
                },
                color = Color.White,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,                // dealing with overflow
                modifier = Modifier.padding(start = 13.dp)
            )
        }
        if(items != null){
            if(items.itemCount!=0){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color),
                    contentPadding = PaddingValues(all = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = items,
                        key = { MovieReview ->
                            MovieReview.id
                        }
                    ) { MovieReview ->
                        MovieReview?.let { ReviewBox(
                            name = it.author,
                            userName = it.authorDetails.username,
                            avatarPath = it.authorDetails.avatarPath,
                            rating = it.authorDetails.rating,
                            content = it.content,
                            releaseDate = it.releaseDate
                        ) }
                    }
                }
            } else{
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append("No Reviews Yet")
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
}

@Composable
fun ListTVReviews(
    navController: NavHostController,
    items: LazyPagingItems<TVReviews>?
){
    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier.padding(vertical = 10.dp),
                onClick = {
                    navController.popBackStack()
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
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                        append("Reviews")
                    }
                },
                color = Color.White,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,                // dealing with overflow
                modifier = Modifier.padding(start = 13.dp)
            )
        }
        if(items != null){
            if(items.itemCount!=0){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color),
                    contentPadding = PaddingValues(all = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = items,
                        key = { MovieReview ->
                            MovieReview.id
                        }
                    ) { MovieReview ->
                        MovieReview?.let { ReviewBox(
                            name = it.author,
                            userName = it.authorDetails.username,
                            avatarPath = it.authorDetails.avatarPath,
                            rating = it.authorDetails.rating,
                            content = it.content,
                            releaseDate = it.releaseDate
                        ) }
                    }
                }
            } else{
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append("No Reviews Yet")
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
}

@Composable
fun ReviewBox(
    name: String,
    userName: String,
    avatarPath: String?,
    rating: Float?,
    content: String,
    releaseDate: String
){
    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .border(border = BorderStroke(1.dp, Color.White), shape = RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val context = LocalContext.current

            if(avatarPath!=null && avatarPath.toString().startsWith("/https://")){
                Image(
                    modifier = Modifier
                        .padding(20.dp)
                        .clip(CircleShape)
                        .width(80.dp),
                    painter = painterResource(id = R.drawable.pngtreeoval_user_avatar_placeholder_black_6796229),
                    contentDescription = "Avatar Image",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            } else if(avatarPath==null){
                Image(
                    modifier = Modifier
                        .padding(20.dp)
                        .clip(CircleShape)
                        .width(80.dp),
                    painter = painterResource(id = R.drawable.pngtreeoval_user_avatar_placeholder_black_6796229),
                    contentDescription = "Avatar Image",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            } else{
                AsyncImage(
                    modifier = Modifier
                        .padding(20.dp)
                        .clip(CircleShape),
                    model = "https://image.tmdb.org/t/p/w185${avatarPath}",
                    contentDescription = "Avatar Image",
                    imageLoader = ImageLoader.Builder(context).crossfade(true).build()
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append(name)
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,                // dealing with overflow
                    modifier = Modifier.padding(start = 13.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                            append(userName)
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,                // dealing with overflow
                    modifier = Modifier.padding(start = 13.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .padding(10.dp)
        ){
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                            append(content)
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    overflow = TextOverflow.Ellipsis,                // dealing with overflow
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                        .fillMaxWidth(),
                    maxLines = 25
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        var ratingString = ""
                        if(rating!=null){
                            ratingString = rating.toString()
                        }
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append(ratingString)
                                }
                            },
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            overflow = TextOverflow.Ellipsis,                // dealing with overflow
                            modifier = Modifier
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append(releaseDate.removeRange(10,releaseDate.length))
                                }
                            },
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            overflow = TextOverflow.Ellipsis,                // dealing with overflow
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewReviewsScreen(){
    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(border = BorderStroke(1.dp, Color.White), shape = RoundedCornerShape(10.dp))
            .background(color)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
//            Image(
//                modifier = Modifier
//                    .padding(10.dp)
//                    .clip(CircleShape)
//                    .width(80.dp),
//                painter = painterResource(id = R.drawable.avatar145),
//                contentDescription = "Avatar Image"
//            )
            Image(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(CircleShape)
                    .width(80.dp),
                painter = painterResource(id = R.drawable.pngtreeoval_user_avatar_placeholder_black_6796229),
                contentDescription = "Avatar Image",
                colorFilter = ColorFilter.tint(Color.White)
            )
            Column(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
//                    buildAnnotatedString
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append("Name")
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,                // dealing with overflow
                    modifier = Modifier.padding(start = 13.dp)
                )
                Text(
//                    buildAnnotatedString
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                            append("UserName")
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,                // dealing with overflow
                    modifier = Modifier.padding(start = 13.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .padding(10.dp)
        ){
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
//                    buildAnnotatedString
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                            append("Futbol Club Barcelona (Catalan pronunciation: [fubˈbɔl ˈklub bəɾsəˈlonə] (listen)), commonly referred to as Barcelona and colloquially known as Barça ([ˈbaɾsə]), is a professional football club based in Barcelona, Catalonia, Spain, that competes in La Liga, the top flight of Spanish football.\n" +
                                    "\n" +
                                    "Founded in 1899 by a group of Swiss, Catalan, German, and English footballers led by Joan Gamper, the club has become a symbol of Catalan culture and Catalanism, hence the motto \"Més que un club\" (\"More than a club\"). Unlike many other football clubs, the supporters own and operate Barcelona. It is the third-most valuable sports team in the world, worth \$5.51 billion, and the world's fourth richest football club in terms of revenue, with an annual turnover of €582.1 million.[2][3] The official Barcelona anthem is the \"Cant del Barça\""
                            )
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    overflow = TextOverflow.Ellipsis,                // dealing with overflow
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                        .fillMaxWidth(),
                    maxLines = 25
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
//                    buildAnnotatedString
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append("Rating")
                                }
                            },
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            overflow = TextOverflow.Ellipsis,                // dealing with overflow
                            modifier = Modifier
                        )
                        Text(
//                    buildAnnotatedString
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append("2018-05-078399384".removeRange(10,17))
//                                    append(releaseDate.removeRange(10,releaseDate.length))
                                }
                            },
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            overflow = TextOverflow.Ellipsis,                // dealing with overflow
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}