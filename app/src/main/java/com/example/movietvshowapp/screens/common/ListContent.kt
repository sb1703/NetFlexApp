package com.example.movietvshowapp.screens.common

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import com.example.movietvshowapp.R
import com.example.movietvshowapp.navigation.Screen
import com.example.movietvshowapp.ui.theme.AppPrimaryColor

@ExperimentalCoilApi
@Composable
fun MovieContentItem(
    navController: NavHostController,
    posterPath: String?,
    originalNameTitle: String,
    onClickUpdateMovieId: () -> Unit
) {

    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .clickable {
                onClickUpdateMovieId()
                navController.navigate(Screen.MovieDetails.route)
            }
            .height(300.dp)
            .width(225.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        if(posterPath != null){
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = "https://image.tmdb.org/t/p/w500$posterPath",
                contentDescription = "Movie Image",
                contentScale = ContentScale.Crop,
                imageLoader = ImageLoader.Builder(context).crossfade(true).build()
            )
        } else{
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
            ) {}
        }
        Surface(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .alpha(alpha = 0.38f),
            color = Color.Black
        ) {}
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                        append(originalNameTitle)
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

@ExperimentalCoilApi
@Composable
fun TVContentItem(
    navController: NavHostController,
    posterPath: String?,
    originalNameTitle: String,
    onClickUpdateMovieId: () -> Unit
) {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .clickable {
                onClickUpdateMovieId()
                navController.navigate(Screen.TVDetails.route)
            }
            .height(300.dp)
            .width(225.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        if(posterPath!=null){
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = "https://image.tmdb.org/t/p/w500$posterPath",
                contentDescription = "TV Image",
                imageLoader = ImageLoader.Builder(context).crossfade(true).build(),
                contentScale = ContentScale.Crop
            )
        } else{
            Surface(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .alpha(alpha = 0.38f),
                color = Color.Black
            ) {}
        }
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                        append(originalNameTitle)
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


@ExperimentalCoilApi
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {

    Box(
        modifier = Modifier
            .clickable {
//                onClickUpdateMovieId()
//                navController.navigate(Screen.MovieDetails.route)
            }
            .height(300.dp)
            .width(225.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable._00),
            contentDescription = "Movie Image",
            contentScale = ContentScale.Crop
        )
//        if(posterPath != null){
//            AsyncImage(
//                modifier = Modifier.fillMaxSize(),
//                model = "https://image.tmdb.org/t/p/w185$posterPath",
//                contentDescription = "Movie Image",
//                contentScale = ContentScale.Crop,
//                imageLoader = ImageLoader.Builder(context).crossfade(true).build()
//            )
//        } else{
//            Surface(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(color)
//            ) {}
//        }
        Surface(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .alpha(alpha = 0.38f),
            color = Color.Black
        ) {}
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
//                    buildAnnotatedString
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                        append("Title")
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