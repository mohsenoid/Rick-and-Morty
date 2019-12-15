package com.mohsenoid.rickandmorty.ui.character.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.injection.DependenciesProvider;
import com.mohsenoid.rickandmorty.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class CharacterListActivity extends BaseActivity {

    static String TAG = CharacterListActivity.class.getSimpleName();
    static String TAG_CHARACTER_LIST_FRAGMENT = "character_list_fragment";

    static String ARG_CHARACTER_IDS = "character_ids";

    private CharacterListFragment characterListFragment;

    public static Intent newIntent(Context context, List<Integer> characterIds) {
        Intent intent = new Intent(context, CharacterListActivity.class);
        intent.putIntegerArrayListExtra(ARG_CHARACTER_IDS, new ArrayList<>(characterIds));
        return intent;
    }

    @Override
    public void injectDependencies(@Nullable Bundle savedInstanceState, DependenciesProvider dependenciesProvider) {
        if (savedInstanceState == null) {
            List<Integer> characterIds = getIntent().getIntegerArrayListExtra(ARG_CHARACTER_IDS);
            if (characterIds == null) {
                onBackPressed();
                return;
            }

            characterListFragment = dependenciesProvider.getCharacterListFragment(characterIds);
        } else {
            characterListFragment = (CharacterListFragment) getSupportFragmentManager().findFragmentByTag(TAG_CHARACTER_LIST_FRAGMENT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (null == savedInstanceState) {
            attachFragments();
        }

        Log.i(TAG, "CharacterListActivity created");
    }

    private void attachFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, characterListFragment, TAG_CHARACTER_LIST_FRAGMENT);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
