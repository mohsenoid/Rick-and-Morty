package com.mohsenoid.rickandmorty.view.character.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import com.mohsenoid.rickandmorty.util.image.ImageDownloader
import kotlinx.android.synthetic.main.item_character.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CharacterViewHolder internal constructor(
    internal val view: View,
    private val imageDownloader: ImageDownloader
) : ViewHolder(view), CoroutineScope {

    private lateinit var job: Job
    override lateinit var coroutineContext: CoroutineContext

    fun onBind(dispatcherProvider: DispatcherProvider) {
        job = SupervisorJob()
        coroutineContext = dispatcherProvider.mainDispatcher + job
    }

    fun setCharacter(character: CharacterEntity) {
        launch {
            imageDownloader.downloadImage(
                imageUrl = character.imageUrl,
                imageView = view.characterImage,
                progress = view.characterImageProgress
            )
        }

        view.characterName.text = character.name

        if (character.isAlive) {
            view.characterStatus.setImageResource(R.drawable.ic_alive)
        } else {
            view.characterStatus.setImageResource(R.drawable.ic_dead)
        }
    }

    fun onRecycled() {
        job.cancel()
    }
}
