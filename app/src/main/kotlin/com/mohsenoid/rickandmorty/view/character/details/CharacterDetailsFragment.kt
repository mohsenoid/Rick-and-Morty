package com.mohsenoid.rickandmorty.view.character.details

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.view.base.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_character_details.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class CharacterDetailsFragment : BaseFragment(), CharacterDetailsContract.View {

    @JvmField
    @field:[Inject Named(ARG_CHARACTER_ID)]
    var characterId: Int? = null

    @Inject
    lateinit var presenter: CharacterDetailsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        characterId.let { characterId ->
            if (characterId == null) {
                Toast.makeText(context, "Character id is missing!", Toast.LENGTH_SHORT).show()
                parentActivityOnBackPressed()
            } else {
                presenter.characterId = characterId
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter.bind(this)
    }

    override fun onDetach() {
        super.onDetach()
        presenter.unbind()
    }

    override fun onResume() {
        super.onResume()
        launch {
            presenter.loadCharacter()
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

    companion object {
        const val ARG_CHARACTER_ID: String = "character_id"

        fun newInstance(characterId: Int): CharacterDetailsFragment {
            val fragment = CharacterDetailsFragment()
            fragment.arguments = Bundle().apply {
                putInt(ARG_CHARACTER_ID, characterId)
            }
            return fragment
        }
    }
}
