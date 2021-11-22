package com.example.a4kfullwallpapers.ui.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.a4kfullwallpapers.R
import com.example.a4kfullwallpapers.adapters.UnSplashPhotoAdapter
import com.example.a4kfullwallpapers.adapters.UnSplashPhotoLoadStateAdapter
import com.example.a4kfullwallpapers.databinding.FragmentCategoryBinding
import com.example.a4kfullwallpapers.databinding.FragmentPopularBinding
import com.example.a4kfullwallpapers.models.UnSplashPhoto
import com.example.a4kfullwallpapers.ui.home.HomeViewModel


class PopularFragment : Fragment(), UnSplashPhotoAdapter.OnItemClickListener {

    lateinit var viewModel: HomeViewModel
    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPopularBinding.bind(view)

        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        val adapter = UnSplashPhotoAdapter(this)

        binding.apply {
            rv.setHasFixedSize(true)
            rv.itemAnimator = null
            rv.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnSplashPhotoLoadStateAdapter { adapter.retry() },
                footer = UnSplashPhotoLoadStateAdapter { adapter.retry() }
            )
            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.searchPhotos("popular")

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rv.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                //empty view
                if (loadState.source.refresh is LoadState.Loading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rv.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(photo: UnSplashPhoto) {
        val bundle = Bundle()
        bundle.putSerializable("photo", photo)
        findNavController().navigate(R.id.photoFragment, bundle)
    }

}