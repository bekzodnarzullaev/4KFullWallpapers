package com.example.a4kfullwallpapers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a4kfullwallpapers.databinding.UnsplashPhotoLoadStateFooterBinding


class UnSplashPhotoLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UnSplashPhotoLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val itemLoadStateFooterBinding: UnsplashPhotoLoadStateFooterBinding) :
        RecyclerView.ViewHolder(itemLoadStateFooterBinding.root) {

        init {
            itemLoadStateFooterBinding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            itemLoadStateFooterBinding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
                textViewLoadStateError.isVisible = loadState !is LoadState.Loading
            }
        }

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            UnsplashPhotoLoadStateFooterBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }
}