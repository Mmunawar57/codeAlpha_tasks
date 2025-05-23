package com.codealpha.app.collegealert.ui.fragments.students

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codealpha.app.collegealert.R
import com.codealpha.app.collegealert.databinding.FragmentStudentSignupBinding
import com.codealpha.app.collegealert.utils.SharedPref

class StudentSignUpFragment : Fragment() {
    private var _binding: FragmentStudentSignupBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStudentSignupBinding.inflate(inflater, container, false)
        binding.btnSignIn.setOnClickListener {
            if(checkAllFields(
                    binding.editTextName,
                    binding.editTextEmail,
                    binding.editTextDepartment,
                    binding.editTextPassword, binding.editTextConfirm
                )
            ) {
                SharedPref.putBoolean("studentHasRegistered",true)
                SharedPref.putString("user", "IsStudent")
                findNavController().navigate(R.id.action_studentSignUpFragment_to_studentLoginFragment)

            }
        }
        binding.txtDontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_studentSignUpFragment_to_studentLoginFragment)
        }
        return binding.root
    }

    private fun checkAllFields(
        editText1: EditText,
        editText2: EditText,
        editText3: EditText,
        editText4: EditText,
        editText5: EditText
    ): Boolean {
        var isValid: Boolean
        if(editText1.text.isEmpty() && editText2.text.isEmpty() &&
            editText3.text.isEmpty() && editText4.text.isEmpty() &&
            editText5.text.isEmpty()
        ) {
            isValid=false
            editText1.error = "Your Name Required"
            editText2.error = "You Email Required"
            editText3.error = "Your Field Required"
            editText4.error = "Password Required"
            editText5.error = "Password not match"
            Toast.makeText(requireContext(), "Please fill all required data", Toast.LENGTH_SHORT)
                .show()
          if(editText4.text != editText5.text){
              isValid=false
              editText5.error = "Password not match"
          }
        } else {
            isValid=true
            SharedPref.putString("std_name",editText1.text.toString())
            SharedPref.putString("std_email",editText2.text.toString())
            SharedPref.putString("std_department",editText3.text.toString())
            SharedPref.putString("std_password",editText4.text.toString())

        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}