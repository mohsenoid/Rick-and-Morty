package com.mohsenoid.rickandmorty.ui.character.list.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.ui.util.ImageDownloader;

import org.jetbrains.annotations.NotNull;

public class CharacterViewHolder extends RecyclerView.ViewHolder {

    public final View view;

    private ImageDownloader imageDownloader;

    private CharacterModel character;

    private ImageView characterImage;
    private ProgressBar characterImageProgress;
    private TextView characterName;
    private ImageView characterDead;
    private ImageView characterAlive;

    CharacterViewHolder(View view, ImageDownloader imageDownloader) {
        super(view);
        this.view = view;
        this.imageDownloader = imageDownloader;

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

        imageDownloader.downloadImage(character.getImage(), characterImage, characterImageProgress);

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
