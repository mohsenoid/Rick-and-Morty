package com.mohsenoid.rickandmorty.ui.character.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.model.CharacterModel;

import java.util.ArrayList;
import java.util.List;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterViewHolder> {

    private List<CharacterModel> characters = new ArrayList<>();
    private ClickListener listener;

    public CharacterListAdapter(ClickListener listener) {
        this.listener = listener;
    }

    public void setCharacters(List<CharacterModel> characters) {
        this.characters = characters;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        final CharacterModel character = characters.get(position);

        holder.setCharacter(character);

        holder.view.setOnClickListener(v -> {
            if (listener != null) listener.onClick(character);
        });
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public interface ClickListener {
        void onClick(CharacterModel character);
    }
}
