package com.mohsenoid.rickandmorty.view.character.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.view.base.BaseActivity
import timber.log.Timber
import java.util.ArrayList

class CharacterListActivity : BaseActivity() {

    private lateinit var characterListFragment: CharacterListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        val characterIds: List<Int>? = intent.getIntegerArrayListExtra(ARG_CHARACTER_IDS)
        if (characterIds == null) {
            onBackPressed()
            return
        }

        characterListFragment =
            supportFragmentManager.findFragmentByTag(TAG_CHARACTER_LIST_FRAGMENT) as? CharacterListFragment
                ?: CharacterListFragment.newInstance(characterIds)

        if (savedInstanceState == null) {
            attachFragments()
        }
        Timber.i("CharacterListActivity created")
    }

    private fun attachFragments() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, characterListFragment, TAG_CHARACTER_LIST_FRAGMENT)
        }.commit()
    }

    companion object {
        private const val TAG_CHARACTER_LIST_FRAGMENT: String = "character_list_fragment"

        private const val ARG_CHARACTER_IDS: String = "character_ids"

        fun newIntent(context: Context?, characterIds: List<Int>): Intent {
            return Intent(context, CharacterListActivity::class.java).apply {
                putIntegerArrayListExtra(ARG_CHARACTER_IDS, ArrayList(characterIds))
            }
        }
    }
}
