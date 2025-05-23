package com.codealpha.app.collegealert.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codealpha.app.collegealert.R
import com.codealpha.app.collegealert.databinding.FragmentDashboardBinding
import com.codealpha.app.collegealert.utils.SharedPref

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        with(binding) {
            btnAdmin.setOnClickListener {
                if(!SharedPref.getBoolean("adminHasRegistered")) {
                        findNavController().navigate(R.id.action_dashboardFragment_to_adminRegisterFragment)
                    } else {
                        findNavController().navigate(R.id.action_dashboardFragment_to_adminLoginFragment)
                    }
                }
            btnStudent.setOnClickListener {
                if(!SharedPref.getBoolean("studentHasRegistered")) {
                    findNavController().navigate(R.id.action_dashboardFragment_to_studentSignUpFragment)
                } else {
                    findNavController().navigate(R.id.action_dashboardFragment_to_studentLoginFragment)
                }
            }
        }

            return binding.root
        }


        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }