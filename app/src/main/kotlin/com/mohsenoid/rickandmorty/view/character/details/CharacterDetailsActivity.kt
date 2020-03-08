package com.mohsenoid.rickandmorty.view.character.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.view.base.BaseActivity
import timber.log.Timber

class CharacterDetailsActivity : BaseActivity() {

    private lateinit var characterDetailsFragment: CharacterDetailsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        val characterId: Int? = intent.extras?.getInt(ARG_CHARACTER_ID)
        if (characterId == null) {
            onBackPressed()
            return
        }

        characterDetailsFragment =
            supportFragmentManager.findFragmentByTag(TAG_CHARACTER_DETAILS_FRAGMENT) as? CharacterDetailsFragment
                ?: CharacterDetailsFragment.newInstance(characterId)

        if (savedInstanceState == null) {
            attachFragments()
        }
        Timber.i("CharacterDetailsActivity created")
    }

    private fun attachFragments() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, characterDetailsFragment, TAG_CHARACTER_DETAILS_FRAGMENT)
        }.commit()
    }

    companion object {
        private const val TAG_CHARACTER_DETAILS_FRAGMENT: String = "character_details_fragment"
        private const val ARG_CHARACTER_ID: String = "character_id"

        fun newIntent(context: Context?, characterId: Int): Intent {
            val intent = Intent(context, CharacterDetailsActivity::class.java)
            intent.putExtra(ARG_CHARACTER_ID, characterId)
            return intent
        }
    }
}
