package com.mohsenoid.rickandmorty.ui.character.list.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.model.CharacterModel;

import org.jetbrains.annotations.NotNull;

public class CharacterViewHolder extends RecyclerView.ViewHolder {

    public final View view;
    private CharacterModel character;

    private ImageView characterImage;
    private ProgressBar characterImageProgress;
    private TextView characterName;
    private ImageView characterDead;
    private ImageView characterAlive;

    CharacterViewHolder(View view) {
        super(view);
        this.view = view;

        characterImage = view.findViewById(R.id.character_image);
        characterImageProgress = view.findViewById(R.id.character_image_progress);
        characterName = view.findViewById(R.id.character_name);
        characterDead = view.findViewById(R.id.character_dead);
        characterAlive = view.findViewById(R.id.character_alive);
    }

    public CharacterModel getCharacter() {
        return character;
    }

    public void setCharacter(@NotNull CharacterModel character) {
        this.character = character;

        // TODO: characterImage.setImageBitmap();
        characterImageProgress.setVisibility(View.VISIBLE);

        characterName.setText(character.getName());

        if (character.isAlive()) {
            characterAlive.setVisibility(View.VISIBLE);
            characterDead.setVisibility(View.GONE);
        } else {
            characterAlive.setVisibility(View.GONE);
            characterDead.setVisibility(View.VISIBLE);
        }
    }
}
