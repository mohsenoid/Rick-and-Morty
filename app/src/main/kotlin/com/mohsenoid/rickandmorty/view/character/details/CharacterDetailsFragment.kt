package com.mohsenoid.rickandmorty.view.character.details

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.navArgs
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.view.base.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_character_details.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class CharacterDetailsFragment : BaseFragment(), CharacterDetailsContract.View {

    private val args: CharacterDetailsFragmentArgs by navArgs()

    private val presenter: CharacterDetailsContract.Presenter by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(characterDetailsFragmentModule)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bind(this)
    }

    override fun onResume() {
        super.onResume()
        launch {
            presenter.loadCharacter(args.characterId)
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showOfflineMessage(isCritical: Boolean) {
        Toast.makeText(context, R.string.offline_app, Toast.LENGTH_LONG).show()
        if (isCritical) {
            val activity: FragmentActivity? = activity
            if (activity != null && !activity.isFinishing) activity.finish()
        }
    }

    override fun showLoading() {
        characterDetailsProgress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        characterDetailsProgress.visibility = View.GONE
    }

    override fun onNoOfflineData() {
        Toast.makeText(context, R.string.no_offline_data, Toast.LENGTH_LONG).show()
        parentActivityOnBackPressed()
    }

    private fun parentActivityOnBackPressed() {
        val parentActivity: Activity? = activity
        parentActivity?.onBackPressed()
    }

    override fun setCharacter(character: CharacterEntity) {
        Picasso.get()
            .load(character.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(characterImage)

        characterName.text = character.name
        characterDetails.text =
            getString(R.string.character_details_format, character.id, character.created)
        if (character.killedByUser) {
            characterKilledByUser.visibility = View.VISIBLE
        } else {
            characterKilledByUser.visibility = View.GONE
        }
        characterStatus.text = character.status
        characterSpecies.text = character.species
        characterGender.text = character.gender
        characterOrigin.text = character.origin.name
        characterLastLocation.text = character.location.name
    }

    override fun onDestroyView() {
        presenter.unbind()
        super.onDestroyView()
    }

    override fun onDestroy() {
        unloadKoinModules(characterDetailsFragmentModule)
        super.onDestroy()
    }
}
