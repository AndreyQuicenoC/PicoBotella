package com.example.picobotella.view.fragment

<<<<<<< HEAD
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
=======
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
>>>>>>> develop
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
<<<<<<< HEAD
=======
import com.example.picobotella.R
>>>>>>> develop
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
<<<<<<< HEAD
=======
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
>>>>>>> develop
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
<<<<<<< HEAD
        
        // Criterio 7: El diálogo solo desaparece al dar clic en cancelar o guardar
        isCancelable = false

=======
>>>>>>> develop
        setupListeners()
    }

    private fun setupListeners() {
<<<<<<< HEAD
        // Criterio 4: Botón Cancelar
=======
>>>>>>> develop
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

<<<<<<< HEAD
        // Criterio 6: Botón Guardar
=======
>>>>>>> develop
        binding.btnSave.setOnClickListener {
            val description = binding.etChallenge.text.toString().trim()
            if (description.isNotEmpty()) {
                challengeViewModel.insertChallenge(description)
                dismiss()
<<<<<<< HEAD
            }
        }

        // Criterio 5: Habilitar/Deshabilitar botón Guardar según el texto
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
        val color = if (enabled) Color.parseColor("#FF5722") else Color.parseColor("#D3D3D3")
        binding.btnSave.backgroundTintList = ColorStateList.valueOf(color)
    }

    override fun onStart() {
        super.onStart()
        // Ajustar el ancho del diálogo
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // Hacer el fondo del diálogo transparente para que se vea el CardView redondeado
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
=======
            } else {
                binding.tilChallenge.error = getString(R.string.error_empty_challenge)
            }
        }
>>>>>>> develop
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
<<<<<<< HEAD
=======

    companion object {
        const val TAG = "AddChallengeDialog"
        fun newInstance() = AddChallengeDialog()
    }
>>>>>>> develop
}
