package com.mohsenoid.rickandmorty.ui.util

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.Companion.DefaultTransform
import coil.compose.AsyncImagePainter.State
import com.mohsenoid.rickandmorty.R

@Composable
fun AsyncImageWithPreview(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    transform: (State) -> State = DefaultTransform,
    onState: ((State) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
) {
    if (LocalInspectionMode.current) {
        // Show a placeholder in preview mode
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = modifier,
        )
    } else {
        // Use AsyncImage in the actual app
        AsyncImage(
            model = model,
            contentDescription = contentDescription,
            modifier = modifier,
            transform = transform,
            onState = onState,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
            filterQuality = filterQuality,
        )
    }
}
