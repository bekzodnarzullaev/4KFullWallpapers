package com.example.a4kfullwallpapers.ui.photo

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.a4kfullwallpapers.R
import com.example.a4kfullwallpapers.databinding.FragmentHomeBinding
import com.example.a4kfullwallpapers.databinding.FragmentPhotoBinding
import com.example.a4kfullwallpapers.models.UnSplashPhoto
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.a4kfullwallpapers.db.entity.PhotoEntity
import com.example.a4kfullwallpapers.ui.home.HomeViewModel

private const val ARG_PARAM1 = "photo"

class PhotoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var photo: UnSplashPhoto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            photo = it.getSerializable(ARG_PARAM1) as UnSplashPhoto
        }
    }

    private var _binding: FragmentPhotoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel:HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPhotoBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.hide()

        binding.apply {
            Glide.with(this@PhotoFragment)
                .load(photo?.urls?.regular)
                .error(R.drawable.ic_error)
                .listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }
                })
                .into(imageView)
        }

        binding.apply {
            like.setOnClickListener{
                viewModel.likePhoto(PhotoEntity(photo_id = photo?.id!!, url = photo?.urls?.regular!!, creator = photo?.user?.username!!))
            }

            unlike.setOnClickListener {
                viewModel.unlikePhoto(photo?.id!!)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.show()
        _binding = null
    }

}