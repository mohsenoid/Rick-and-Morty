package com.mohsenoid.rickandmorty.view.character.details

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.navArgs
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.mohsenoid.rickandmorty.domain.model.ModelCharacter
import com.mohsenoid.rickandmorty.view.util.launchWhileResumed
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@Suppress("TooManyFunctions")
class CharacterDetailsFragment : Fragment(), CharacterDetailsContract.View {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

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
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bind(this)
    }

    override fun onResume() {
        super.onResume()
        lifecycle.launchWhileResumed {
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
        binding.characterDetailsProgress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.characterDetailsProgress.visibility = View.GONE
    }

    override fun onNoOfflineData() {
        Toast.makeText(context, R.string.no_offline_data, Toast.LENGTH_LONG).show()
        parentActivityOnBackPressed()
    }

    private fun parentActivityOnBackPressed() {
        val parentActivity: Activity? = activity
        parentActivity?.onBackPressed()
    }

    override fun setCharacter(character: ModelCharacter) {
        with(binding) {
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbind()
        _binding = null
    }

    override fun onDestroy() {
        unloadKoinModules(characterDetailsFragmentModule)
        super.onDestroy()
    }
}
