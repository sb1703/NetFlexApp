package com.example.movietvshowapp.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import com.example.movietvshowapp.model.tv.OnTheAirTV

@ExperimentalCoilApi
@Composable
fun ListContentOnTheAirTV(
    navController: NavHostController,
    items: LazyPagingItems<OnTheAirTV>?,
    onClickUpdateMovieId: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(350.dp)
    ) {
        Text(
//                    buildAnnotatedString
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                    append("On The Air")
                }
            },
            color = Color.White,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,                // dealing with overflow
            modifier = Modifier.padding(start = 13.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(all = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (items != null) {
                if(items.itemCount!=0){
                    items(
                        items = items,
                        key = { onTheAirTV ->
                            onTheAirTV.id
                        }
                    ) { onTheAirTV ->
                        onTheAirTV?.let { TVContentItem(posterPath = it.posterPath, originalNameTitle = it.originalName, navController = navController, onClickUpdateMovieId = {onClickUpdateMovieId(it.id)}) }
                    }
                }
            }
        }
    }
}
