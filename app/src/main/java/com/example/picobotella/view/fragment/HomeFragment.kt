package com.example.picobotella.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.picobotella.R
import com.example.picobotella.databinding.FragmentHomeBinding
import com.example.picobotella.utils.BackgroundAudioManager
import com.example.picobotella.viewmodel.ChallengeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val challengeViewModel: ChallengeViewModel by activityViewModels()
    private var backgroundAudioManager: BackgroundAudioManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backgroundAudioManager = BackgroundAudioManager(requireContext())
        setupBlinkAnimation()
        setupControllers()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        if (challengeViewModel.isAudioOn.value == true) {
            backgroundAudioManager?.start()
        }
    }

    override fun onPause() {
        backgroundAudioManager?.pause()
        super.onPause()
    }

    override fun onDestroyView() {
        backgroundAudioManager?.release()
        backgroundAudioManager = null
        _binding = null
        super.onDestroyView()
    }

    private fun setupControllers() {
        binding.btnInstructions.setOnClickListener {
            applyTouchAnimation(it) {
                findNavController().navigate(R.id.action_homeFragment_to_instructionsFragment)
            }
        }

        binding.btnChallenges.setOnClickListener {
            applyTouchAnimation(it) {
                findNavController().navigate(R.id.action_homeFragment_to_challengeListFragment)
            }
        }

        binding.btnAudioToggle.setOnClickListener {
            applyTouchAnimation(it) { challengeViewModel.toggleAudio() }
        }

        binding.btnRate.setOnClickListener {
            applyTouchAnimation(it) { /* HU 4.0 */ }
        }

        binding.btnShare.setOnClickListener {
            applyTouchAnimation(it) { /* HU 10.0 */ }
        }

        binding.btnSpinCircle.setOnClickListener {
            applyTouchAnimation(it) { challengeViewModel.spinBottle() }
        }
    }

    private fun setupObservers() {
        challengeViewModel.countdown.observe(viewLifecycleOwner) { count ->
            binding.txtCountdown.text = count?.toString() ?: ""
            binding.txtCountdown.contentDescription = if (count != null) {
                getString(R.string.desc_countdown, count)
            } else {
                getString(R.string.desc_bottle)
            }
        }

        challengeViewModel.bottleRotation.observe(viewLifecycleOwner) { degrees ->
            binding.imgBottle.animate()
                .rotation(degrees)
                .setDuration(4000)
                .start()
        }

        challengeViewModel.isAudioOn.observe(viewLifecycleOwner) { isOn ->
            val icon = if (isOn) R.drawable.ic_audio_on else R.drawable.ic_audio_off
            binding.btnAudioToggle.setImageResource(icon)
            syncBackgroundAudio(isOn)
        }

        challengeViewModel.isSpinning.observe(viewLifecycleOwner) { isSpinning ->
            val visibility = if (isSpinning) View.INVISIBLE else View.VISIBLE
            binding.btnSpinCircle.visibility = visibility
            binding.txtPresionameTitle.visibility = visibility
            if (!isSpinning) {
                setupBlinkAnimation()
            }
        }
    }

    private fun syncBackgroundAudio(isOn: Boolean) {
        if (isOn) {
            backgroundAudioManager?.start()
        } else {
            backgroundAudioManager?.pause()
        }
    }

    private fun setupBlinkAnimation() {
        val blinkAnimation = AlphaAnimation(1.0f, 0.2f).apply {
            duration = 700
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
        binding.btnSpinCircle.startAnimation(blinkAnimation)
        binding.txtPresionameTitle.startAnimation(blinkAnimation)
    }

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
                    .withEndAction { onAnimationEnd() }
                    .start()
            }
            .start()
    }
}
