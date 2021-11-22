package com.example.a4kfullwallpapers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.a4kfullwallpapers.R
import com.example.a4kfullwallpapers.databinding.ItemUnsplashPhotoBinding
import com.example.a4kfullwallpapers.db.entity.PhotoEntity


class LikedPhotoAdapter(var list: List<PhotoEntity>) : RecyclerView.Adapter<LikedPhotoAdapter.Vh>() {
    inner class Vh(var itemUnsplashPhotoBinding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(itemUnsplashPhotoBinding.root) {

        fun onBind(photo: PhotoEntity) {
            itemUnsplashPhotoBinding.apply {
                Glide.with(itemView)
                    .load(photo.url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .placeholder(R.drawable.place_holder)
                    .into(imageView)

                textViewUserName.text = photo.creator
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}