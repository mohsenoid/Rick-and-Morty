package com.mohsenoid.rickandmorty.view.character.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterViewHolder internal constructor(
    internal val view: View
) : ViewHolder(view) {

    fun setCharacter(character: CharacterEntity) {
        Picasso.get()
            .load(character.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(view.characterImage)

        view.characterName.text = character.name

        if (character.isAlive) {
            view.characterStatus.setImageResource(R.drawable.ic_alive)
        } else {
            view.characterStatus.setImageResource(R.drawable.ic_dead)
        }
    }
}
