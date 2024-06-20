package com.mohsenoid.rickandmorty.view.model

data class ViewCharacterItem(
    val name: String,
    val imageUrl: String,
    val isAliveAndNotKilledByUser: Boolean,
    val onKill: () -> Unit,
    val onClick: () -> Unit,
) {
    fun onKill() = onKill.invoke()
    fun onClick() = onClick.invoke()
}
