package com.example.picobotella.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.picobotella.R
import com.example.picobotella.databinding.FragmentHomeBinding
import com.example.picobotella.viewmodel.ChallengeViewModel

class HomeFragment : Fragment() {

    // Cambiamos a la estructura de View Binding del profesor
    private lateinit var binding: FragmentHomeBinding
    private val challengeViewModel: ChallengeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos usando la clase autogenerada de View Binding por Android Studio
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
        observadorViewModel()
        setupBlinkAnimation()
    }

    // Siguiendo el metodo exacto del profesor para los clicks y navegación
    private fun controladores() {

        // Navegación a Instrucciones (HU 5.0)
        binding.btnInstructions.setOnClickListener {
            applyTouchAnimation(it) {
                findNavController().navigate(R.id.action_homeFragment_to_instructionsFragment)
            }
        }

        // Navegación a Retos (HU 6.0)
        binding.btnChallenges.setOnClickListener {
            applyTouchAnimation(it) {
                findNavController().navigate(R.id.action_homeFragment_to_challengeListFragment)
            }
        }

        // Click en el botón circular para lanzar el giro de botella (HU 11)
        binding.btnSpinCircle.setOnClickListener {
            applyTouchAnimation(it) {
                challengeViewModel.spinBottle()
            }
        }

        // Inicialización para los demás botones de la Toolbar
        binding.btnRate.setOnClickListener { applyTouchAnimation(it) { /* Enlace Nequi HU 4.0 */ } }
        binding.btnAudioToggle.setOnClickListener { applyTouchAnimation(it) { challengeViewModel.toggleAudio() } }
        binding.btnShare.setOnClickListener { applyTouchAnimation(it) { /* Bottom sheet HU 10 */ } }
    }

    // Estructura de observadores idéntica a la clase anterior
    private fun observadorViewModel() {
        // Observa los cambios del contador (3 a 0)
        challengeViewModel.countdown.observe(viewLifecycleOwner) { count ->
            binding.txtCountdown.text = count?.toString() ?: ""
        }

        // Observa si la botella debe rotar
        challengeViewModel.bottleRotation.observe(viewLifecycleOwner) { degrees ->
            binding.imgBottle.animate().rotation(degrees).setDuration(4000).start()
        }
    }

    // Animación de parpadeo requerida para el botón inferior (HU 2.0 Criterio 6)
    private fun setupBlinkAnimation() {
        val blinkAnimation = AlphaAnimation(1.0f, 0.2f).apply {
            duration = 700
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
        binding.btnSpinCircle.startAnimation(blinkAnimation)
        binding.txtPresionameTitle.startAnimation(blinkAnimation)
    }

    // Animación sutil de touch requerida por la Toolbar (HU 3.0 Criterio 7)
    private fun applyTouchAnimation(view: View, onAnimationEnd: () -> Unit) {
        view.animate()
            .scaleX(0.85f)
            .scaleY(0.85f)
            .setDuration(80)
            .withEndAction {
                view.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(80)
                    .withEndAction {
                        onAnimationEnd()
                    }.start()
            }.start()
    }
}