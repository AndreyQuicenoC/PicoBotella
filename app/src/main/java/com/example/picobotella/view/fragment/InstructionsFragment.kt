package com.example.picobotella.view.fragment

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.picobotella.R
import com.example.picobotella.databinding.FragmentInstructionsBinding
import com.example.picobotella.viewmodel.ChallengeViewModel
import java.util.Locale

class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    private val challengeViewModel: ChallengeViewModel by activityViewModels()
    private var wasAudioOnInitially = true
    private var tts: TextToSpeech? = null

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

        // Inicializar motor de Text-To-Speech (Lectura en voz alta)
        tts = TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.setLanguage(Locale("es", "ES"))
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {}
                    override fun onDone(utteranceId: String?) {
                        activity?.runOnUiThread {
                            stopPulseAnimation()
                            _binding?.btnSpeakInstructions?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_audio_off, 0, 0, 0)
                            _binding?.btnSpeakInstructions?.text = "Leer en voz alta instrucciones"
                        }
                    }
                    override fun onError(utteranceId: String?) {
                        activity?.runOnUiThread {
                            stopPulseAnimation()
                            _binding?.btnSpeakInstructions?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_audio_off, 0, 0, 0)
                            _binding?.btnSpeakInstructions?.text = "Leer en voz alta instrucciones"
                        }
                    }
                })
                // Habilitar el botón una vez que el motor de TTS está completamente inicializado y listo
                activity?.runOnUiThread {
                    _binding?.btnSpeakInstructions?.isEnabled = true
                }
            }
        }

        // Listener del botón de lectura en voz alta
        binding.btnSpeakInstructions.setOnClickListener {
            if (tts?.isSpeaking == true) {
                tts?.stop()
                stopPulseAnimation()
                binding.btnSpeakInstructions.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_audio_off, 0, 0, 0)
                binding.btnSpeakInstructions.text = "Leer en voz alta instrucciones"
            } else {
                val howToPlayTitle = "¿Cómo se juega?"
                val howToPlayDesc = "Los jugadores forman un círculo y en el centro colocan el dispositivo móvil, luego tocan el botón parpadeante para girar la botella. El jugador que señale la botella debe cumplir el reto que lanza la app, de lo contrario abandona el juego."
                val whoWinsTitle = "¿Quién gana?"
                val whoWinsDesc = "Gana el último jugador que no abandone el juego."
                val textToRead = "$howToPlayTitle $howToPlayDesc $whoWinsTitle $whoWinsDesc"

                binding.btnSpeakInstructions.text = "Detener lectura"
                binding.btnSpeakInstructions.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_audio_on, 0, 0, 0)
                startPulseAnimation()
                
                tts?.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, "InstructionsTTS")
            }
        }

        // Criterio 3: Al dar clic en la flecha de la toolbar personalizada
        binding.toolbarRules.setNavigationOnClickListener {
            tts?.stop()
            stopPulseAnimation()
            if (wasAudioOnInitially) {
                challengeViewModel.setAudioState(true) // Restablece el audio globalmente
            }
            findNavController().navigateUp() // Regresa al Home
        }
    }

    private fun startPulseAnimation() {
        binding.btnSpeakInstructions.animate()
            .scaleX(1.05f)
            .scaleY(1.05f)
            .setDuration(600)
            .withEndAction {
                binding.btnSpeakInstructions.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(600)
                    .withEndAction {
                        if (tts?.isSpeaking == true) {
                            startPulseAnimation()
                        }
                    }
                    .start()
            }
            .start()
    }

    private fun stopPulseAnimation() {
        binding.btnSpeakInstructions.animate().cancel()
        binding.btnSpeakInstructions.scaleX = 1.0f
        binding.btnSpeakInstructions.scaleY = 1.0f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tts?.let {
            it.stop()
            it.shutdown()
        }
        tts = null
        _binding = null
    }
}