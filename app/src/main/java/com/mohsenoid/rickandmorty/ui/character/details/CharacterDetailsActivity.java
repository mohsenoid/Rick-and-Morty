package com.mohsenoid.rickandmorty.ui.character.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.injection.DependenciesProvider;
import com.mohsenoid.rickandmorty.ui.base.BaseActivity;

public class CharacterDetailsActivity extends BaseActivity {

    private static String TAG = CharacterDetailsActivity.class.getSimpleName();
    private static String TAG_CHARACTER_DETAILS_FRAGMENT = "character_details_fragment";

    private static String ARG_CHARACTER_ID = "character_id";

    private CharacterDetailsFragment characterDetailsFragment;

    public static Intent newIntent(Context context, int characterId) {
        Intent intent = new Intent(context, CharacterDetailsActivity.class);
        intent.putExtra(ARG_CHARACTER_ID, characterId);
        return intent;
    }

    @Override
    public void injectDependencies(@Nullable Bundle savedInstanceState, DependenciesProvider dependenciesProvider) {
        if (savedInstanceState == null) {
            int characterId = getIntent().getIntExtra(ARG_CHARACTER_ID, -1);
            if (characterId == -1) {
                onBackPressed();
                return;
            }

            characterDetailsFragment = dependenciesProvider.getCharacterDetailsFragment(characterId);
        } else {
            characterDetailsFragment = (CharacterDetailsFragment) getSupportFragmentManager().findFragmentByTag(TAG_CHARACTER_DETAILS_FRAGMENT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (null == savedInstanceState) {
            attachFragments();
        }

        Log.i(TAG, "CharacterDetailsActivity created");
    }

    private void attachFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, characterDetailsFragment, TAG_CHARACTER_DETAILS_FRAGMENT);
        fragmentTransaction.commit();
    }
}
