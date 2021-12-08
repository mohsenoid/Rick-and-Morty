package com.mohsenoid.rickandmorty.view.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.view.character.list.adapter.CharacterListAdapter
import com.mohsenoid.rickandmorty.view.model.LoadingState
import com.mohsenoid.rickandmorty.view.model.ViewCharacterItem
import com.squareup.picasso.Picasso

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("onRefreshListener")
fun SwipeRefreshLayout.onRefreshListener(listener: SwipeRefreshLayout.OnRefreshListener?) {
    setOnRefreshListener(listener)
}

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.setIsRefreshing(loadingState: LoadingState?) {
    isRefreshing = loadingState == LoadingState.Loading
}

@BindingAdapter("isLoading")
fun ProgressBar.setIsLoading(loadingState: LoadingState?) {
    visibility = if (loadingState == LoadingState.Loading) View.VISIBLE else View.GONE
}

@BindingAdapter("isLoadingMore")
fun ProgressBar.setIsLoadingMore(loadingState: LoadingState?) {
    visibility = if (loadingState == LoadingState.LoadingMore) View.VISIBLE else View.GONE
}

@BindingAdapter("characters")
fun RecyclerView.setCharacters(itemViewModels: List<ViewCharacterItem>?) {
    val adapter = getOrCreateCharacterListAdapter()
    adapter.setCharacters(itemViewModels ?: emptyList())
}

private fun RecyclerView.getOrCreateCharacterListAdapter(): CharacterListAdapter {
    return if (adapter != null && adapter is CharacterListAdapter) {
        adapter as CharacterListAdapter
    } else {
        val newAdapter = CharacterListAdapter()
        adapter = newAdapter
        newAdapter
    }
}

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .placeholder(R.drawable.ic_placeholder)
        .into(this)
}
