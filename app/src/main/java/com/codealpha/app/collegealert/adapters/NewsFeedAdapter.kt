package com.codealpha.app.collegealert.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codealpha.app.collegealert.R
import com.codealpha.app.collegealert.databinding.NewsFeedLayoutBinding
import com.codealpha.app.collegealert.modelclasses.NewsFeed

class NewsFeedAdapter(private val newsFeedList: List<NewsFeed>, val listener: (Int) -> Unit) :
    RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder>() {
    inner class NewsFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = NewsFeedLayoutBinding.bind(itemView)
        fun bind(newsFeed: NewsFeed) {
            binding.txtTitle.text = newsFeed.title
            binding.txtDate.text = newsFeed.date
            Glide.with(itemView.context).load(newsFeed.image).placeholder(R.drawable.icon_upload)
                .into(binding.img)

        }

        init {
            itemView.setOnClickListener {
                listener.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedViewHolder {
        return NewsFeedViewHolder(
            NewsFeedLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }

    override fun getItemCount(): Int {
        return newsFeedList.size
    }

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        holder.bind(newsFeedList[position])
    }
}