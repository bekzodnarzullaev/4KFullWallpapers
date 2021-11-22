package com.example.a4kfullwallpapers.ui.liked

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.a4kfullwallpapers.adapters.LikedPhotoAdapter
import com.example.a4kfullwallpapers.databinding.FragmentLikedBinding
import com.example.a4kfullwallpapers.db.entity.PhotoEntity
import com.example.a4kfullwallpapers.ui.home.HomeViewModel


class LikedFragment : Fragment() {

    lateinit var viewModel: HomeViewModel
    lateinit var likedPhotoAdapter: LikedPhotoAdapter
    private var _binding: FragmentLikedBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        viewModel.getLikedPhotos().observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                likedPhotoAdapter = LikedPhotoAdapter(it)
                binding.rv.adapter = likedPhotoAdapter
            }else{
                binding.noLike.isVisible = true
            }

        }
    }

}