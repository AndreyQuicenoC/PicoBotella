package com.example.picobotella.view.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.picobotella.databinding.DialogRandomChallengeBinding
import com.bumptech.glide.Glide
import com.example.picobotella.R
import com.example.picobotella.viewmodel.ChallengeViewModel

class RandomChallengeDialog : DialogFragment() {

    private var _binding: DialogRandomChallengeBinding? = null
    private val binding get() = _binding!!

    private val challengeViewModel: ChallengeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogRandomChallengeBinding.inflate(inflater, container, false)
        
        // Configuración para que el fondo del diálogo sea transparente y use el diseño personalizado
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false // No permite cerrar tocando fuera
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        challengeViewModel.randomChallengeResult.observe(viewLifecycleOwner) { result ->
            result?.let { (challenge, pokemon) ->
                // Mostramos el texto del reto obtenido de la DB local
                binding.tvChallengeText.text = challenge.description
                
                // Cargar imagen del Pokémon con Glide
                // Forzamos https para evitar problemas de seguridad y asegurar la carga desde Serebii
                val imageUrl = pokemon.img.replace("http://", "https://")
                
                if (imageUrl.isNotEmpty()) {
                    Glide.with(requireContext())
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_bottle_splash)
                        .error(R.drawable.ic_bottle_splash)
                        .into(binding.ivPokemonImage)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnClose.setOnClickListener {
            challengeViewModel.resetGameState()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "RandomChallengeDialog"
        fun newInstance() = RandomChallengeDialog()
    }
}
