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
import com.example.picobotella.model.Challenge
import com.example.picobotella.R
class ChallengeListFragment : Fragment() {

    private var _binding: FragmentChallengeListBinding? = null
    private val binding get() = _binding!!

    private val challengeViewModel: ChallengeViewModel by activityViewModels()
    private lateinit var challengeAdapter: ChallengeAdapter
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
        
        wasAudioOnInitially = challengeViewModel.isAudioOn.value ?: true
        if (wasAudioOnInitially) {
            challengeViewModel.setAudioState(false)
        }

        setupToolbar()
        setupRecyclerView()
        setupFAB()
        setupObservers()
        
        challengeViewModel.getListChallenge()
    }

    private fun setupToolbar() {
        binding.btnBack.setOnClickListener {
            if (wasAudioOnInitially) {
                challengeViewModel.setAudioState(true)
            }
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        challengeAdapter = ChallengeAdapter(
            challenges = emptyList(),
            onEditClick = { challengeSeleccionado ->
                // HU 8.0 logic: Empaquetamos los datos del reto en el Bundle
                val bundle = Bundle().apply {
                    putInt("arg_challenge_id", challengeSeleccionado.id)
                    putString("arg_challenge_desc", challengeSeleccionado.description)
                }

                // Navegamos al diálogo usando el Navigation Component
                findNavController().navigate(
                    R.id.action_challengeListFragment_to_editChallengeDialog,
                    bundle
                )
            },
            onDeleteClick = { challenge ->
                showDeleteDialog(challenge)
            }
        )
        
        binding.recyclerViewChallenges.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = challengeAdapter
        }
    }

    private fun showDeleteDialog(challenge: Challenge) {
        val dialog = DeleteChallengeDialog.newInstance(challenge)
        dialog.show(childFragmentManager, DeleteChallengeDialog.TAG)
    }

    private fun setupFAB() {
        binding.fabAddChallenge.setOnClickListener {
            val dialog = AddChallengeDialog()
            dialog.show(parentFragmentManager, "AddChallengeDialog")
        }
    }

    private fun setupObservers() {
        challengeViewModel.listChallenge.observe(viewLifecycleOwner) { list ->
            challengeAdapter.updateList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
