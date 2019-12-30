package com.mohsenoid.rickandmorty.view.episode.list

import android.os.Bundle
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.view.base.BaseActivity
import timber.log.Timber

class EpisodeListActivity : BaseActivity() {

    private lateinit var episodeListFragment: EpisodeListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        episodeListFragment =
            supportFragmentManager.findFragmentByTag(TAG_EPISODE_LIST_FRAGMENT) as? EpisodeListFragment
                ?: EpisodeListFragment.newInstance()

        if (savedInstanceState == null) {
            attachFragments()
        }
        Timber.i("EpisodeListActivity created")
    }

    private fun attachFragments() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, episodeListFragment, TAG_EPISODE_LIST_FRAGMENT)
        }.commit()
    }

    companion object {
        private const val TAG_EPISODE_LIST_FRAGMENT: String = "episodeListFragment"
    }
}
