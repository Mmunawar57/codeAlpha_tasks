package com.codealpha.app.collegealert.ui.fragments.students

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.navigation.fragment.findNavController
import com.codealpha.app.collegealert.R
import com.codealpha.app.collegealert.databinding.FragmentHomeBinding
import com.codealpha.app.collegealert.databinding.ProfileLayutBinding
import com.codealpha.app.collegealert.ui.activities.MainActivity
import com.codealpha.app.collegealert.utils.SharedPref
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val name = SharedPref.getString("std_name")
            txtStdName.text = name
            Log.d("HomeFragment", "onCreateView():$name ")
            newsFeed.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_newfeedsFragment)
            }
            cardCalender.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_calenderFragment)
            }
            cardProfile.setOnClickListener {
                showProfileDialog(requireContext())
            }
            cardSetting.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
            }
            layoutSignOut.setOnClickListener {
                val dialog= MaterialAlertDialogBuilder(requireContext())
                dialog.apply {
                    setMessage("Are You Sure you want to Sign out?")
                    setPositiveButton("Yes",listenerPositive )
                    setNegativeButton("No",listenerNegative)
                }.show()
            }
        }
    }
    val listenerPositive=object: DialogInterface.OnClickListener{
        override fun onClick(p0: DialogInterface?, p1: Int) {
            SharedPref.putBoolean("std_login",false)
            requireContext().startActivity(Intent(requireContext(), MainActivity::class.java))
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
    private fun showProfileDialog(context: Context){
        var profileBinding= ProfileLayutBinding.inflate(layoutInflater)
        val dialog= Dialog(context, LayoutParams.MATCH_PARENT)
        val window=dialog.window
        window?.setLayout(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)

        dialog.setContentView(profileBinding.root)
        dialog.show()
        with(profileBinding) {
            txtYourName.text=SharedPref.getString("std_name")
            txtYourEmail.text=SharedPref.getString("std_email")
            txtYourdepartment.text=SharedPref.getString("std_department")
            txtYourPassword.text=SharedPref.getString("std_password")
        }
        if(_binding==null) {
            if(dialog.isShowing) {
                dialog.dismiss()
                dialog.cancel()
            }
        }
    }

}