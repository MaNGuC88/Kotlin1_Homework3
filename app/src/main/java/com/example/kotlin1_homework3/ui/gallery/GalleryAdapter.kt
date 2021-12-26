package com.example.kotlin1_homework3.ui.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin1_homework3.R
import com.example.kotlin1_homework3.base.OnItemClick
import com.example.kotlin1_homework3.databinding.ItemImageBinding
import com.example.kotlin1_homework3.extensions.load

class GalleryAdapter (private val context: Context, private var list: MutableList<String>):
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>(){

    private val selected: ArrayList<Int> = ArrayList()

    private var onItemClick: OnItemClick? = null

    fun setOnItemClick(onItemClick: OnItemClick?) {
        this.onItemClick = onItemClick
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(context), parent, false)
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(list[position])

        if (!selected.contains(position)){
            holder.itemView.setBackgroundResource(R.drawable.bg_image_unselected)
        }
        else
            holder.itemView.setBackgroundResource(R.drawable.bg_image_selected)

        holder.itemView.setOnClickListener {
            onItemClick?.onItemClick(position)
            if (selected.contains(position)) {
                selected.remove(selected.indexOf(position))
                holder.itemView.setBackgroundResource(R.drawable.bg_image_unselected)
            } else {
                selected.add(position)
                holder.itemView.setBackgroundResource(R.drawable.bg_image_selected)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getItem(position: Int): String {
        return list[position]
    }

    class GalleryViewHolder(itemImageBinding: ItemImageBinding):
        RecyclerView.ViewHolder(itemImageBinding.root){

        private val binding = itemImageBinding

        fun bind(uri: String) {
            binding.image.load(uri)
        }
    }
}