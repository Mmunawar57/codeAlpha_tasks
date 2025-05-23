package com.codealpha.app.collegealert.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codealpha.app.collegealert.R
import com.codealpha.app.collegealert.adapters.NewsFeedAdapter
import com.codealpha.app.collegealert.databinding.FragmentNewfeedsBinding
import com.codealpha.app.collegealert.modelclasses.NewsFeed
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class NewsfeedFragment : Fragment() {
    private lateinit var adapter: NewsFeedAdapter
    private lateinit var databaseReference: FirebaseFirestore
    private val newsFeed = mutableListOf<NewsFeed>()
    private var _binding: FragmentNewfeedsBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewfeedsBinding.inflate(inflater, container, false)
        databaseReference = Firebase.firestore
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchDatabase()
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun fetchDatabase() {
        Log.d("NewsfeedFragment", "fetchDatabase: method called()")
        newsFeed.clear()
        databaseReference.collection("newsFeedData")
            .get().addOnSuccessListener { results ->
                results.documents
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
                        adapter = NewsFeedAdapter(newsFeed) {pos->
                            val bundle= Bundle().apply {
                                putString("title",newsFeed[pos].title)
                                putString("description",newsFeed[pos].description)
                                putString("date",newsFeed[pos].date)
                                putString("location",newsFeed[pos].location)
                                putString("image",newsFeed[pos].image)
                            }
                            findNavController().navigate(R.id.action_newfeedsFragment_to_detailsFragment,bundle)
                            _binding = null
                        }
                        binding.recyclerView.adapter = adapter
                    }
                    adapter.notifyDataSetChanged()
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