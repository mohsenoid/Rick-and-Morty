package com.mohsenoid.rickandmorty.domain.characters.model

import androidx.compose.runtime.Immutable

@Immutable
data class Character(
    val id: Int,
    val name: String,
    val isAlive: Boolean,
    val isKilled: Boolean,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val location: String,
    val image: String,
)
