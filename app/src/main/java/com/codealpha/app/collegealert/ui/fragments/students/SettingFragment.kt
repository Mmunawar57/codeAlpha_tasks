package com.codealpha.app.collegealert.ui.fragments.students

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.codealpha.app.collegealert.R
import com.codealpha.app.collegealert.BuildConfig
import com.codealpha.app.collegealert.databinding.FragmentSettingBinding
import com.codealpha.app.collegealert.utils.SharedPref

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        updateUI()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toggle.setOnClickListener {
            toggleTheme()

        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.txtVersionValue.text = BuildConfig.VERSION_NAME
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun updateUI() {
        if(SharedPref.getBoolean("IS_DARK_MODE")) {
            binding.toggle.setImageResource(R.drawable.icon_toggle_on)
        } else {
            binding.toggle.setImageResource(R.drawable.icon_toggle_off)
        }
    }

    private fun toggleTheme() {
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            SharedPref.putBoolean("IS_DARK_MODE", false)
            binding.toggle.setImageResource(R.drawable.icon_toggle_off)
        } else {
            // Treat all other modes as light mode initially
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            SharedPref.putBoolean("IS_DARK_MODE", true)
            binding.toggle.setImageResource(R.drawable.icon_toggle_on)
        }

        // Optional: recreate activity to apply changes immediately
      //  requireActivity().recreate()
    }

}