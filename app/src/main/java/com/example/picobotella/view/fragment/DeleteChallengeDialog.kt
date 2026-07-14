package com.example.picobotella.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.picobotella.databinding.DialogDeleteChallengeBinding
import com.example.picobotella.model.Challenge
import com.example.picobotella.viewmodel.ChallengeViewModel

class DeleteChallengeDialog : DialogFragment() {

    private var _binding: DialogDeleteChallengeBinding? = null
    private val binding get() = _binding!!

    private val challengeViewModel: ChallengeViewModel by activityViewModels()

    private var challengeId: Int = -1
    private var challengeDescription: String = ""

    companion object {
        const val TAG = "DeleteChallengeDialog"
        private const val ARG_CHALLENGE_ID = "challenge_id"
        private const val ARG_CHALLENGE_DESC = "challenge_desc"

        fun newInstance(challenge: Challenge): DeleteChallengeDialog {
            val fragment = DeleteChallengeDialog()
            val args = Bundle().apply {
                putInt(ARG_CHALLENGE_ID, challenge.id)
                putString(ARG_CHALLENGE_DESC, challenge.description)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false // El diálogo solo desaparece al dar clic en NO o SI
        arguments?.let {
            challengeId = it.getInt(ARG_CHALLENGE_ID)
            challengeDescription = it.getString(ARG_CHALLENGE_DESC) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogDeleteChallengeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ajustar el fondo transparente de la ventana del diálogo para respetar esquinas y márgenes del XML
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Mostrar descripción del reto
        binding.txtChallengeDescription.text = challengeDescription

        // Click en NO -> Cerrar diálogo y dejar en la ventana de retos
        binding.btnNo.setOnClickListener {
            dismiss()
        }

        // Click en SI -> Eliminar reto local y cerrar diálogo
        binding.btnYes.setOnClickListener {
            challengeViewModel.deleteChallenge(Challenge(id = challengeId, description = challengeDescription))
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
