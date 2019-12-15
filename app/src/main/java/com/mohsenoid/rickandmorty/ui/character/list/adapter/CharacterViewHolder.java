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

    ImageView characterStatus;

    private ImageDownloader imageDownloader;
    private CharacterModel character;
    private ImageView characterImage;
    private ProgressBar characterImageProgress;
    private TextView characterName;

    CharacterViewHolder(View view, ImageDownloader imageDownloader) {
        super(view);
        this.view = view;
        this.imageDownloader = imageDownloader;

        characterImage = view.findViewById(R.id.character_image);
        characterImageProgress = view.findViewById(R.id.character_image_progress);
        characterName = view.findViewById(R.id.character_name);
        characterStatus = view.findViewById(R.id.character_status);
    }

    public CharacterModel getCharacter() {
        return character;
    }

    public void setCharacter(@NotNull CharacterModel character) {
        this.character = character;

        imageDownloader.downloadImage(character.getImage(), characterImage, characterImageProgress);

        characterName.setText(character.getName());

        if (character.isAlive()) {
            characterStatus.setImageResource(R.drawable.ic_alive);
        } else {
            characterStatus.setImageResource(R.drawable.ic_dead);
        }
    }
}
