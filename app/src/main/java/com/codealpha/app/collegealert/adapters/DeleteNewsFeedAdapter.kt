package com.codealpha.app.collegealert.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codealpha.app.collegealert.R
import com.codealpha.app.collegealert.databinding.DeleteFeedBinding
import com.codealpha.app.collegealert.modelclasses.NewsFeed

class DeleteNewsFeedAdapter(val list: ArrayList<NewsFeed>,val clickFun:(NewsFeed,Int)->Unit): RecyclerView.Adapter<DeleteNewsFeedAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = DeleteFeedBinding.bind(itemView)
        fun bind(newsFeed: NewsFeed,position: Int){
            binding.btnDelete.setOnClickListener {
                clickFun(newsFeed,position)
                removeItem(adapterPosition)
            }
            Glide.with(binding.img).load(newsFeed.image).into(binding.img)
            binding.txtTitle.text = newsFeed.title
            binding.txtDate.text = newsFeed.date
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeleteNewsFeedAdapter.MyViewHolder {
       return MyViewHolder(LayoutInflater.from(parent.context)
           .inflate(R.layout.delete_feed,parent,false))
    }

    override fun onBindViewHolder(
        holder: DeleteNewsFeedAdapter.MyViewHolder,
        position: Int
    ) {
        holder.bind(list[position],position)
    }

    override fun getItemCount(): Int {
       return list.size
    }
    fun removeItem(position: Int) {
        list.remove(list[position])
        notifyItemRemoved(position)
        notifyDataSetChanged() // Or use notifyItemRemoved() for better performance
    }
}