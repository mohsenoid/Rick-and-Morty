package com.mohsenoid.rickandmorty.view.character.list.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.databinding.ItemCharacterBinding
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.squareup.picasso.Picasso

class CharacterViewHolder internal constructor(
    internal val binding: ItemCharacterBinding
) : ViewHolder(binding.root) {

    fun setCharacter(character: CharacterEntity) {
        with(binding) {
            Picasso.get()
                .load(character.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(characterImage)

            characterName.text = character.name

            if (character.isAlive) {
                characterStatus.setImageResource(R.drawable.ic_alive)
            } else {
                characterStatus.setImageResource(R.drawable.ic_dead)
            }
        }
    }
}
