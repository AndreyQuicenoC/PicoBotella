package com.example.picobotella.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.picobotella.R
import com.example.picobotella.databinding.FragmentHomeBinding
import com.example.picobotella.utils.BackgroundAudioManager
import com.example.picobotella.utils.BottleSound
import com.example.picobotella.viewmodel.ChallengeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val challengeViewModel: ChallengeViewModel by activityViewModels()
    private var backgroundAudioManager: BackgroundAudioManager? = null

    private var bottleAudio: BottleSound? = null

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
        setupToolbar()
        setupControllers()
        setupObservers()

    }

    override fun onResume() {
        super.onResume()
        if (challengeViewModel.isAudioOn.value == true) {
            backgroundAudioManager?.start()
        } else {
            backgroundAudioManager?.pause()
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

    private fun setupToolbar() {
        binding.customToolbar.onRateClick = {
            openPlayStore()
        }
        binding.customToolbar.onAudioToggleClick = {
            challengeViewModel.toggleAudio()
        }
        binding.customToolbar.onInstructionsClick = {
            findNavController().navigate(R.id.action_homeFragment_to_instructionsFragment)
        }
        binding.customToolbar.onChallengesClick = {
            findNavController().navigate(R.id.action_homeFragment_to_challengeListFragment)
        }
        binding.customToolbar.onShareClick = {
            findNavController().navigate(R.id.action_homeFragment_to_shareFragment)
        }
    }

    private fun openPlayStore() {
        val playStoreUrl = "https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es"
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "No se pudo abrir la tienda de aplicaciones",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun showSpinButton() {
        binding.btnSpinCircle.visibility = View.VISIBLE
        binding.txtPresionameTitle.visibility = View.VISIBLE
        setupBlinkAnimation()
    }


    private fun setupControllers() {
        binding.btnSpinCircle.setOnClickListener {
            //applyTouchAnimation(it) { challengeViewModel.spinBottle() }
            challengeViewModel.spinBottle()
        }
    }

    private fun setupObservers() {
        binding.imgBottle.rotation = challengeViewModel.bottleRotation.value ?: 0f

        challengeViewModel.countdown.observe(viewLifecycleOwner) { count ->
            binding.txtCountdown.text = count?.toString() ?: ""
            binding.txtCountdown.contentDescription = if (count != null) {
                getString(R.string.desc_countdown, count)
            } else {
                getString(R.string.desc_bottle)
            }
        }

        challengeViewModel.bottleRotation.observe(viewLifecycleOwner) { degrees ->
            if (challengeViewModel.isSpinning.value == true) {
                binding.imgBottle.animate()
                    .rotation(degrees)
                    .setDuration(ChallengeViewModel.SPIN_DURATION_MS)
                    .start()

            } else {
                binding.imgBottle.animate().cancel()
                binding.imgBottle.rotation = degrees

            }
        }

        challengeViewModel.isAudioOn.observe(viewLifecycleOwner) { isOn ->
            binding.customToolbar.setAudioOn(isOn)
            syncBackgroundAudio(isOn)
        }

        challengeViewModel.isSpinning.observe(viewLifecycleOwner) { isSpinning ->

            if (isSpinning) {

                binding.btnSpinCircle.clearAnimation()
                binding.txtPresionameTitle.clearAnimation()

                //binding.btnSpinCircle.visibility = View.VISIBLE
                //binding.txtPresionameTitle.visibility = View.VISIBLE
                //setupBlinkAnimation()

                binding.btnSpinCircle.visibility = View.GONE
                binding.txtPresionameTitle.visibility = View.GONE


                if (bottleAudio == null) {
                    bottleAudio = BottleSound(requireContext())
                }
                bottleAudio?.start()

            } else {
                //binding.btnSpinCircle.visibility = View.VISIBLE
                //binding.txtPresionameTitle.visibility = View.VISIBLE
                bottleAudio?.pause()
                //setupBlinkAnimation()
            }
        }

        challengeViewModel.randomChallengeResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                val dialog = RandomChallengeDialog.newInstance()
                dialog.show(childFragmentManager, RandomChallengeDialog.TAG)
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
