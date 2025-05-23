package com.codealpha.app.collegealert

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.codealpha.app.collegealert.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentDetailsBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return  binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchDatabase()
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun fetchDatabase() {
        val title = arguments?.getString("title")
        val description = arguments?.getString("description")
        val date = arguments?.getString("date")
        val location = arguments?.getString("location")
        val image = arguments?.getString("image")
        with(binding){
            txtTitle.text = title
            txtDetails.text = description
            txtDateValue.text = date
            txtLocationValue.text = location
            Glide.with(requireContext())
                .load(image)
                .transform(RoundedCorners(doToPx(10)))
                .into(imgFeed)
        }
    }
    private fun doToPx(dp: Int): Int{
        return (dp * resources.displayMetrics.density).toInt()
    }
}