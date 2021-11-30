package com.mohsenoid.rickandmorty.view.character.list.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mohsenoid.rickandmorty.databinding.ItemCharacterBinding
import com.mohsenoid.rickandmorty.view.model.ViewCharacterItem

class CharacterViewHolder internal constructor(
    internal val binding: ItemCharacterBinding
) : ViewHolder(binding.root) {

    fun setCharacter(character: ViewCharacterItem) {
        binding.character = character
    }
}
