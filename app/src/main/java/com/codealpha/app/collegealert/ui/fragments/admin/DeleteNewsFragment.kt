package com.codealpha.app.collegealert.ui.fragments.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codealpha.app.collegealert.adapters.DeleteNewsFeedAdapter
import com.codealpha.app.collegealert.databinding.FragmentDeletenewsBinding
import com.codealpha.app.collegealert.databinding.FragmentNewfeedsBinding
import com.codealpha.app.collegealert.modelclasses.NewsFeed
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class DeleteNewsFragment : Fragment() {
    private lateinit var adapter: DeleteNewsFeedAdapter
    private lateinit var databaseReference: FirebaseFirestore
    private val newsFeed = ArrayList<NewsFeed>()
    private var _binding: FragmentDeletenewsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDeletenewsBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchDatabase()

    }
    private fun fetchDatabase() {
        databaseReference = Firebase.firestore
        databaseReference.collection("newsFeedData")
            .get().addOnSuccessListener { results ->
               val id= databaseReference.collection("newsFeed").id
                Log.d("NewsfeedFragment", "$id")

                if(results != null) {
                    for (document in results) {
                        Log.d("NewsfeedFragment", "fetchDatabase: document:${document} ")
                        Log.d(
                            "NewsfeedFragment",
                            "fetchDatabase: id:${document.id}, data: ${document.data}"
                        )
                        //  val news = document.toObject(NewsFeed::class.java)
                        //  news?.let { newsFeed.add(it) }
                        val new = NewsFeed(
                            document.data["title"].toString(),
                            document.data["description"].toString(),
                            document.data["date"].toString(),
                            document.data["location"].toString(),
                            document.data["image"].toString()
                            ,document.id
                        )
                        newsFeed.add(new)
                    }
                    if(true) {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                    if(binding.recyclerView.adapter == null) {
                        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                        adapter = DeleteNewsFeedAdapter(newsFeed) { item, pos ->
                            databaseReference.collection("newsFeedData").document(item.documentId)
                                .delete()
                                .addOnSuccessListener {
                                    if (pos in newsFeed.indices) {
                                        newsFeed.removeAt(pos)
                                        adapter.notifyItemRemoved(pos)
                                    }
                                    if (newsFeed.isEmpty()) {
                                        Toast.makeText(
                                            requireContext(),
                                            "No more events",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        binding.recyclerView.visibility = View.INVISIBLE
                                        binding.txtNoMore.visibility = View.VISIBLE
                                    }
                                }
                        }

                        binding.recyclerView.adapter = adapter
                    }
                } else {
                    Log.d("NewsfeedFragment", "fetchDatabase: Failed to fetch data from database")
                }
            }
            .addOnFailureListener {
                Log.d("NewsfeedFragment", "fetchDatabase: Failed to fetch data from database")
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}