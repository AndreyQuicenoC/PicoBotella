package com.example.picobotella.view.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.picobotella.R
import com.example.picobotella.databinding.DialogEditChallengeBinding
import com.example.picobotella.model.Challenge
import com.example.picobotella.viewmodel.ChallengeViewModel

class EditChallengeDialog(private val challenge: Challenge) : DialogFragment() {

    private var _binding: DialogEditChallengeBinding? = null
    private val binding get() = _binding!!

    private val challengeViewModel: ChallengeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogEditChallengeBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etChallenge.setText(challenge.description)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            val description = binding.etChallenge.text.toString().trim()
            if (description.isNotEmpty()) {
                val updatedChallenge = challenge.copy(description = description)
                challengeViewModel.updateChallenge(updatedChallenge)
                dismiss()
            } else {
                binding.tilChallenge.error = getString(R.string.error_empty_challenge)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "EditChallengeDialog"
        fun newInstance(challenge: Challenge) = EditChallengeDialog(challenge)
    }
}
