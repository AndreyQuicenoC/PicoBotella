package com.example.picobotella.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.picobotella.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private var navigationJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottleAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.bottle_splash_anim)
        view.findViewById<View>(R.id.imgSplashBottle).startAnimation(bottleAnimation)

        navigationJob = viewLifecycleOwner.lifecycleScope.launch {
            delay(SPLASH_DURATION_MS)
            navigateToHome()
        }
    }

    private fun navigateToHome() {
        if (!isAdded) return

        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.splashFragment) {
            navController.navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        navigationJob?.cancel()
        navigationJob = null
        super.onDestroyView()
    }

    companion object {
        private const val SPLASH_DURATION_MS = 5000L
    }
}
