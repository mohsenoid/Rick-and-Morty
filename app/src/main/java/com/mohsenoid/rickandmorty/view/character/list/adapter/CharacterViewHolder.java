package com.mohsenoid.rickandmorty.view.character.list.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity;
import com.mohsenoid.rickandmorty.view.util.ImageDownloader;

import org.jetbrains.annotations.NotNull;

public class CharacterViewHolder extends RecyclerView.ViewHolder {

    public final View view;

    final ImageView characterStatus;

    private final ImageDownloader imageDownloader;
    private final ImageView characterImage;
    private final ProgressBar characterImageProgress;
    private final TextView characterName;

    CharacterViewHolder(View view, ImageDownloader imageDownloader) {
        super(view);
        this.view = view;
        this.imageDownloader = imageDownloader;

        characterImage = view.findViewById(R.id.character_image);
        characterImageProgress = view.findViewById(R.id.character_image_progress);
        characterName = view.findViewById(R.id.character_name);
        characterStatus = view.findViewById(R.id.character_status);
    }

    public void setCharacter(@NotNull CharacterEntity character) {
        imageDownloader.downloadImage(character.getImage(), characterImage, characterImageProgress);

        characterName.setText(character.getName());

        if (character.isAlive()) {
            characterStatus.setImageResource(R.drawable.ic_alive);
        } else {
            characterStatus.setImageResource(R.drawable.ic_dead);
        }
    }
}
