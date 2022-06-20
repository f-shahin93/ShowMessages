package com.shahin.showmessages.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import com.shahin.showmessages.R

@Composable
fun MessageTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = lightColors(
            primary = colorResource(id = R.color.primary_color),
            secondary = colorResource(id = R.color.secondary_color),
            background = colorResource(id = R.color.primary_background),
            surface = colorResource(id = R.color.primary_background)
        ),
        typography = Typography(defaultFontFamily = FontFamily(Font(R.font.iran_yekan))),
        content = content
    )
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun LoadImageByUrl(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    Card(
        modifier = modifier,
        content = {
            Image(
                painter = rememberImagePainter(
                    data = url,
                    builder = {
                        crossfade(true)
                        diskCachePolicy(CachePolicy.ENABLED)
                        memoryCachePolicy(CachePolicy.ENABLED)
                    }
                ),
                contentDescription = null,
                contentScale = contentScale,
            )
        },
        elevation = 0.dp,
        )

}