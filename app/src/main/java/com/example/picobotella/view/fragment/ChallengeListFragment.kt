package com.example.picobotella.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.picobotella.databinding.FragmentChallengeListBinding
import com.example.picobotella.view.adapter.ChallengeAdapter
import com.example.picobotella.viewmodel.ChallengeViewModel

class ChallengeListFragment : Fragment() {

    private var _binding: FragmentChallengeListBinding? = null
    private val binding get() = _binding!!

    private val challengeViewModel: ChallengeViewModel by activityViewModels()
    private var wasAudioOnInitially = true
    private lateinit var challengeAdapter: ChallengeAdapter

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

        wasAudioOnInitially = challengeViewModel.isAudioOn.value ?: true
        if (wasAudioOnInitially) {
            challengeViewModel.setAudioState(false)
        }

        binding.btnBack.setOnClickListener {
            if (wasAudioOnInitially) {
                challengeViewModel.setAudioState(true)
            }
            findNavController().navigateUp()
        }

        // HU 7.0: Abrir diálogo para agregar
        binding.fabAddChallenge.setOnClickListener {
            val dialog = AddChallengeDialog()
            dialog.show(parentFragmentManager, "AddChallengeDialog")
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // Inicializamos el adaptador con lambdas vacías para editar/eliminar (HU 8 y 9)
        challengeAdapter = ChallengeAdapter(
            challenges = emptyList(),
            onEditClick = { /* HU 8.0: Pendiente implementar diálogo de edición */ },
            onDeleteClick = { /* HU 9.0: Pendiente implementar diálogo de eliminación */ }
        )

        binding.recyclerViewChallenges.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = challengeAdapter
        }

        // Observamos el LiveData. Cuando se inserte un reto en HU 7.0, esto se disparará automáticamente.
        challengeViewModel.listChallenge.observe(viewLifecycleOwner) { challenges ->
            challengeAdapter.updateList(challenges)
        }

        // Carga inicial de datos
        challengeViewModel.getListChallenge()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
