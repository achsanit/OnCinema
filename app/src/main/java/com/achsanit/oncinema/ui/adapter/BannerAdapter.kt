package com.achsanit.oncinema.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.achsanit.oncinema.R
import com.achsanit.oncinema.data.source.remote.model.Result
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.banner_item.view.*

class BannerAdapter(): RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData(list: List<Result>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.banner_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentList = differ.currentList[position]
        val banner = "https://image.tmdb.org/t/p/w500${currentList.backdropPath}"

        holder.itemView.tvTitleBanner.text = currentList.title

        Glide.with(holder.itemView.context)
            .load(banner)
            .into(holder.itemView.ivItemBanner)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}