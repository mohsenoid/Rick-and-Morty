package com.mohsenoid.rickandmorty.view.character.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import kotlinx.android.synthetic.main.item_character.view.*
import java.util.ArrayList

class CharacterListAdapter(
    private val dispatcherProvider: DispatcherProvider,
    private val listener: ClickListener
) : RecyclerView.Adapter<CharacterViewHolder>() {

    private var characters: MutableList<CharacterEntity> = ArrayList()

    fun setCharacters(characters: List<CharacterEntity>) {
        this.characters = characters.toMutableList()
    }

    fun updateCharacter(character: CharacterEntity) {
        val index = characters.indexOfFirst { it.id == character.id }
        characters[index] = character
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.onBind(dispatcherProvider)
        holder.setCharacter(character)
        holder.view.setOnClickListener { listener.onCharacterRowClick(character) }
        holder.view.characterStatus.setOnClickListener { listener.onCharacterStatusClick(character) }
    }

    override fun onViewRecycled(holder: CharacterViewHolder) {
        holder.onRecycled()
        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    interface ClickListener {

        fun onCharacterRowClick(character: CharacterEntity)

        fun onCharacterStatusClick(character: CharacterEntity)
    }
}
