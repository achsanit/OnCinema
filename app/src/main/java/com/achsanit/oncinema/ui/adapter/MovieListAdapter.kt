package com.achsanit.oncinema.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achsanit.oncinema.R
import com.achsanit.oncinema.data.source.remote.model.Result
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_item.view.*
import java.lang.ClassCastException

private val ITEM_VIEW_TYPE_FOOTER = 1
private val ITEM_VIEW_TYPE_ITEM = 0

class MovieListAdapter(): ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffUtil()) {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    class FooterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is DataItem.MovieItem -> ITEM_VIEW_TYPE_ITEM
            is DataItem.Footer -> ITEM_VIEW_TYPE_FOOTER
        }
    }

    fun submitListWithFooter(list: List<Result>) {
        val items = list.map { DataItem.MovieItem(it) } + listOf(DataItem.Footer)

        submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_ITEM -> ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false))
            ITEM_VIEW_TYPE_FOOTER -> FooterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.footer_list,parent,false))
            else -> throw  ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ItemViewHolder -> {
                val currentList =  getItem(position) as DataItem.MovieItem
                val item = currentList.movieItem
                val poster = "https://image.tmdb.org/t/p/w500${item.posterPath}"

                Glide.with(holder.itemView.context)
                    .load(poster)
                    .into(holder.itemView.ivPoster)
                val rating = ((item.voteAverage * 10).toInt()).toDouble()/10
                holder.itemView.tvRating.text = rating.toString()
                holder.itemView.cardRecyclerItem.setOnClickListener {

                }
            }
        }
    }
}

sealed class DataItem {
    abstract val id: Long
    data class MovieItem(val movieItem: Result): DataItem() {
        override val id: Long
            get() = movieItem.id.toLong()
    }

    object Footer: DataItem() {
        override val id: Long
            get() = Long.MIN_VALUE
    }
}

class DiffUtil : DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}