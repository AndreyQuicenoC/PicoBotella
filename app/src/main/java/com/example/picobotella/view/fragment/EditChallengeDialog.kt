package com.example.picobotella.view.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.picobotella.databinding.DialogEditChallengeBinding
import com.example.picobotella.model.Challenge
import com.example.picobotella.viewmodel.ChallengeViewModel
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColorInt

class EditChallengeDialog : DialogFragment() {

    private var _binding: DialogEditChallengeBinding? = null
    private val binding get() = _binding!!

    private val challengeViewModel: ChallengeViewModel by activityViewModels()

    // Datos del reto a editar recuperados de forma segura
    private var challengeId: Int = 0
    private var originalDescription: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Criterio de Robustez: Recuperación segura de argumentos primitivos del NavGraph
        arguments?.let {
            challengeId = it.getInt("arg_challenge_id", 0)
            originalDescription = it.getString("arg_challenge_desc", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogEditChallengeBinding.inflate(inflater, container, false)
        // Criterio 1: Fondo blanco con wrapper transparente desde develop para aplicar esquinas curvas
        dialog?.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Criterio 7: El cuadro de diálogo solo desaparece al dar clic en Cancelar o Guardar
        isCancelable = false

        // Criterio 3: Carga el texto original de la descripción a editar
        if (savedInstanceState == null) {
            // Evita sobreescribir lo que escribe el usuario si rota la pantalla
            binding.etChallengeDescription.setText(originalDescription)
        }

        setupListeners()

        // Inicializar el estado del botón guardar basándonos en el texto actual cargado
        val startText = binding.etChallengeDescription.text.toString().trim()
        updateSaveButtonState(startText.isNotEmpty())
    }

    private fun setupListeners() {
        // Criterio 4: Cancelar la operación y cerrar el diálogo
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        // Criterio 6: Guardar la descripción modificada e impactar la DB mediante el ViewModel
        binding.btnSave.setOnClickListener {
            val updatedDescription = binding.etChallengeDescription.text.toString().trim()
            if (updatedDescription.isNotEmpty()) {
                val modifiedChallenge = Challenge(id = challengeId, description = updatedDescription)
                challengeViewModel.updateChallenge(modifiedChallenge)
                dismiss()
            }
        }

        // Criterio 5: Habilitación y cambio de color dinámico del botón Guardar (Naranja activo, Gris inactivo)
        binding.etChallengeDescription.addTextChangedListener(object : TextWatcher {
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
        // Corrección KTX: Convertimos los Strings directamente a enteros de color
        val color = if (enabled) "#FF5722".toColorInt() else "#D3D3D3".toColorInt()
        binding.btnSave.backgroundTintList = ColorStateList.valueOf(color)
    }

    override fun onStart() {
        super.onStart()
        // Ajusta el tamaño del diálogo al ancho de la pantalla de forma consistente con Add
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}