package com.mohsenoid.rickandmorty.view.character.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohsenoid.rickandmorty.databinding.ItemCharacterBinding
import com.mohsenoid.rickandmorty.view.model.ViewCharacterItem
import java.util.ArrayList

class CharacterListAdapter : RecyclerView.Adapter<CharacterViewHolder>() {

    private var characters: MutableList<ViewCharacterItem> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setCharacters(characters: List<ViewCharacterItem>) {
        this.characters = characters.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character: ViewCharacterItem = characters[position]
        holder.setCharacter(character)
    }

    override fun getItemCount(): Int {
        return characters.size
    }
}
