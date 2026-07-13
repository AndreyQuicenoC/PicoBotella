package com.example.picobotella.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.picobotella.databinding.FragmentInstructionsBinding
import com.example.picobotella.viewmodel.ChallengeViewModel

class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    private val challengeViewModel: ChallengeViewModel by activityViewModels()
    private var wasAudioOnInitially = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Criterio 1: Evaluamos si el audio estaba en ON, y si es así, usamos el ViewModel para apagarlo
        wasAudioOnInitially = challengeViewModel.isAudioOn.value ?: true
        if (wasAudioOnInitially) {
            challengeViewModel.setAudioState(false) // Esto muta el LiveData y muta el Toolbar del Home indirectamente
        }

        // Criterio 3: Al dar clic en la flecha de la toolbar personalizada
        binding.toolbarRules.setNavigationOnClickListener {
            if (wasAudioOnInitially) {
                challengeViewModel.setAudioState(true) // Restablece el audio globalmente
            }
            findNavController().navigateUp() // Regresa al Home
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}