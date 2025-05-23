package com.codealpha.app.collegealert.ui.fragments.admin

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.codealpha.app.collegealert.R
import com.codealpha.app.collegealert.databinding.FragmentAdminLoginBinding
import com.codealpha.app.collegealert.utils.SharedPref

class AdminLoginFragment : Fragment() {
    private var _binding: FragmentAdminLoginBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAdminLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            if(checkAllFields(binding.editTextName, binding.editTextPassword)) {
                findNavController().navigate(R.id.action_adminLoginFragment_to_adminHomeFragment)
                SharedPref.putBoolean("admin_login",true)
            }
        }
        binding.txtDontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_adminLoginFragment_to_adminRegisterFragment)
        }

    }
    private fun checkAllFields(editText1: EditText, editText2: EditText): Boolean {
        var isValid = true
        val email = SharedPref.getString("admin_email")
        val password = SharedPref.getString("admin_password")
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}