package com.example.picobotella.view.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.picobotella.databinding.DialogAddChallengeBinding
import com.example.picobotella.viewmodel.ChallengeViewModel

class AddChallengeDialog : DialogFragment() {

    private var _binding: DialogAddChallengeBinding? = null
    private val binding get() = _binding!!

    private val challengeViewModel: ChallengeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddChallengeBinding.inflate(inflater, container, false)
        // Fondo transparente desde develop para respetar el CardView
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Criterio 7 (HU 7.0): Solo desaparece al dar clic en botones
        isCancelable = false

        setupListeners()
    }

    private fun setupListeners() {
        // Criterio 4: Botón Cancelar
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        // Criterio 6: Botón Guardar
        binding.btnSave.setOnClickListener {
            val description = binding.etChallenge.text.toString().trim()
            if (description.isNotEmpty()) {
                challengeViewModel.insertChallenge(description)
                dismiss()
            }
        }

        // Criterio 5: Habilitación dinámica del botón Guardar
        binding.etChallenge.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val hasText = !s.isNullOrBlank()
                updateSaveButtonState(hasText)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateSaveButtonState(enabled: Boolean) {
        binding.btnSave.isEnabled = enabled
        // Color naranja si está activo, gris si está inactivo
        val color = if (enabled) Color.parseColor("#FF5722") else Color.parseColor("#D3D3D3")
        binding.btnSave.backgroundTintList = ColorStateList.valueOf(color)
    }

    override fun onStart() {
        super.onStart()
        // Ajustar el tamaño del diálogo al ancho de la pantalla
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "AddChallengeDialog"
        fun newInstance() = AddChallengeDialog()
    }
}
