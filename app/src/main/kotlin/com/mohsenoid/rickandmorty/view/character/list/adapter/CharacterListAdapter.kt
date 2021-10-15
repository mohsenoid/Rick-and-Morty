package com.mohsenoid.rickandmorty.view.character.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohsenoid.rickandmorty.databinding.ItemCharacterBinding
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import java.util.ArrayList

class CharacterListAdapter(
    private val listener: ClickListener
) : RecyclerView.Adapter<CharacterViewHolder>() {

    private var characters: MutableList<CharacterEntity> = ArrayList()

    fun setCharacters(characters: List<CharacterEntity>) {
        this.characters = characters.toMutableList()
    }

    fun updateCharacter(character: CharacterEntity) {
        val index: Int = characters.indexOfFirst { it.id == character.id }
        characters[index] = character
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character: CharacterEntity = characters[position]
        holder.setCharacter(character)
        holder.binding.root.setOnClickListener { listener.onCharacterRowClick(character) }
        holder.binding.characterStatus.setOnClickListener {
            listener.onCharacterStatusClick(
                character
            )
        }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    interface ClickListener {

        fun onCharacterRowClick(character: CharacterEntity)

        fun onCharacterStatusClick(character: CharacterEntity)
    }
}
