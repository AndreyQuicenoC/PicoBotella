package com.example.picobotella.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.picobotella.databinding.FragmentShareBinding
import com.example.picobotella.R

class ShareFragment : Fragment() {

    private var _binding: FragmentShareBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Acción del botón Volver (flecha naranja)
        binding.btnBackShare.setOnClickListener {
            findNavController().navigateUp()
        }

        // Acción del botón Compartir aplicación
        binding.btnShareApp.setOnClickListener {
            triggerShareIntent()
        }
    }

    private fun triggerShareIntent() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            val title = getString(R.string.share_title)
            val slogan = getString(R.string.share_slogan)
            val downloadUrl = getString(R.string.share_download_url)
            
            val message = "$title\n$slogan\n$downloadUrl"
            putExtra(Intent.EXTRA_TEXT, message)
        }
        
        startActivity(Intent.createChooser(shareIntent, "Compartir app usando"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
