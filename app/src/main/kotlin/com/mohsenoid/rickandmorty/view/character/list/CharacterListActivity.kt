package com.mohsenoid.rickandmorty.view.character.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.injection.DependenciesProvider
import com.mohsenoid.rickandmorty.view.base.BaseActivity
import timber.log.Timber
import java.util.*

class CharacterListActivity : BaseActivity() {

    private lateinit var characterListFragment: CharacterListFragment

    public override fun injectDependencies(
        savedInstanceState: Bundle?,
        dependenciesProvider: DependenciesProvider
    ) {
        characterListFragment = if (savedInstanceState == null) {
            val characterIds: List<Int>? =
                intent.getIntegerArrayListExtra(ARG_CHARACTER_IDS)
            if (characterIds == null) {
                onBackPressed()
                return
            }
            dependenciesProvider.getCharacterListFragment(characterIds)
        } else {
            supportFragmentManager.findFragmentByTag(TAG_CHARACTER_LIST_FRAGMENT) as CharacterListFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            attachFragments()
        }
        Timber.i("CharacterListActivity created")
    }

    private fun attachFragments() {
        val fragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.container,
            characterListFragment,
            TAG_CHARACTER_LIST_FRAGMENT
        )
        fragmentTransaction.commit()
    }

    companion object {
        private const val TAG_CHARACTER_LIST_FRAGMENT = "character_list_fragment"

        private const val ARG_CHARACTER_IDS = "character_ids"

        fun newIntent(context: Context?, characterIds: List<Int>): Intent {
            return Intent(context, CharacterListActivity::class.java).apply {
                putIntegerArrayListExtra(ARG_CHARACTER_IDS, ArrayList(characterIds))
            }
        }
    }
}
