package com.example.kotlin1_homework3.ui.selected_images

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin1_homework3.databinding.ItemImageBinding
import com.example.kotlin1_homework3.extensions.load

class SelectedImagesAdapter(private val context: Context, private var list: MutableList<String>):
    RecyclerView.Adapter<SelectedImagesAdapter.SelectedImagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedImagesViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(context), parent, false)
        return SelectedImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectedImagesViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SelectedImagesViewHolder(itemImageBinding: ItemImageBinding):
        RecyclerView.ViewHolder(itemImageBinding.root){

        private val binding = itemImageBinding

        fun bindItems(uri: String) {
            binding.image.load(uri)
        }
    }

}