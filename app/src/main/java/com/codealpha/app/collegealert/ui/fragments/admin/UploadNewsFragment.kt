package com.codealpha.app.collegealert.ui.fragments.admin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.codealpha.app.collegealert.R
import com.codealpha.app.collegealert.databinding.FragmentUploadnewsBinding
import com.codealpha.app.collegealert.modelclasses.NewsFeed
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.jvm.Throws
import kotlin.toString


class UploadNewsFragment : Fragment() {
    private var _binding: FragmentUploadnewsBinding? = null
    private val binding get() = _binding!!
    private  var imageUri: Uri= Uri.EMPTY
    private val newsFeed = mutableListOf<NewsFeed>()
    private var title: String? = null
    private var description: String? = null
    private var date: String? = null
    private var location: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUploadnewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            imgFeed.setOnClickListener {
                getImageFromGallery()
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnSave.setOnClickListener {
                if(txtTitle.text.toString().isNotEmpty() && txtDateValue.text.toString()
                        .isNotEmpty() && txtLocationValue.text.toString()
                        .isNotEmpty() && txtDetails.text.toString().isNotEmpty() && imageUri != Uri.EMPTY
                ) {
                    prgressBar.visibility = View.VISIBLE
                    view1.visibility = View.VISIBLE
                    title = txtTitle.text.toString()
                    description = txtDetails.text.toString()
                    date = txtDateValue.text.toString()
                    location = txtLocationValue.text.toString()
                    newsFeed.add(
                        NewsFeed(
                            title!!,
                            description!!,
                            date!!,
                            location!!,
                            imageUri.toString())
                    )
                    insertData(newsFeed)
                } else {
                    txtTitle.error = "Field cannot be empty"
                    txtDetails.error = "Field cannot be empty"
                    txtDateValue.error = "Field cannot be empty"
                    txtLocationValue.error = "Field cannot be empty"
                    Toast.makeText(requireContext(), "Fields should not be empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private var imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK) {
                imageUri = result.data?.data!!
                Glide.with(requireContext()).load(imageUri).into(binding.imgFeed)
            }
        }

    private fun getImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }
        imagePickerLauncher.launch(intent)
    }

    private fun insertData(newsFeed: List<NewsFeed>) {
        val db = Firebase.firestore
        for (i in newsFeed) {
            val eventMap = hashMapOf(
                "title" to i.title,
                "description" to i.description,
                "date" to i.date,
                "location" to i.location,
                "image" to i.image
            )
            try {
                db.collection("newsFeedData").add(eventMap)
                    .addOnFailureListener { e ->
                        IOException("Exception during Firestore add: ${e.message}, Cause: ${e.cause}")
                    }.addOnCompleteListener { result ->
                        if(result.isSuccessful) {
                            lifecycleScope.launch {
                                binding.prgressBar.visibility = View.VISIBLE
                                binding.view1.visibility = View.VISIBLE
                                delay(2000)
                            }
                            Toast.makeText(
                                requireContext(),
                                "Data Uploaded Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            clearData()
                            binding.prgressBar.visibility = View.GONE
                            binding.view1.visibility = View.GONE
                        }
                        Log.e("UploadData", "insertData: Completed ")
                    }
            } catch (e: Exception) {
                IOException("Exception during Firestore add: ${e.message}, Cause: ${e.cause}")
            }
        }
    }

    private fun clearData() {
        with(binding) {
            txtTitle.text.clear()
            txtDetails.text.clear()
            txtDateValue.text.clear()
            txtLocationValue.text.clear()
            imgFeed.setImageResource(R.drawable.alll)
        }
    }


}