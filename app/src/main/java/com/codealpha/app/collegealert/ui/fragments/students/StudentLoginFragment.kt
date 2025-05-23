package com.codealpha.app.collegealert.ui.fragments.students

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codealpha.app.collegealert.R
import com.codealpha.app.collegealert.databinding.FragmentStudentLoginBinding
import com.codealpha.app.collegealert.utils.SharedPref

class StudentLoginFragment : Fragment() {
    private var _binding: FragmentStudentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.btnLogin.setOnClickListener {
            if(checkAllFields(binding.editTextName, binding.editTextPassword)) {
                findNavController().navigate(R.id.action_studentLoginFragment_to_homeFragment)
                SharedPref.putBoolean("std_login", true)
            }
        }
        binding.txtDontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_studentLoginFragment_to_studentSignUpFragment)
        }
        return binding.root
    }

    private fun checkAllFields(editText1: EditText, editText2: EditText): Boolean {
        var isValid = true
        val email = SharedPref.getString("std_email")
        val password = SharedPref.getString("std_password")
        if(editText1.text.isEmpty() || editText2.text.isEmpty()) {
            editText1.error = "Email Required"
            editText2.error = "Password Required"
            isValid = false
        }
        if(isValid) {
            if(editText1.text.toString() != email) {
                editText1.error = "Invalid Email"
                isValid = false
            }
            if(editText2.text.toString() != password) {
                editText2.error = "Invalid Password"
                isValid = false

            }
        }
        return isValid
    }
}