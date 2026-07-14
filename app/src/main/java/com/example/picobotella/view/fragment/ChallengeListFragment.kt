package com.example.picobotella.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.picobotella.databinding.FragmentChallengeListBinding
import com.example.picobotella.view.adapter.ChallengeAdapter
import com.example.picobotella.viewmodel.ChallengeViewModel
import androidx.navigation.fragment.findNavController

class ChallengeListFragment : Fragment() {

    private var _binding: FragmentChallengeListBinding? = null
    private val binding get() = _binding!!

    private val challengeViewModel: ChallengeViewModel by activityViewModels()
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
        setupToolbar()
        setupRecyclerView()
        setupFAB()
        setupObservers()
        
        // Cargar la lista inicialmente
        challengeViewModel.getListChallenge()
    }

    private fun setupToolbar() {
        binding.toolbarChallenges.setNavigationOnClickListener {
        findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        challengeAdapter = ChallengeAdapter(
            challenges = emptyList(),
            onEditClick = { challenge ->
                val dialog = EditChallengeDialog.newInstance(challenge)
                dialog.show(parentFragmentManager, EditChallengeDialog.TAG)
            },
            onDeleteClick = { challenge ->
                val dialog = DeleteChallengeDialog.newInstance(challenge)
                dialog.show(parentFragmentManager, DeleteChallengeDialog.TAG)
            }
        )
        binding.rvChallenges.adapter = challengeAdapter
    }

    private fun setupFAB() {
        binding.fabAddChallenge.setOnClickListener {
            val dialog = AddChallengeDialog.newInstance()
            dialog.show(parentFragmentManager, AddChallengeDialog.TAG)
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
