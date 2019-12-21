package com.mohsenoid.rickandmorty.view.character.details;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity;
import com.mohsenoid.rickandmorty.injection.DependenciesProvider;
import com.mohsenoid.rickandmorty.view.base.BaseFragment;
import com.mohsenoid.rickandmorty.view.util.ImageDownloader;

public class CharacterDetailsFragment extends BaseFragment implements CharacterDetailsContract.View {

    private static final String ARG_CHARACTER_ID = "character_id";

    private CharacterDetailsContract.Presenter presenter;
    private ImageDownloader imageDownloader;
    private ProgressBar progress;
    private ImageView characterImage;
    private ProgressBar characterImageProgress;
    private TextView characterName;
    private TextView characterDetails;
    private TextView characterStatus;
    private TextView characterKilledByUser;
    private TextView characterSpecies;
    private TextView characterGender;
    private TextView characterOrigin;
    private TextView characterLastLocation;

    public static CharacterDetailsFragment newInstance(int characterId) {
        CharacterDetailsFragment fragment = new CharacterDetailsFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_CHARACTER_ID, characterId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void injectDependencies(DependenciesProvider dependenciesProvider) {
        presenter = dependenciesProvider.getCharacterDetailsFragmentPresenter();
        imageDownloader = dependenciesProvider.getImageDownloader();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int characterId = extractCharacterId();
        if (characterId == -1) {
            Toast.makeText(getContext(), "Character id is missing!", Toast.LENGTH_SHORT).show();
            parentActivityOnBackPressed();
        } else {
            presenter.setCharacterId(characterId);
        }
    }

    private int extractCharacterId() {
        Bundle args = getArguments();
        if (args == null) return -1;

        return args.getInt(ARG_CHARACTER_ID, -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_details, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        progress = view.findViewById(R.id.character_details_progress);
        characterImage = view.findViewById(R.id.character_image);
        characterImageProgress = view.findViewById(R.id.character_image_progress);
        characterName = view.findViewById(R.id.character_name);
        characterDetails = view.findViewById(R.id.character_details);
        characterStatus = view.findViewById(R.id.character_status);
        characterKilledByUser = view.findViewById(R.id.character_killed_by_user);
        characterSpecies = view.findViewById(R.id.character_species);
        characterGender = view.findViewById(R.id.character_gender);
        characterOrigin = view.findViewById(R.id.character_origin);
        characterLastLocation = view.findViewById(R.id.character_last_location);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter.bind(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.loadCharacter();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showOfflineMessage(boolean isCritical) {
        Toast.makeText(getContext(), R.string.offline_app, Toast.LENGTH_LONG).show();

        if (isCritical) {
            FragmentActivity activity = getActivity();
            if (activity != null && !activity.isFinishing()) activity.finish();
        }
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onNoOfflineData() {
        Toast.makeText(getContext(), R.string.no_offline_data, Toast.LENGTH_LONG).show();
        parentActivityOnBackPressed();
    }

    private void parentActivityOnBackPressed() {
        Activity parentActivity = getActivity();
        if (parentActivity != null) parentActivity.onBackPressed();
    }

    @Override
    public void setCharacter(CharacterEntity character) {
        imageDownloader.downloadImage(character.getImage(), characterImage, characterImageProgress);

        characterName.setText(character.getName());
        characterDetails.setText(getString(R.string.character_details_format, character.getId(), character.getCreated()));

        if (character.getKilledByUser()) {
            characterKilledByUser.setVisibility(View.VISIBLE);
        } else {
            characterKilledByUser.setVisibility(View.GONE);
        }

        characterStatus.setText(character.getStatus());
        characterSpecies.setText(character.getSpecies());
        characterGender.setText(character.getGender());
        characterOrigin.setText(character.getOrigin().getName());
        characterLastLocation.setText(character.getLocation().getName());
    }
}
