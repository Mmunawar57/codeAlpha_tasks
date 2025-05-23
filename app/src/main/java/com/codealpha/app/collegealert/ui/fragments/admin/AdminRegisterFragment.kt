package com.codealpha.app.collegealert.ui.fragments.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.codealpha.app.collegealert.R
import com.codealpha.app.collegealert.databinding.FragmentAdminregisterBinding
import com.codealpha.app.collegealert.utils.SharedPref

class AdminRegisterFragment : Fragment(){
    private var _binding: FragmentAdminregisterBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentAdminregisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignIn.setOnClickListener {
            if(checkAllFields(
                    binding.editTextName,
                    binding.editTextEmail,
                    binding.editTextDepartment,
                    binding.editTextPassword, binding.editTextConfirm
                )
            ) {
                SharedPref.putBoolean("adminHasRegistered",true)
                SharedPref.putString("user", "IsAdmin")
                findNavController().navigate(R.id.action_adminRegisterFragment_to_adminLoginFragment)
            }
        }
        binding.txtDontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_adminRegisterFragment_to_adminLoginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

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
            SharedPref.putString("admin_name",editText1.text.toString())
            SharedPref.putString("admin_email",editText2.text.toString())
            SharedPref.putString("admin_department",editText3.text.toString())
            SharedPref.putString("admin_password",editText4.text.toString())

        }
        return isValid
    }

}