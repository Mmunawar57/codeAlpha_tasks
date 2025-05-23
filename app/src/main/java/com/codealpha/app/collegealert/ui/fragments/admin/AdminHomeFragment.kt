package com.codealpha.app.collegealert.ui.fragments.admin

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.codealpha.app.collegealert.R
import com.codealpha.app.collegealert.databinding.FragmentAdminhomeBinding
import com.codealpha.app.collegealert.ui.activities.MainActivity
import com.codealpha.app.collegealert.utils.SharedPref
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdminHomeFragment : Fragment() {
    private var _binding: FragmentAdminhomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdminhomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            cardSetting.setOnClickListener {
                findNavController().navigate(R.id.action_adminHomeFragment_to_settingFragment)
            }
            cardSignout.setOnClickListener {
                val dialog= MaterialAlertDialogBuilder(requireContext())
                dialog.apply {
                    setMessage("Are You Sure you want to Sign out?")
                    setPositiveButton("Yes",listenerPositive )
                    setNegativeButton("No",listenerNegative)
                }.show()
            }
            cardUpload.setOnClickListener {
                findNavController().navigate(R.id.action_adminHomeFragment_to_uploadNewsFragment)
            }
            cardDelete.setOnClickListener {
                findNavController().navigate(R.id.action_adminHomeFragment_to_deleteNewsFragment)
            }
        }
    }


    val listenerPositive=object: DialogInterface.OnClickListener{
        override fun onClick(p0: DialogInterface?, p1: Int) {
            SharedPref.putBoolean("admin_login",false)
            lifecycleScope.launch {
                binding.progressBar.visibility = View.VISIBLE
                delay(1500)
                binding.progressBar.visibility = View.GONE
                requireContext().startActivity(Intent(requireContext(), MainActivity::class.java))
            }
        }

    }
    val listenerNegative=object: DialogInterface.OnClickListener {
        override fun onClick(p0: DialogInterface?, p1: Int) {
            p0!!.cancel()
        }
    }
        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}