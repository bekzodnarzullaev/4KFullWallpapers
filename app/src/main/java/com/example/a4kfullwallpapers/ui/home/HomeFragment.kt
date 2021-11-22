package com.example.a4kfullwallpapers.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SearchView
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.a4kfullwallpapers.R
import com.example.a4kfullwallpapers.adapters.CategoryPagerAdapter
import com.example.a4kfullwallpapers.adapters.UnSplashPhotoAdapter
import com.example.a4kfullwallpapers.adapters.UnSplashPhotoLoadStateAdapter
import com.example.a4kfullwallpapers.databinding.FragmentHomeBinding
import com.example.a4kfullwallpapers.models.UnSplashPhoto
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), UnSplashPhotoAdapter.OnItemClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var categoryPagerAdapter: CategoryPagerAdapter
    lateinit var categoryList: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        loadCategory()

        categoryPagerAdapter = CategoryPagerAdapter(activity?.supportFragmentManager!!, lifecycle)
        binding.viewPager.adapter = categoryPagerAdapter

        binding.apply {
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = categoryList[position]
            }.attach()
        }

        //set tabs
        setTabs()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabView: View = tab?.customView!!
                val title = tabView.findViewById<TextView>(R.id.tab_title)
                val circle = tabView.findViewById<LinearLayout>(R.id.tab_circle)
                circle.visibility = VISIBLE
                title.setTextColor(Color.WHITE)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabView: View = tab?.customView!!
                val title = tabView.findViewById<TextView>(R.id.tab_title)
                val circle = tabView.findViewById<LinearLayout>(R.id.tab_circle)
                circle.visibility = INVISIBLE
                title.setTextColor(resources.getColor(R.color.tab_unselected_color))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

//        inflater.inflate(R.menu.main, menu)



        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchItem.isVisible = true

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.tabLayout.isVisible = false
                    binding.viewPager.isVisible = false
                    binding.rv.isVisible = true
                    setAdapter()
                    binding.rv.scrollToPosition(0)
                    homeViewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                binding.tabLayout.isVisible = true
                binding.viewPager.isVisible = true
                binding.rv.isVisible = false
                return true
            }

        })

    }

    private fun setAdapter() {

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

        homeViewModel.photos.observe(viewLifecycleOwner) {
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

    private fun setTabs() {

        val tabCount = binding.tabLayout.tabCount

        for (i in 0 until tabCount) {
            val tabView: View = layoutInflater.inflate(R.layout.item_tab, null, false)
            val title = tabView.findViewById<TextView>(R.id.tab_title)
            val circle = tabView.findViewById<LinearLayout>(R.id.tab_circle)
            title.text = categoryList[i]
            if (i == 0) {
                circle.visibility = VISIBLE
                title.setTextColor(Color.WHITE)
            } else {
                circle.visibility = INVISIBLE
            }
            binding.tabLayout.getTabAt(i)?.customView = tabView
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabView: View = tab?.customView!!
                val title = tabView.findViewById<TextView>(R.id.tab_title)
                val circle = tabView.findViewById<LinearLayout>(R.id.tab_circle)
                circle.visibility = VISIBLE
                title.setTextColor(Color.WHITE)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabView: View = tab?.customView!!
                val title = tabView.findViewById<TextView>(R.id.tab_title)
                val circle = tabView.findViewById<LinearLayout>(R.id.tab_circle)
                circle.visibility = INVISIBLE
                title.setTextColor(resources.getColor(R.color.tab_unselected_color))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun loadCategory() {
        categoryList = ArrayList()
        categoryList.add("ALL")
        categoryList.add("NEW")
        categoryList.add("TECHNOLOGY")
        categoryList.add("ANIMAL")
        categoryList.add("NATURE")
        categoryList.add("COUNTRY")
        categoryList.add("SEASON")
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