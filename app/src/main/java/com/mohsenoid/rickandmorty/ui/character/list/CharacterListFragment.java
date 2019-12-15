package com.mohsenoid.rickandmorty.ui.character.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.injection.DependenciesProvider;
import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.ui.base.BaseFragment;
import com.mohsenoid.rickandmorty.ui.character.details.CharacterDetailsActivity;
import com.mohsenoid.rickandmorty.ui.character.list.adapter.CharacterListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CharacterListFragment extends BaseFragment implements CharacterListContract.View, CharacterListAdapter.ClickListener {

    private static final String ARG_CHARACTER_IDS = "character_ids";

    private List<Integer> characterIds;

    private CharacterListContract.Presenter presenter;
    private ProgressBar progress;
    private CharacterListAdapter adapter;

    public static CharacterListFragment newInstance(List<Integer> characterIds) {
        CharacterListFragment fragment = new CharacterListFragment();

        Bundle args = new Bundle();
        args.putIntegerArrayList(ARG_CHARACTER_IDS, new ArrayList<>(characterIds));
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void injectDependencies(DependenciesProvider dependenciesProvider) {
        presenter = dependenciesProvider.getCharacterListFragmentPresenter();
        adapter = dependenciesProvider.getCharacterListAdapter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!extractCharacterIds()) {
            Toast.makeText(getContext(), "Character ids are missing!", Toast.LENGTH_SHORT).show();
            parentActivityOnBackPressed();
        }
    }

    private boolean extractCharacterIds() {
        Bundle args = getArguments();
        if (args == null) return false;

        characterIds = args.getIntegerArrayList(ARG_CHARACTER_IDS);
        return characterIds != null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        progress = view.findViewById(R.id.character_list_progress);

        RecyclerView characterList = view.findViewById(R.id.character_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        characterList.setLayoutManager(linearLayoutManager);
        characterList.setAdapter(adapter);
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

        presenter.loadCharacters(characterIds);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showOfflineMessage(boolean isCritical) {
        Toast.makeText(getContext(), R.string.offline_app, Toast.LENGTH_LONG).show();
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

    @Override
    public void onCharactersQueryResult(List<Integer> characterIds, List<CharacterModel> characters) {
        adapter.setCharacters(characters);
        adapter.notifyDataSetChanged();
    }

    private void parentActivityOnBackPressed() {
        Activity parentActivity = getActivity();
        if (parentActivity != null) parentActivity.onBackPressed();
    }

    @Override
    public void onClick(CharacterModel character) {
        Intent characterDetailsIntent = CharacterDetailsActivity.newIntent(getContext(), character.getId());
        startActivity(characterDetailsIntent);
    }
}
