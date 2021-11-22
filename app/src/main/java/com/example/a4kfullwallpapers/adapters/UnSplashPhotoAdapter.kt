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
import com.example.a4kfullwallpapers.models.UnSplashPhoto

class UnSplashPhotoAdapter(private val onItemClickListener: OnItemClickListener) :
    PagingDataAdapter<UnSplashPhoto, UnSplashPhotoAdapter.PhotoViewHolder>(
        PHOTO_COMPARATOR
    ) {

    inner class PhotoViewHolder(var itemUnsplashPhotoBinding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(itemUnsplashPhotoBinding.root) {

        init {
            itemUnsplashPhotoBinding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        onItemClickListener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(photo: UnSplashPhoto) {
            itemUnsplashPhotoBinding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .placeholder(R.drawable.place_holder)
                    .into(imageView)

                textViewUserName.text = photo.user.username
            }
        }

    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnSplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnSplashPhoto, newItem: UnSplashPhoto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UnSplashPhoto,
                newItem: UnSplashPhoto
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ItemUnsplashPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    interface OnItemClickListener {
        fun onItemClick(photo: UnSplashPhoto)
    }
}