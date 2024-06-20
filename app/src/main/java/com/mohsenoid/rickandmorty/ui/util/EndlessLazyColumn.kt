package com.mohsenoid.rickandmorty.ui.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun <T> EndlessLazyColumn(
    modifier: Modifier = Modifier,
    isNoConnectionError: Boolean = false,
    isLoading: Boolean = false,
    isEndOfList: Boolean = false,
    listState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    items: List<T>,
    itemKey: (T) -> Any,
    loadMore: () -> Unit,
    noConnectionItem: @Composable () -> Unit = {},
    loadingItem: @Composable () -> Unit = {},
    itemContent: @Composable (T) -> Unit = {},
) {
    val reachedBottom: Boolean by remember { derivedStateOf { listState.reachedBottom() } }

    // load more if scrolled to bottom
    LaunchedEffect(reachedBottom) {
        if (reachedBottom && !isLoading && !isEndOfList) {
            loadMore()
        }
    }

    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
            state = listState,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
        ) {
            items(
                items = items,
                key = { item: T -> itemKey(item) },
            ) { item ->
                itemContent(item)
            }

            item {
                if (!isEndOfList) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (isNoConnectionError) {
                            noConnectionItem()
                        } else if (isLoading) {
                            loadingItem()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EndlessLazyColumnPreview() {
    EndlessLazyColumn(
        modifier = Modifier.fillMaxSize(),
        isNoConnectionError = false,
        isLoading = true,
        isEndOfList = false,
        items = listOf("1", "2", "3", "4", "5"),
        itemKey = { it },
        noConnectionItem = {},
        loadMore = {},
        loadingItem = { CircularProgressIndicator() },
    ) {
        Text(
            text = it,
            textAlign = TextAlign.Center,
        )
    }
}

private fun LazyListState.reachedBottom(): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - 1
}
