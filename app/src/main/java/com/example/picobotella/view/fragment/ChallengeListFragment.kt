package com.example.picobotella.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.picobotella.databinding.FragmentChallengeListBinding
import com.example.picobotella.viewmodel.ChallengeViewModel

class ChallengeListFragment : Fragment() {

    private var _binding: FragmentChallengeListBinding? = null
    private val binding get() = _binding!!

    private val challengeViewModel: ChallengeViewModel by activityViewModels()
    private var wasAudioOnInitially = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChallengeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Guardamos el estado inicial del audio
        wasAudioOnInitially = challengeViewModel.isAudioOn.value ?: true
        
        // Si el audio está encendido, lo pausamos al entrar
        if (wasAudioOnInitially) {
            challengeViewModel.setAudioState(false)
        }

        // Lógica del botón Volver (flecha naranja)
        binding.btnBack.setOnClickListener {
            // Si el audio estaba encendido, lo reactivamos al salir
            if (wasAudioOnInitially) {
                challengeViewModel.setAudioState(true)
            }
            // Regresamos al Home
            findNavController().navigateUp()
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // Placeholder para la configuración del Adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
