package com.mohsenoid.rickandmorty.view.character.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.injection.DependenciesProvider
import com.mohsenoid.rickandmorty.view.base.BaseActivity
import timber.log.Timber

class CharacterDetailsActivity : BaseActivity() {

    private lateinit var characterDetailsFragment: CharacterDetailsFragment

    public override fun injectDependencies(
        savedInstanceState: Bundle?,
        dependenciesProvider: DependenciesProvider
    ) {
        characterDetailsFragment = if (savedInstanceState == null) {
            val characterId =
                intent.getIntExtra(ARG_CHARACTER_ID, -1)
            if (characterId == -1) {
                onBackPressed()
                return
            }
            dependenciesProvider.getCharacterDetailsFragment(characterId)
        } else {
            supportFragmentManager.findFragmentByTag(TAG_CHARACTER_DETAILS_FRAGMENT) as CharacterDetailsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            attachFragments()
        }
        Timber.i("CharacterDetailsActivity created")
    }

    private fun attachFragments() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, characterDetailsFragment, TAG_CHARACTER_DETAILS_FRAGMENT)
            commit()
        }
    }

    companion object {
        private const val TAG_CHARACTER_DETAILS_FRAGMENT = "character_details_fragment"
        private const val ARG_CHARACTER_ID = "character_id"

        fun newIntent(context: Context?, characterId: Int): Intent {
            val intent = Intent(context, CharacterDetailsActivity::class.java)
            intent.putExtra(ARG_CHARACTER_ID, characterId)
            return intent
        }
    }
}